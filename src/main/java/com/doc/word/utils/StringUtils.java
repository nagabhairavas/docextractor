package com.doc.word.utils;

public class StringUtils {
	public static String NUMBER_PATTERN = "-?\\d+(\\.\\d+)?";
	public static String WORD_PATTERN = "[a-zA-Z0-9'-]+";
	public static String CRLF_PATTERN = "[\\r\\n]+";
	public static boolean isEmpty(String str) {
		if (str != null && str.trim().length()>0) return false;
		else return true;
	}
	
	public static String trim(String str) {
		if (str != null && str.trim().length()>0) return str.trim().toLowerCase();
		else return "";
	}
}