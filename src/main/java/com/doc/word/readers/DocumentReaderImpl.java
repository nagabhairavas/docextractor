package com.doc.word.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.poi.hwpf.extractor.WordExtractor;

public class DocumentReaderImpl extends DocumentReader {

	public DocumentReaderImpl() {
		super();
	}
	
	@Override
	public void parse(InputStream in) throws IOException {
		WordExtractor myWords = new WordExtractor(in);                
	    in.close();
	    Scanner scanner = new Scanner(myWords.getText());
	    while (scanner.hasNextLine())
	    	getDocument().addLine(scanner.nextLine());
	    scanner.close();
	    myWords.close();
	}

}
