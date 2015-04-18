package com.library.common.iconpicker;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.format.DateFormat;

import com.library.common.util.Logger;

public abstract class LincolnImageUtil {
	private String ImageDirectory = Environment.getExternalStorageDirectory() + "";
	private String OriginImageName = "zhong" + DateFormat.format("yyyy-MM-dd", new Date()) + ".jpg";
	private String AdjustImageName = "zhong" + DateFormat.format("yyyy-MM-dd", new Date()) + "_adjust" + ".jpg";

	private String OriginalImageAbsolutePath = ImageDirectory + "/" + OriginImageName;
	private String AdjustImageAbsolutePath = ImageDirectory + "/" + AdjustImageName;

	public static Activity activity;
	public static final int REQUEST_CODE_LOCAL_IMG = 101;
	public static final int REQUEST_CODE_CAPTURE_IMG = 102;
	public static final int REQUEST_CODE_IMAGE_CROP = 103;

	private long maxKB = 1 * 1024 * 1024;// 1M
	private long minKB = 0;

	public LincolnImageUtil(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 开始工作
	 * 
	 * @param FromLocal
	 *            ：是否是从图库选择图片
	 */
	public void startWork(boolean FromLocal) {
		if (FromLocal) {
			GetPathUtil.getPictureFromLocal(activity, REQUEST_CODE_LOCAL_IMG);
		} else {
			GetPathUtil.captureImage(activity, REQUEST_CODE_CAPTURE_IMG, OriginalImageAbsolutePath);
		}
	}

	public void setOnActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != -1) {// Result_OK=-1
			return;
		}
		String path;
		if (requestCode == REQUEST_CODE_CAPTURE_IMG) {
			path = OriginalImageAbsolutePath;
		} else {
			path = HandleImageUtil.onActivityResult(activity, data);
		}

		uploadIconToServer(path);
	}

	public abstract void uploadIconToServer(String FilePath);

	public Bitmap getBitMapByWidthHeight(String filePath, int imageWidth, int imageHeight) {
		return HandleImageUtil.getZoomBitmap(filePath, imageWidth, imageHeight);
	}

	public void setImageMaxSize(int maxSizeKB) {
		maxKB = maxSizeKB * 1024;
	}

	public void setImageMinSize(int minSizeKB) {
		minKB = minSizeKB;
	}

	/**
	 * 是否大于MaxSize
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean isMoreMaxSize(String filePath) {
		boolean result = false;
		long fileSize = HandleImageUtil.getBitmapSize(filePath);
		Logger.d("fileSize:" + fileSize + " maxSize:" + maxKB);
		if (fileSize > maxKB) {
			result = true;
		}
		return result;
	}

	/**
	 * 是否小于MinSize
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean isLessMinSize(String filePath) {
		boolean result = false;
		long fileSize = HandleImageUtil.getBitmapSize(filePath);
		if (fileSize < maxKB) {
			result = true;
		}
		return result;
	}

	/**
	 * 缩放图片
	 * 
	 * @param path
	 * @param maxSize
	 *            单位Kb
	 * @return
	 */
	public String zoomImageView(String path, long maxSize) {
		this.maxKB = maxSize;
		if (isMoreMaxSize(path)) {
			int sampleSize = (int) (HandleImageUtil.getBitmapSize(path) / maxKB) + 1;// 大于MaxSize最小缩放1/2
			Logger.d("sampleSize:" + sampleSize);
			boolean saveResult = HandleImageUtil.getZoomBitmapPath(path, ImageDirectory, AdjustImageName, sampleSize);
			if (saveResult) {
				path = AdjustImageAbsolutePath;
			}
		}
		return path;
	}

	/**
	 * Bitmap 转成byte[]
	 * @param bm
	 * @return
	 */
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
