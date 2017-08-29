package com.competency.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private int id;
	private int number;
	private int itemId;
	private String name;
	private String type;
	private String explanation;
	private int answer;
	private byte[] image;
	private List<Choice> choices;
	
	public Question() {}
	public Question(int id, int number, int itemId, String name, String type, String explanation) {
		this.id = id;
		this.number = number;
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.explanation = explanation;
		choices = new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public List<Choice> getChoices() {
		return choices;
	}
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
	
	public void addChoice(Choice choice) {
		choices.add(choice);
	}
	
}
