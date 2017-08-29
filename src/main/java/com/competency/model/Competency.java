package com.competency.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public class Competency {
	
	private int id;
	private String description;
	private List<Category> categories;
	private List<Integer> questionnaire;
	@Value("${competency.vizualization}")
	public static final int VIZUALIZATION = 8;
	public Competency() {
	}
	
	public Competency(int id, String description) {
		this.id = id;
		this.description = description;
		categories = new ArrayList<>();
		questionnaire = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public void addCategory(Category category) {
		this.categories.add(category);
	}

	public List<Integer> getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(List<Integer> questionnaire) {
		this.questionnaire = questionnaire;
	}
	
	public boolean isVisualization() {
		return getId()==VIZUALIZATION;
	}
}