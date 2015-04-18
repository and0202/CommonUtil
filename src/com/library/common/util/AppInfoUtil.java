package com.library.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 获取应用本身的信息
 * 
 * @author lincoln
 * 
 */
public class AppInfoUtil {
	/**
	 * 获得应用Code
	 */
	public static int getAppVersionCode(Context context) {
		int result = 0;
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			result = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得版本Name
	 */
	public static String getAppVersionName(Context context) {
		String result = "";
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			result = pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得应用的包名
	 */
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	/**
	 *  获得渠道名
	 */
	public static String getChannelCode(Context context) {
		String code = getMetaData(context, "UMENG_CHANNEL");
		if (code != null) {
			return code;
		}
		return "C_000";
	}

	/**
	 * 获得MetaData
	 */
	private static String getMetaData(Context context, String key) {
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			Object value = ai.metaData.get(key);
			if (value != null) {
				return value.toString();
			}
		} catch (Exception e) {
			//
		}
		return null;
	}
}
