package com.library.common.util;

public class StringUtil {
	/**
	 * @param input
	 *            输入字符串
	 * @return 切割好的字符串 切割URL字符串，变成文件名
	 */
	public static String getFileName(String src) {
		int start = src.lastIndexOf("/") + 1;
		int end = src.length();
		String fileName = src.substring(start, end);
		// System.out.println("文件名 " + fileName);
		return fileName;
	}
}
