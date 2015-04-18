package com.library.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author lincoln
 * 
 */
public class DateFormatUtil {

	/**
	 * 把时间戳timeStamp(单位秒)转换成固定格式的字符串
	 * 
	 * @param dataFormat
	 *            :yyyy-MM-dd HH:mm:ss E
	 * @param timeStamp
	 * @return
	 */
	public static String formatData(String dataFormat, long timeStamp) {
		if (timeStamp == 0) {
			return "";
		}
		timeStamp = timeStamp * 1000;
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat(dataFormat);
		result = format.format(new Date(timeStamp));
		return result;
	}
	/**
	 * 当前时间戳,单位毫秒，不需要*1000
	 * @param dataFormat
	 * @param TimeMillis
	 * @return
	 */
	public static String formatDataTimeMillis(String dataFormat, long TimeMillis) {
		String result = "";
		SimpleDateFormat format = new SimpleDateFormat(dataFormat);
		result = format.format(new Date(TimeMillis));
		return result;
	}

	/**
	 * 时间展示：显示xx分钟前
	 */
	private static long ONE_MINUTE = 1 * 60 * 1000;// 1分钟
	private static long ONE_HOUR = 1* 60* ONE_MINUTE;// 1小时
	

	public static String supriseFormatDate(String dataFormat, long createTimeStamp) {
		String result = "";
		long timeStampCurrent = System.currentTimeMillis();
		long timeStampCreate = createTimeStamp * 1000;
		if (timeStampCurrent - timeStampCreate <= ONE_MINUTE / 2) {
			result = "刚刚";
		} else if (timeStampCurrent - timeStampCreate < ONE_MINUTE) {
			result = "30秒前";
		} else if (timeStampCurrent - timeStampCreate < 2*ONE_MINUTE) {
			result = "1分钟前";
		} else if (timeStampCurrent - timeStampCreate < 3*ONE_MINUTE) {
			result = "2分钟前";
		} else if (timeStampCurrent - timeStampCreate < 4*ONE_MINUTE) {
			result = "3分钟前";
		} else if (timeStampCurrent - timeStampCreate < 5*ONE_MINUTE) {
			result = "4分钟前";
		} else if (timeStampCurrent - timeStampCreate < 6*ONE_MINUTE) {
			result = "5分钟前";
		} else if (timeStampCurrent - timeStampCreate < 7*ONE_MINUTE) {
			result = "6分钟前";
		} else if (timeStampCurrent - timeStampCreate < 8*ONE_MINUTE) {
			result = "7分钟前";
		}else if (timeStampCurrent - timeStampCreate < 9*ONE_MINUTE) {
			result = "8分钟前";
		}else if (timeStampCurrent - timeStampCreate < 10*ONE_MINUTE) {
			result = "9分钟前";
		}else if (timeStampCurrent - timeStampCreate < 30*ONE_MINUTE) {
			result = "10分钟前";
		}else if (timeStampCurrent - timeStampCreate < 1*ONE_HOUR) {
			result = "半小时前";
		}else if (timeStampCurrent - timeStampCreate < 2*ONE_HOUR) {
			result = "1小时前";
		}else if (timeStampCurrent - timeStampCreate < 3*ONE_HOUR) {
			result = "2小时前";
		}else if (timeStampCurrent - timeStampCreate < 4*ONE_HOUR) {
			result = "3小时前";
		}else if (timeStampCurrent - timeStampCreate < 24*ONE_HOUR &&(formatData("dd", timeStampCreate).equals(formatData("dd", timeStampCurrent))) ) {
			result = "今天";
		}else {
			result = formatData(dataFormat, createTimeStamp);
		}
		return result;
	}
	

	/**
	 * 获得年份
	 */
	public static int getYear(Calendar calendar) {
		int month = 0;
		month = calendar.get(Calendar.YEAR) ;
		return month;
	};

	/**
	 * 获得月份
	 * 
	 */
	public static int getMonth(Calendar calendar) {
		int month = 0;
		month = calendar.get(Calendar.MONTH) + 1;
		return month;
	};

	/**
	 * 获得本月第几天
	 */
	public static int getDay(Calendar calendar) {
		int month = 0;
		month = calendar.get(Calendar.DAY_OF_MONTH) ;
		return month;
	};



}
