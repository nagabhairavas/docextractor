package com.doc.word.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.doc.word.utils.StringUtils;

@Entity(name = "Line")
@Table(name = "data_asset_line", schema="af_poc")
public class Line {
	
	@EmbeddedId
    private LineId id;
	
	@Column(name = "line_content")
	private String content;
	
	@OneToMany
	@NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
        @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id"),
        @JoinColumn(name = "line_id", referencedColumnName = "line_id")
    })
	private List<Word> words;
	
	private Line() {
	}
	
	public Line(int userId, int transactionId, int lineId, String content) {
		this.id = new LineId(userId, transactionId, lineId);
		this.content = content;
		this.words = new ArrayList<>();
		addWords(content);
	}

	public LineId getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public List<Word> getWords() {
		return words;
	}

	public void addWords(String line) {
		Pattern wordSplitter = Pattern.compile(StringUtils.WORD_PATTERN);
		Matcher m = wordSplitter.matcher(line);
		while (m.find()) {
	    	String word = m.group();
	    	if (!StringUtils.isEmpty(word) && !word.trim().matches(StringUtils.NUMBER_PATTERN)) {
	    		words.add(new Word(id.getUserId(), id.getTransactionId(), id.getLineId(), words.size()+1, StringUtils.trim(word)));
	    	}
	    }
	}
	
}
