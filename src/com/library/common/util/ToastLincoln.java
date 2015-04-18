package com.library.common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast，方便扩展
 * 
 * @author lincoln
 * 
 */
public class ToastLincoln {
	private static Toast mToast;
//	private Context context;

	private ToastLincoln(Context context) {
		if (mToast == null) {
//			this.context = context;
			mToast = new Toast(context);
		}
	}

	public static void ShowLong(Context context, String content) {
		mToast.makeText(context, content, mToast.LENGTH_LONG).show();
	}

	public static void ShowShort(Context context, String content) {
		mToast.makeText(context, content, mToast.LENGTH_SHORT).show();
	}

	public static void ShowLong(Context context, int resId) {
		String content = context.getResources().getString(resId);
		mToast.makeText(context, content, mToast.LENGTH_LONG).show();
	}

	public static void ShowShort(Context context, int resId) {
		String content = context.getResources().getString(resId);
		mToast.makeText(context, content, mToast.LENGTH_SHORT).show();
	}
}
