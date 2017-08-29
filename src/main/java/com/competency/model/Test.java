package com.competency.model;

import java.util.List;

public class Test {
	private int id;
	private int userId;
	private List<Competency> competencies;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<Competency> getCompetencies() {
		return competencies;
	}
	public void setCompetencies(List<Competency> competencies) {
		this.competencies = competencies;
	}
	
}
