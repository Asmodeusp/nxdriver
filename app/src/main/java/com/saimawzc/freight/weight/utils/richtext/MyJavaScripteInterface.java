package com.saimawzc.freight.weight.utils.richtext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;

public class MyJavaScripteInterface {
    private Context context;

    public MyJavaScripteInterface(Context context) {
        this.context = context;
    }

    /**
     * 接口返回的方式
     */
    @android.webkit.JavascriptInterface
    public void openImage(String img, ArrayList<String> mPicList) {
        Intent intent = new Intent(context, PlusImageActivity.class);
        intent.putStringArrayListExtra("imglist", mPicList);
        intent.putExtra("currentpos", 0);
        context.startActivity(intent);

    }
    ArrayList<String> list;
    /**
     * 加载时获取资源的方式
     */
    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        if(list==null){
            list=new ArrayList<>();
        }
        list.clear();
        list.add(img);
        if(list.size()<=0){
            return;
        }
        Intent intent = new Intent(context, PlusImageActivity.class);
        intent.putStringArrayListExtra("imglist", list);
        intent.putExtra("currentpos", 0);
        context.startActivity(intent);
        Log.e("msg",img);
    }

    /**
     * 加载时获取资源的方式
     */
    @android.webkit.JavascriptInterface
    public void openVideo(String video) {
      Log.e("msg","视频地址"+video);

    }


}
