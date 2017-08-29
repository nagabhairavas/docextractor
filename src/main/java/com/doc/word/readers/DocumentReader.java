package com.doc.word.readers;

import java.io.IOException;
import java.io.InputStream;

import com.doc.word.entity.Document;

public abstract class DocumentReader {
	
	private Document document;
	
	public abstract void parse(InputStream in) throws IOException;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}