package com.doc.word.readers;

import java.io.IOException;
import java.io.InputStream;

import com.doc.word.utils.StringUtils;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

public class PdfReaderImpl extends DocumentReader {
	
	public PdfReaderImpl() {
		super();
	}

	@Override
	public void parse(InputStream in) throws IOException {
		PdfDocument document = new PdfDocument(new PdfReader(in));
		for (int p = 1; p <= document.getNumberOfPages(); p++) {
			String page = PdfTextExtractor.getTextFromPage(document.getPage(p));
			String[] lines = page.split(StringUtils.CRLF_PATTERN);
			for (String line : lines)
				getDocument().addLine(line);
		}
		document.close();
	}

}
