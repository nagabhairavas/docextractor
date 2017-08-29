package com.competency.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.competency.model.Category;
import com.competency.model.Choice;
import com.competency.model.Competency;
import com.competency.model.CompetencyEntity;
import com.competency.model.CompetencyScore;
import com.competency.model.DocResult;
import com.competency.model.Question;
import com.competency.model.Test;
import com.competency.rowmappers.CompetencyRowMapper;
import com.competency.rowmappers.CompetencyScoreRowMapper;
import com.competency.rowmappers.DocResultRowMapper;

@Repository
public class CompetencyRepository {
	
	final static Logger logger = LoggerFactory.getLogger(CompetencyRepository.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Test getCompetency(int userId) {
		Test test = new Test();
		Integer testId = jdbcTemplate.queryForObject("select max(test_nbr) testId from af_poc.user_test_status where user_id=?", 
			new Object[]{userId}, Integer.class);
		if (testId==null) test.setId(1);
		else test.setId(testId+1);
		List<CompetencyEntity> list = jdbcTemplate.query("select a.competency_id competencyId, c.competency_descr competency, " +
			"a.category_id categoryId, g.category_descr category, substring(cast(a.question_nbr as varchar(10)),2,3) as questionId, " +
			"a.question_nbr questionNbr, a.item_id itemId, a.question_txt question, a.explanation, " + 
			"b.item_choice_id choiceId, b.answer_choice_nbr choiceNbr, b.answer_choice choice, b.answer_weight weight " + 
			"from AF_POC.QUESTION_REPOSITORY a, AF_POC.QUESTION_CHOICE b, af_poc.competency_table c, af_poc.category_table g " + 
			"where (substring(cast(a.question_nbr as varchar(10)),1,1)=? or a.competency_id=7) " +
			"and a.item_id=b.item_id and a.competency_id=c.competency_id and a.category_id=g.category_id " + 
			"order by a.competency_id, a.category_id, substring(cast(a.question_nbr as varchar(10)),1,1), " +
			"substring(cast(a.question_nbr as varchar(10)),2,3), a.item_id, b.answer_choice_nbr asc", new Object[]{test.getId()+""}, 
			new CompetencyRowMapper());
		
		List<Competency> competencies = new ArrayList<>();
		test.setCompetencies(competencies);
		Competency competency = null;
		Category category = null;
		Question question = null;
		int compId = -1, cId = -1, qId = -1;
		for (CompetencyEntity entity : list) {
			if (compId != entity.getCompetencyId()) {
				competency = new Competency(entity.getCompetencyId(), entity.getCompetency());
				competencies.add(competency);
				compId = entity.getCompetencyId();
				cId = -1; qId = -1;
			}
			if (cId!=entity.getCategoryId()) {
				category = new Category(entity.getCategoryId(), entity.getCategory());
				competency.addCategory(category);
				cId = entity.getCategoryId();
				qId = -1;
			}
			if (qId!=entity.getQuestionId()) {
				question = new Question(entity.getQuestionId(), entity.getQuestionNbr(), entity.getItemId(), 
					entity.getQuestion(), entity.getAnswerType(), entity.getExplanation());
				category.addQuestion(question);
				qId = entity.getQuestionId();
			}
			question.addChoice(new Choice(entity.getChoiceId(), entity.getChoiceNbr(), entity.getChoice(), entity.getWeight()));
		}
		return test;
	}
	
	public int saveTest(Test test, List<Question> answers) {
		int status = jdbcTemplate.update("update af_poc.user_test_status set test_nbr=?,status='S' where user_id=?", 
			new Object[]{test.getId(), test.getUserId()}, new int[]{Types.INTEGER, Types.INTEGER});
		if (status==0)
			jdbcTemplate.update("insert into af_poc.user_test_status (user_id,test_nbr,status) values (?,?,'S')", 
				new Object[]{test.getUserId(), test.getId()}, new int[]{Types.INTEGER, Types.INTEGER});
		jdbcTemplate.batchUpdate("insert into af_poc.question_response (item_response_id,item_id,item_choice_id,user_id,status) " +
			"values (nextval('af_poc.response_seq'),?,?,?,'S')", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Question answer = answers.get(i);
				logger.info("question.itemId={}, question.answer={}", answer.getItemId(), answer.getAnswer());
				ps.setInt(1, answer.getItemId());
				ps.setInt(2, answer.getAnswer());
				ps.setInt(3, test.getUserId());
			}

			@Override
			public int getBatchSize() {
				return answers.size();
			}
		});
		return 1;
	}
	
	public List<CompetencyScore> getTestScore(int userId) {
		Test test = new Test();
		Integer testId = jdbcTemplate.queryForObject("select max(test_nbr) testId from af_poc.user_test_status where user_id=?", 
			new Object[]{userId}, Integer.class);
		if (testId==null) test.setId(1);
		else test.setId(testId);
		List<CompetencyScore> list = jdbcTemplate.query("select c.competency_id competencyId, c.competency_descr competency, " +
			"count(distinct q.item_id) questions, sum(case when qc.answer_weight=100 then 1 else 0 end) correctanswers, " +
			"round(sum(case when qc.answer_weight=100 then 1 else 0 end)*100/count(distinct q.item_id),2) pct " +
			"from af_poc.question_repository q " +
			"join af_poc.competency_table c on c.competency_id=q.competency_id " + 
			"left outer join af_poc.question_response a on a.item_id=q.item_id and a.user_id=? " + 
			"left outer join af_poc.question_choice qc on qc.item_choice_id=a.item_choice_id " +
			"where cast(substring(cast(q.question_nbr as varchar(10)),1,1) as integer)=? and q.competency_id!=7 " +
			"group by c.competency_id, c.competency_descr " +
			"order by 2", new Object[]{userId, test.getId()}, 
			new CompetencyScoreRowMapper());
		return list;
	}
	
	public List<CompetencyScore> getMultipleIntelligenceScore(int userId) {
		//Test test = new Test();
		//Integer testId = jdbcTemplate.queryForObject("select max(test_nbr) testId from af_poc.user_test_status where user_id=?", 
		//	new Object[]{userId}, Integer.class);
		//if (testId==null) test.setId(1);
		//else test.setId(testId);
		List<CompetencyScore> list = jdbcTemplate.query("select g.category_id competencyId, g.category_descr competency, " +
			"count(distinct q.item_id) questions, sum(case when c.answer_weight=100 then 1 else 0 end) correctanswers, " +
			"round(sum(case when c.answer_weight=100 then 1 else 0 end)*100.0/count(distinct q.item_id),2) pct " +
			"from af_poc.question_repository q " +
			"left outer join af_poc.category_table g on g.category_id=q.category_id " +
			"left outer join af_poc.question_response r on r.item_id=q.item_id and r.user_id=? " +
			"left outer join af_poc.question_choice c on c.item_id=q.item_id and c.item_choice_id=r.item_choice_id " +
			"where q.competency_id=7 " +
			"group by g.category_id, g.category_descr " +
			"order by 2", new Object[]{userId}, 
			new CompetencyScoreRowMapper());
		return list;
	}
	
	public List<DocResult> getDocResult(int userId, int transactionId) {
		List<DocResult> list = jdbcTemplate.query("select distinct x.term_category_id, x.term_id, x.term_descr, m.competency_id, c.competency_descr " +
			"from (select * from af_poc.ai_term_repository t, af_poc.data_asset_line l " +
			"where l.line_content ilike '%'||t.term_descr||'%' order by l.user_id, l.transaction_id, l.line_id, t.term_descr) as x, " +
			"af_poc.ai_term_category_map m, af_poc.competency_table c " +
			"where x.term_category_id=m.term_category_id and x.term_id=m.term_id " +
			"and c.competency_id=m.competency_id and X.user_id=? and X.transaction_id=? " +
			"order by x.term_category_id, x.term_id, m.competency_id", new Object[]{userId, transactionId}, 
			new DocResultRowMapper());
		return list;
	}
	
	public Integer getTransactionId() {
		return jdbcTemplate.queryForObject("select nextval('af_poc.transid_seq')", Integer.class);
	}
}
