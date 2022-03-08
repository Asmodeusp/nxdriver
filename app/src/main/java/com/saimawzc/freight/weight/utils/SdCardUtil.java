package com.saimawzc.freight.weight.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.widget.Toast;


import com.saimawzc.freight.weight.utils.app.DeviceUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SdCardUtil {

    public static String catch_path = ""; // 应用的cache目录用于存放缓存
    public static final String PROJECT_FILE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/" + "winclient" + "/"; // 项目路径
    public static final String DEFAULT_PHOTO_PATH = PROJECT_FILE_PATH + "pics/";
    public static final String DEFAULT_RECORD_PATH = PROJECT_FILE_PATH + "record/";
    public static String TEMP = "file:///" + PROJECT_FILE_PATH + "camera.jpg";

    /**
     * 判断是否有sd卡
     */
    public static boolean checkSdState() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    /**
     * 获取Sd卡路径
     */
    public static String getSd() {
        if (!checkSdState()) {
            return "";
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取相册路径
     */
    public static String getDCIM() {
        if (!checkSdState()) {
            return "";
        }
        String path = getSd() + "/dcim/";
        if (new File(path).exists()) {
            return path;
        }
        path = getSd() + "/DCIM/";
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return "";
            }
        }
        return path;
    }

    /**
     * 获取DCIM目录下的Camera目录
     */
    public static String getCamera() {
        if (!checkSdState()) {
            return "";
        }
        String path = getDCIM() + "/Camera/";
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return "";
            }
        }
        return path;
    }

    /**
     * 获取app缓存目录
     */
    public static String getCacheDir(Context context) {
        if (!checkSdState()) {
            return "";
        }
        return getSd() + "/Android/data/" + context.getPackageName() + "/cache/";
    }

    /**
     * 获取app目录
     */
    public static String getPrjFileDir(Context context) {
        if (!checkSdState()) {
            return "";
        }
        String path = getSd() + File.separator + DeviceUtil.getApplicationName(context) + File.separator;
        File projectDir = new File(path);
        if (!projectDir.exists()) {
            if (!projectDir.mkdirs()) {
                return "";
            }
        }
        return path;
    }

    /**
     * 相册目录下的图片路径
     */
    public static String getSysImgPath() {
        if (!checkSdState()) {
            return "";
        }
        return getCamera() + "IMG_" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * app目录下的图片路径
     */
    public static String getAppImgPath(Context context) {
        String prj = getPrjFileDir(context);
        if ("".equals(prj)) {
            return "";
        }
        return prj + "IMG_" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 获取缓存图片路径
     *
     * @param context
     * @return
     */
    public static String getCacheImage(Context context) {
        return getCacheDir(context) + "IMG_" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 获取拓展存储Cache的绝对路径
     *
     * @param context
     */
    public static String getExternalCacheDir(Context context) {
        if (!SdCardUtil.checkSdState())
            return null;
        StringBuilder sb = new StringBuilder();
        File file = context.getExternalCacheDir();
        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context
                    .getPackageName())
                    .append("/cache/").append(File.separator).toString();
        }
        return sb.toString();
    }

    public static String getCacheTempImage(Context context) {
        if(Build.VERSION.SDK_INT<30){
            return getExternalCacheDir(context) + System.currentTimeMillis() + ".jpg";
        }else {
            return  createImageFile(context).getAbsolutePath();
        }

    }
    public static String getCacheTempvideo(Context context) {
        return getExternalCacheDir(context) + System.currentTimeMillis() + ".mp4";
    }
    /**
     * 初始化文件目录
     */
    public static void initFileDir(Context context) {
        File projectDir = new File(PROJECT_FILE_PATH);
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }
        File fileDir = new File(DEFAULT_PHOTO_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File recordDir = new File(DEFAULT_RECORD_PATH);
        if (!recordDir.exists()) {
            recordDir.mkdirs();
        }
        catch_path = Environment.getExternalStorageDirectory().getPath()
                + "/Android/data/" + context.getPackageName() + "/cache/";
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createImageFile(Context mContext)  {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir;
        storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists())storageDir.mkdirs();
        File tempFile = new File(storageDir, imageFileName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }

        return tempFile;
    }

}
