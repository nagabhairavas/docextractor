package com.competency.model;

public class Choice {
	private int id;
	private int number;
	private String name;
	private int weight;
	public Choice() {}
	public Choice(int id, int number, String name, int weight) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.weight = weight;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getNumber() {
		return number;
	}
	public int getWeight() {
		return weight;
	}
	
}
