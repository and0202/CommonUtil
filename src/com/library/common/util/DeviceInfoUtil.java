package com.library.common.util;

import java.io.DataOutputStream;
import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

/**
 * 获取手机设备本身的信息
 * 
 * @author lincoln
 * 
 */
public class DeviceInfoUtil {
	/**
	 * isRoot(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean isRoot() {
		boolean result = false;
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();

			int exitValue = process.waitFor();

			if (exitValue == 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 判断当前手机是否有ROOT权限,不显示root选择对话框
	 * 
	 * @return
	 */
	public static boolean isRootNotShow() {
		boolean bool = false;
		try {
			if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
				bool = false;
			} else {
				bool = true;
			}
			Logger.d("bool = " + bool);
		} catch (Exception e) {

		}
		return bool;
	}

	/**
	 * 获得移动国家码
	 * 
	 * @return
	 */
	public static int getMcc(Context context) {
		int result = 0;
		try {
			TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String networkOperator = tel.getNetworkOperator();
			if (networkOperator != null && networkOperator.length() == 15) {
				result = Integer.parseInt(networkOperator.substring(0, 3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 获得移动网络码
	 * 
	 * @return
	 */
	public static int getmnc(Context context) {
		int result = 0;
		try {
			TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String networkOperator = tel.getNetworkOperator();

			if (networkOperator != null && networkOperator.length() == 15) {
				result = Integer.parseInt(networkOperator.substring(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	/**
	 * 检查SD卡是否可用
	 * 
	 * @return TRUE:可用;FALSE:不可用
	 */
	public static boolean externalMemoryAvailable() {
		return (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()));
	}
}
