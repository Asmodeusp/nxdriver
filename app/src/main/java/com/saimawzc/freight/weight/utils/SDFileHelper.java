package com.saimawzc.freight.weight.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.saimawzc.freight.weight.waterpic.ImageUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2018-06-29.
 */

public class SDFileHelper {
    private Context context;
    private int type=1;

    public SDFileHelper() {
    }

    public SDFileHelper(Context context) {
        super();
        this.context = context;
    }
    public SDFileHelper(Context context,int type) {
        super();
        this.type=type;
        this.context = context;
    }
    //Glide保存图片
    public void savePicture(final String fileName, String url){
        Glide.with(context.getApplicationContext()).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaFileToSD(fileName,bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //往SD卡写入文件的方法
    public String savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath;
            if(type==1){
                filePath = ImageUtil.getSystemPhotoPath((Activity) context);
            }else if(type==2){
                filePath= ImageUtil.getSystemPhotoPath((Activity) context);
            }else {
                filePath = ImageUtil.getSystemPhotoPath((Activity) context);
            }

            File dir1 = new File(filePath);
            if (!dir1.exists()){
                dir1.mkdirs();
            }
            filename = filePath+ "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
           //Toast.makeText(context, "图片已成功保存到"+filePath, Toast.LENGTH_SHORT).show();
            return filename;

        } else{
            Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
            return "";
        }

    }



}
