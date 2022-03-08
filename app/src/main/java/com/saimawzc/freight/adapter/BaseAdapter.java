package com.saimawzc.freight.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.saimawzc.freight.base.BaseActivity;


/**
 * Created by yyfy_yf on 2017/12/20.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    public Context mContext;
    protected BaseActivity activity;

    public static  boolean IS_RESH = false;

    //上拉加载默认状态--默认为-1
    public int load_more_status = -1;  //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //加载完成
    public static final int LOADING_FINISH = 2;

    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * @param status 更换加载状态
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
    /**
     * 覆写startactivity方法，加入切换动画
     */
    public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(mContext, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);

    }

    /**
     * 覆写startactivity方法，加入切换动画,从下方出来
     */
    public void startActivityBottom(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(mContext, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);

    }

    /**
     * 成为主播需要的页面
     * @param bundle
     * @param target
     */
    public void startActivityLive(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(mContext, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext. startActivity(intent);
    }


    /*****
     * 设置按钮样式
     * */
    public void setBtnStyle(Context context,TextView textView, int draw,int color){
        textView.setTextColor(context.getResources().getColor(color));
        textView.setBackgroundResource(draw);
    }





}
