package com.doc.word.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LineId implements Serializable {
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "transaction_id")
	private int transactionId;
	
	@Column(name = "line_id")
	private int lineId;
	
	private LineId() {
    }
 
    public LineId(int userId, int transactionId, int lineId) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.lineId = lineId;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineId)) return false;
        LineId that = (LineId) o;
        return getUserId()==that.getUserId() &&
        	getTransactionId()==that.getTransactionId() &&
            getLineId()==that.getLineId();
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getTransactionId(), getLineId());
    }

}
