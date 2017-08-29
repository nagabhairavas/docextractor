package com.doc.word.extractor.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doc.word.entity.Document;
import com.doc.word.enums.DocumentType;
import com.doc.word.readers.DocumentReader;
import com.doc.word.readers.DocumentReaderFactory;
import com.doc.word.repository.DocumentRepository;
import com.doc.word.repository.LineRepository;
import com.doc.word.repository.WordRepository;

@Service
public class MyWordExtractor {
	
	final static Logger logger = LoggerFactory.getLogger(MyWordExtractor.class);
	
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private LineRepository lineRepository;
	@Autowired
	private WordRepository wordRepository;
	
	public static void main(String[] args) throws Exception {
	    
		String fileExt = args[0].substring(args[0].lastIndexOf(".")+1);
		logger.info("File Extesion : " + fileExt);
		DocumentType docType = DocumentType.UNKNOWN;
		try {
			DocumentType.valueOf(fileExt.toUpperCase());
		} catch (IllegalArgumentException iae) {
			logger.error("Unknown document type {}", fileExt);
			System.exit(1);
		}
		try {
		    FileInputStream in = new FileInputStream(new File(args[0]));
		    DocumentReader reader = DocumentReaderFactory.getDocument(docType);
			reader.parse(in);
		    Document document = reader.getDocument();
		    logger.error("Number of lines in the document {}", document.getLines().size());
		    //words.stream().forEach(System.out::println);
		} catch (Exception e) {
			logger.error("Error processing documents {}", e.getMessage());
			System.exit(1);
		}
	}
	
	public Document extract(int userId, int transactionId, String fileName, InputStream in) {
		Document document = null;
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
		logger.info("File Extesion : " + fileExt);
		DocumentType docType = DocumentType.UNKNOWN;
		try {
			docType = DocumentType.valueOf(fileExt.toUpperCase());
		} catch (IllegalArgumentException iae) {
			logger.error("Unknown document type {}", fileExt);
		}
		try {
		    DocumentReader reader = DocumentReaderFactory.getDocument(docType);
		    document = new Document(userId, transactionId, docType);
		    reader.setDocument(document);
			reader.parse(in);
		    document = reader.getDocument();
		    logger.info("Number of lines in the document {}", document.getLines().size());
		    //int words = document.getLines().stream().
		    //logger.info("Number of words in the document {}", document.getLines().size());
		    //words.stream().forEach(System.out::println);
		} catch (Exception e) {
			logger.error("Error processing documents {}", e.getMessage());
			e.printStackTrace(System.out);
		}
		documentRepository.save(document);
		document.getLines().stream().forEach((line) -> {
			lineRepository.save(line);
			line.getWords().stream().forEach((word) -> {
				wordRepository.save(word);
			});
		});
		return document;
	}
}
