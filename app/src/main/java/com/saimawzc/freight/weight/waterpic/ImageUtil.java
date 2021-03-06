package com.saimawzc.freight.weight.waterpic;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.saimawzc.freight.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.saimawzc.freight.weight.waterpic.WatermarkSettings.mContext;

/**
 * 图片处理工具类
 * @author xuxu
 */
public class ImageUtil {

	//保存图片文件
	public static String saveToFile(String fileFolderStr, boolean isDir,  Bitmap croppedImage) throws IOException {
		if(croppedImage==null){
			return  null;
		}
		 File jpgFile;
		if (isDir) {
			File fileFolder = new File(fileFolderStr);
			String filename = generateImageName();
			if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
				fileFolder.mkdir();
			}
			jpgFile = new File(fileFolder, filename);
		} else {
			jpgFile = new File(fileFolderStr);
			if (!jpgFile.getParentFile().exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
				FileUtils.makeFolders(jpgFile.getParentFile().getPath());
				jpgFile.mkdirs();
			}
		}
		int options = 80;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		croppedImage.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / (1024 * 1024)> 1) {
			baos.reset();
			options -= 10;
			croppedImage.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
		outputStream.write(baos.toByteArray());
		outputStream.flush();
		outputStream.close();
		return jpgFile.getPath();
	}

	/**
	 * 获取图片路径
	 * @param activity
	 * @param uri
	 * @return
	 */
	public static String getPath(Activity activity, Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
		if(cursor == null)
			return null;
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	/**
	 * 得到bitmap的大小
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
			return bitmap.getByteCount();
		}
		// 在低版本中用一行的字节x高度
		return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
	}
	/**
	 * 解决小米手机上获取图片路径为null的情况
	 * @param intent
	 * @return
     */
	public static Uri getPictureUri(Context context, Intent intent) {
		Uri uri = intent.getData();
		String type = intent.getType();
		if (uri.getScheme().equals("file") && (type.contains("image/"))) {
			String path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				ContentResolver cr = context.getContentResolver();
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
						.append("'" + path + "'").append(")");
				Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						new String[] { MediaStore.Images.ImageColumns._ID },
						buff.toString(), null, null);
				int index = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
					// set _id value
					index = cur.getInt(index);
				}
				if (index == 0) {
					// do nothing
				} else {
					Uri uri_temp = Uri
							.parse("content://media/external/images/media/"
									+ index);
					if (uri_temp != null) {
						uri = uri_temp;
					}
				}
			}
		}
		return uri;
	}

	/**
	 * 保存图片到本地
	 * @param context
	 * @param filePath
	 */
	public static void saveImageToGallery(Context context, String filePath) {
	    File file = new File(filePath); 
	    // 其次把文件插入到系统图库
	    try {
	    	MediaStore.Images.Media.insertImage(context.getContentResolver(),
	    			file.getAbsolutePath(), null, null);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    // 最后通知图库更新
	    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
	}
	
	/**
	 * 压缩图片
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(String path) {
		Bitmap bitmap = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(
					new File(path)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;
			while (true) {
				if ((options.outWidth >> i <= 1000)
						&& (options.outHeight >> i <= 1000)) {
					in = new BufferedInputStream(
							new FileInputStream(new File(path)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 获取图片
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap getImage(String path) {
		Uri uri = Uri.parse(path);
		Bitmap bmp = BitmapFactory.decodeFile(uri.toString());
		return bmp;
	}
	
	/**
	 * 生成图片名称
	 */
	public static String generateImageName() {
		return "aqcheck"+UUID.randomUUID().toString() + ".jpg";
	}

	/**
	 * 系统相册路径
	 * @return
     */
	public static String getSystemPhotoPath(Activity activity) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
			return activity.getExternalFilesDir("Caches").getAbsolutePath()+"/nxdriver/";
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/nxDriver";
	}

}
