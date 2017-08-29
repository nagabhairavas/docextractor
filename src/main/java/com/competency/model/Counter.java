package com.competency.model;

public class Counter {
	private int counter;
	public int init() {
		counter = 0;
		return counter;
	}
	public int incrementAndGet() {
		++counter;
		return counter;
	}
	public int get() {
		return counter;
	}
}