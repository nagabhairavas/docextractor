package com.competency.model;

public class CompetencyScore {
	private int competencyId;
	private String competency;
	private int categoryId;
	private String category;
	private int questions;
	private int answers;
	private double percent;
	public int getCompetencyId() {
		return competencyId;
	}
	public void setCompetencyId(int competencyId) {
		this.competencyId = competencyId;
	}
	public String getCompetency() {
		return competency;
	}
	public void setCompetency(String competency) {
		this.competency = competency;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getQuestions() {
		return questions;
	}
	public void setQuestions(int questions) {
		this.questions = questions;
	}
	public int getAnswers() {
		return answers;
	}
	public void setAnswers(int answers) {
		this.answers = answers;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
}