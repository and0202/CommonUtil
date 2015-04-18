package com.library.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class ImageUtil {
	/**
	 * 将bitmap类型的数据存入sd卡某个目录
	 * 
	 * @param bitmap
	 *            :要存入的位图，
	 * @param filePath
	 *            :文件的目录
	 * @param fileName
	 *            :文件名
	 * @return 如果为true,存入sd卡目录,如果为false，代表没成功
	 */
	public static boolean saveBitmapToSD(Bitmap bitmap, String filePath, String fileName) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 如果sd卡有效
			FileOutputStream fos;
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(filePath, fileName);
			Logger.d("saveBitmapToSD():fileFullPath=" + file.getAbsolutePath());
			file.deleteOnExit();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 没有可写权限
			if (!file.canWrite()) {
				Logger.d("saveBitmapToSD():Can't write [" + file.getAbsolutePath() + "]");
				return false;
			}
			try {
				file.createNewFile();
				fos = new FileOutputStream(file);
				if (bitmap.compress(CompressFormat.JPEG, 80, fos)) { // 如果保存成功
					fos.flush();
					fos.close();
					return true;
				}
			} catch (Exception e) {
				Logger.d("saveBitmapToSD():未保存到相框" + e.toString());
				return false;
			}
		}
		return false;
	}

	/**
	 * 保存图片到相册
	 * 
	 */
	public static boolean saveBitmapToGallery(Bitmap bitmap, String name) {
		String GalleryPath = "/DCIM/Camera"; // 相册路径
		String filePath = Environment.getExternalStorageDirectory() + GalleryPath;
		return saveBitmapToSD(bitmap, filePath, name);
	}

	public static boolean isFileExist( String filePath, String fileName) {
		boolean result = false;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 如果sd卡有效
			try {
				File file = new File(filePath, fileName);
				if (file.exists()) {
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
