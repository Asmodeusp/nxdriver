package com.saimawzc.freight.weight.utils.loadimg;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.saimawzc.freight.R;


import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * 作者：tryedhp on 2017/9/28/0028 16:00
 * 邮箱：try2017yx@163.com
 */

public class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView,
                             Drawable defaultDrawable, int width, int height) {
        Glide.with(activity.getApplication())
                .load("file://" + path)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                .skipMemoryCache(true)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        try{
                            imageView.setImageDrawable(resource);
                        }catch (Exception e){

                        }

                    }


                    @Override
                    public void setRequest(Request request) {
                        imageView.setTag(R.id.adapter_item_tag_key, request);
                    }


                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                    }
                });
    }


    @Override
    public void clearMemoryCache() {

    }
}
