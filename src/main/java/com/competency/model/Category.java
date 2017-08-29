package com.competency.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private int id;
	private String name;
	private List<Question> questionnaire;
	
	public Category() {
	}
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
		questionnaire = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Question> getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(List<Question> questionnaire) {
		this.questionnaire = questionnaire;
	}
	
	public void addQuestion(Question question) {
		questionnaire.add(question);
	}
	
}