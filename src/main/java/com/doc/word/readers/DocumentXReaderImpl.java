package com.doc.word.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocumentXReaderImpl extends DocumentReader {

	public DocumentXReaderImpl() {
		super();
	}
	
	@Override
	public void parse(InputStream in) throws IOException {
		XWPFWordExtractor myWords = new XWPFWordExtractor(new XWPFDocument(in));                
	    in.close();
	    Scanner scanner = new Scanner(myWords.getText());
	    while (scanner.hasNextLine())
	    	getDocument().addLine(scanner.nextLine());
	    scanner.close();
	    myWords.close();
	}

}
