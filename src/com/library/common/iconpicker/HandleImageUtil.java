package com.library.common.iconpicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.library.common.util.Logger;

public class HandleImageUtil {
	public static String onActivityResult(Activity activity, Intent data) {
		Uri uri = data.getData();
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(uri, proj, // Which columns to
															// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)

		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		String path = cursor.getString(column_index);
		Logger.d("select_path:" + path);
		return path;
	}

	/**
	 * 获得缩放后的图片
	 */
	public static Bitmap getZoomBitmap(String filePath, int imageWidth, int imageHeight) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, option);
		int sampleSize = calculateInSampleSize(option, imageWidth, imageHeight);
		option.inJustDecodeBounds = false;

		option.inSampleSize = sampleSize;
		Logger.d("缩放比例：" + sampleSize);
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, option);
		Logger.d("宽高比：" + bitmap.getWidth() + " " + bitmap.getHeight());

		int degrees = getExifOrientation(filePath);
		return getRotateBitmap(bitmap, degrees);
	}

	/**
	 * 获得缩放后的图片 sampleSize：缩放比例
	 */
	public static Bitmap getZoomBitmap(String filePath, int sampleSize) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inSampleSize = sampleSize;
		Logger.d("缩放比例：" + sampleSize);
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, option);
		Logger.d("宽高比：" + bitmap.getWidth() + " " + bitmap.getHeight());

		int degrees = getExifOrientation(filePath);
		return getRotateBitmap(bitmap, degrees);
	}

	/**
	 *图片进行缩放
	 */
	public static boolean getZoomBitmapPath(String originImagePath ,String adjustDirectoryPath, String adjustFileName, int sampleSize) {
		boolean result = false;
		Bitmap bitmap = getZoomBitmap(originImagePath, sampleSize);
		boolean isSaveSuccess = saveBitmapToSD(bitmap, adjustDirectoryPath, adjustFileName);
		if (isSaveSuccess) {
			result = true;
		}
		bitmap.recycle();
		return result;
	}

	/**
	 * 计算缩放比例
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 获得图片需要旋转的角度
	 */
	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			Logger.d("cannot read exif" + ex.getMessage());
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	/**
	 * 获得最终图片
	 * 
	 * @param bitmap
	 * @param degrees
	 * @return
	 */
	private static Bitmap getRotateBitmap(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
				if (bitmap != b2) {
					bitmap = b2;
				}
			} catch (OutOfMemoryError ex) {
				Logger.d("Out of memory!");
			}
		}
		return bitmap;
	}

	/**
	 * 根据图片路径获得Bitmap的大小
	 * 
	 * @param bitmapPath
	 * @return
	 */
	public static long getBitmapSize(String bitmapPath) {
		try {
			File file = new File(bitmapPath);
			FileInputStream fileInputStream = new FileInputStream(file);
			return fileInputStream.available();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

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
			boolean deleteState = file.delete();
			Logger.d("saveBitmap:delete" + deleteState);
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
				if (bitmap.compress(CompressFormat.PNG, 80, fos)) { // 如果保存成功
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
}
