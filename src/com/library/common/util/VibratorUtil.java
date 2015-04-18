package com.library.common.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 手机震动
 * 
 * @author lincoln
 * 
 */
public class VibratorUtil {
	public static void vibrator(final Context context, long millionSeconds) {
		Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(millionSeconds);
	}
}
