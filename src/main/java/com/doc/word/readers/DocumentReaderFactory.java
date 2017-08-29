package com.doc.word.readers;

import com.doc.word.enums.DocumentType;

public class DocumentReaderFactory {
	public static DocumentReader getDocument(DocumentType type) {
		switch (type) {
			case DOC:
				return new DocumentReaderImpl();
			case DOCX:
				return new DocumentXReaderImpl();
			case PDF:
				return new PdfReaderImpl();
			default:
				return null;
		}
	}
}