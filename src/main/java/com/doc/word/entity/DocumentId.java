package com.doc.word.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentId implements Serializable {
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "transaction_id")
	private int transactionId;
	
	private DocumentId() {
    }
 
    public DocumentId(int userId, int transactionId) {
        this.userId = userId;
        this.transactionId = transactionId;
    }
    
    public int getUserId() {
		return userId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentId)) return false;
        DocumentId that = (DocumentId) o;
        return getUserId()==that.getUserId() &&
        	getTransactionId()==that.getTransactionId();
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTransactionId());
    }

}
