package com.doc.word.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Word")
@Table(name = "data_asset_word", schema="af_poc")
public class Word {
	
	@EmbeddedId
    private WordId id;
	
	@Column(name = "word")
	private String value;
	
	private Word() {
	}
	
	public Word(int userId, int transactionId, int lineId, int wordId, String value) {
		this.id = new WordId(userId, transactionId, lineId, wordId);
		this.value = value;
	}

	public WordId getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
	
}
