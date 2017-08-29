package com.competency.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.competency.model.DocResult;

public class DocResultRowMapper implements RowMapper<DocResult> {

	@Override
	public DocResult mapRow(ResultSet rs, int row) throws SQLException {
		DocResult d = new DocResult();
		d.setCategoryId(rs.getInt(1));
		d.setTermId(rs.getInt(2));
		d.setTermDescr(rs.getString(3));
		d.setCompetencyId(rs.getInt(4));
		d.setCompetencyDescr(rs.getString(5));
		return d;
	}

}