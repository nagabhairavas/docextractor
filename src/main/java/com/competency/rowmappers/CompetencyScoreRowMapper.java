package com.competency.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.competency.model.CompetencyScore;

public class CompetencyScoreRowMapper implements RowMapper<CompetencyScore> {

	@Override
	public CompetencyScore mapRow(ResultSet rs, int row) throws SQLException {
		CompetencyScore c = new CompetencyScore();
		c.setCompetencyId(rs.getInt(1));
		c.setCompetency(rs.getString(2));
		c.setQuestions(rs.getInt(3));
		c.setAnswers(rs.getInt(4));
		c.setPercent(rs.getDouble(5));
		return c;
	}

}