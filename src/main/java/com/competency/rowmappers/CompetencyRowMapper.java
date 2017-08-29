package com.competency.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.competency.model.CompetencyEntity;

public class CompetencyRowMapper implements RowMapper<CompetencyEntity> {

	@Override
	public CompetencyEntity mapRow(ResultSet rs, int row) throws SQLException {
		CompetencyEntity c = new CompetencyEntity();
		c.setCompetencyId(rs.getInt(1));
		c.setCompetency(rs.getString(2));
		c.setCategoryId(rs.getInt(3));
		c.setCategory(rs.getString(4));
		c.setQuestionId(rs.getInt(5));
		c.setQuestionNbr(rs.getInt(6));
		c.setItemId(rs.getInt(7));
		c.setQuestion(rs.getString(8));
		c.setExplanation(rs.getString(9));
		c.setChoiceId(rs.getInt(10));
		c.setChoiceNbr(rs.getInt(11));
		c.setChoice(rs.getString(12));
		c.setWeight(rs.getInt(13));
		return c;
	}

}