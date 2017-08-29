package com.competency.repository;

import com.competency.model.CompetencyEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompetencyRepositoryJpa extends JpaRepository<CompetencyEntity, Long> {
	
	@Query(value="select a.competency_id competencyId, c.competency_descr competency, " +
		"a.category_id categoryId, g.category_descr category, " +
		"substring(cast(a.question_nbr as varchar(10)),2,3) as questionId, a.question_nbr questionNbr, " + 
		"a.item_id itemId, a.question_txt question, a.answer_type answerType, " +
		"b.item_choice_id choiceId, b.answer_choice_nbr choiceNbr, b.answer_choice choice, b.answer_weight weight " + 
		"from AF_POC.QUESTION_REPOSITORY a, AF_POC.QUESTION_CHOICE b, af_poc.competency_table c, af_poc.category_table g " + 
		"where substring(cast(a.question_nbr as varchar(10)),1,1)=?1 " +
		"and a.item_id=b.item_id and a.competency_id=c.competency_id and a.category_id=g.category_id " + 
		"order by a.competency_id, a.category_id, substring(cast(a.question_nbr as varchar(10)),1,1), " +
		"substring(cast(a.question_nbr as varchar(10)),2,3), a.item_id, b.answer_choice_nbr asc", nativeQuery = true)
	
	List<CompetencyEntity> findByTestId(String testId);

}
