package com.saimawzc.freight.adapter.my;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saimawzc.freight.R;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.widget.zoonview.PhotoView;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * 广告
 * content :
 */
public class LoadImageAdatper extends PagerAdapter {

    private Context mcontext;
    private List<String> murls;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public LoadImageAdatper(Context context, List<String> urls) {
        mcontext = context;
        murls = urls;
    }
    @Override
    public int getCount() {
        if (murls == null) return 0;
        return murls.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout mitemView = (LinearLayout) LayoutInflater.from(mcontext).inflate(R.layout.itme_lookimg, null);
        PhotoView imageView = (PhotoView) mitemView.findViewById(R.id.image);
        String img= murls.get(position);
            if(img.contains("http")){
                ImageLoadUtil.imageLoaderProxy.displayImage(mcontext, img, imageView);
            }else {
                Uri uri = Uri.fromFile(new File(img));
                ImageLoadUtil.displayImage(mcontext, uri, imageView);
            }
        container.addView(mitemView);
        if(onItemClickListener!=null){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 注意：位置是position
                    onItemClickListener.onItemClick(v, position);

                }
            });
        }
        return mitemView;

    }


    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
