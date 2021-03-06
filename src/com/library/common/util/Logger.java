package com.library.common.util;

import android.util.Log;

/**
 * Logger 输出工具类
 * @author lincoln
 *
 */
public class Logger {
	/**
	 * 测试状态下为true，输出日志；正式上线时为false，不输出日志
	 */
	public static boolean DEBUG = true;
	
	/**
	 * tag
	 */
	private static String tag = "lincoln";
	
	/**
	 * 正常输出日志
	 * @param content
	 */
	public static void d(String content){
		if (DEBUG) {
			if (content == null || content.equals("")) {
				return ;
			}
			Log.d(tag, content);
		}
	}
	public static void d(String tag,String content){
		if (DEBUG) {
			if (content == null || content.equals("")) {
				return ;
			}
			Log.d(tag, content);
		}
	}
	
}
