package com.saimawzc.freight.weight.utils;

import cn.finalteam.galleryfinal.FunctionConfig;

/**
 * Created by Administrator on 2017/2/21.
 */

public class GalleryUtils {

    public static FunctionConfig functionConfig;

     public static FunctionConfig getFbdtFunction(){
         functionConfig = new FunctionConfig.Builder()
                 .setEnableCamera(false)//开启相机功能
                 .setCropHeight(100)
                 .setCropHeight(100)
                 .setCropSquare(true)//裁剪正方形
                 .setRotateReplaceSource(true)
                 .setForceCropEdit(true)
                 .setEnablePreview(true)
                 .setMutiSelectMaxSize(9)
                 .build();
         return functionConfig;
     }


    //自定义选择图片张数
    public static  FunctionConfig getFbdtFunction(int picSize){
         functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)//开启相机功能
                .setCropHeight(100)
                .setCropHeight(100)
                .setCropSquare(true)//裁剪正方形
                .setRotateReplaceSource(true)
                .setForceCropEdit(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(picSize)
                .build();
        return functionConfig;
    }


    public static FunctionConfig getCameratFunction(){
         functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)//开启相机功能
                .setCropHeight(100)
                .setCropHeight(100)
                .setCropSquare(true)//裁剪正方形
                .setRotateReplaceSource(true)
                .setForceCropEdit(true)
                .setEnableEdit(true)
                .setForceCropEdit(true)
                .setEnablePreview(true)
                .build();
        return functionConfig;
    }
    public static FunctionConfig getFbdtFunctionSingle(){
         functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)//开启相机功能
                .setCropHeight(100)
                .setCropHeight(100)
                .setCropSquare(true)//裁剪正方形
                .setRotateReplaceSource(true)
                .setForceCropEdit(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(1)
                .build();
        return functionConfig;
    }
}
