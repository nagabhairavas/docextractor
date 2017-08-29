package com.doc.word.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import com.doc.word.enums.DocumentStatus;
import com.doc.word.enums.DocumentType;

@Entity(name = "Document")
@Table(name = "data_asset_proc_status", schema="af_poc")
public class Document {
	
	@EmbeddedId
    private DocumentId id;
	@Column(name = "data_asset_type")
	private DocumentType type;
	private DocumentStatus status;
	@OneToMany
	@NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
        @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    })
	private List<Line> lines;
	
	private Document() {
	}
	
	public Document(int userId, int transactionId, DocumentType type) {
		id = new DocumentId(userId, transactionId);
		this.type = type;
		this.lines = new ArrayList<>();
	}

	public int getUserId() {
		return id.getUserId();
	}

	public int getTransactionId() {
		return id.getTransactionId();
	}
	
	public DocumentId getId() {
		return id;
	}
	
	public DocumentType getType() {
		return type;
	}

	public DocumentStatus getStatus() {
		return status;
	}

	public void setStatus(DocumentStatus status) {
		this.status = status;
	}
	
	public List<Line> getLines() {
		return lines;
	}
	
	public void addLine(String content) {
		if (!StringUtils.isEmpty(content))
			lines.add(new Line(id.getUserId(), id.getTransactionId(), lines.size()+1, content));
	}
}
