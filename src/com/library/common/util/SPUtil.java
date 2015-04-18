package com.library.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {
	/**
	 * 向SP中写long
	 */
	public static void putLong(Context context, String spName, String key,long value) {
		SharedPreferences sPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		Editor editor = sPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	/**
	 * 从SP中读数据long
	 */
	public static long getLong(Context context, String spName, String key) {
		SharedPreferences sPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		return sPreferences.getLong(key, 0);
	}
	
	
	/**
	 * 向SP中写long
	 */
	public static void putString(Context context, String spName, String key,String value) {
		SharedPreferences sPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		Editor editor = sPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	/**
	 * 从SP中读数据long
	 */
	public static String getString(Context context, String spName, String key) {
		SharedPreferences sPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		return sPreferences.getString(key, "");
	}
	
	/**
	 * 写入boolean值
	 */
	public static void putBoolean(Context context, String spName, String key,boolean value){
		SharedPreferences sPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		Editor editor = sPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * 从SP中读Boolean
	 */
	public static boolean getBoolean(Context context, String spName, String key) {
		SharedPreferences sPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		return sPreferences.getBoolean(key, false);
	}
	
}
