package com.doc.word.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WordId implements Serializable {
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "transaction_id")
	private int transactionId;
	
	@Column(name = "line_id")
	private int lineId;
	
	@Column(name = "word_id")
	private int wordId;
	
	private WordId() {
    }
 
    public WordId(int userId, int transactionId, int lineId, int wordId) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.lineId = lineId;
        this.wordId = wordId;
    }
    
    public int getUserId() {
		return userId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public int getLineId() {
		return lineId;
	}

	public int getWordId() {
		return wordId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineId)) return false;
        WordId that = (WordId) o;
        return getUserId()==that.getUserId() &&
        	getTransactionId()==that.getTransactionId() &&
            getLineId()==that.getLineId() &&
            getWordId()==that.getWordId();
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTransactionId(), getLineId(), getWordId());
    }

}
