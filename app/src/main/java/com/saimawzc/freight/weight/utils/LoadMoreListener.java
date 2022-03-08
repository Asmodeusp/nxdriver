package com.saimawzc.freight.weight.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * package: com.easyandroid.sectionadapter.listener.LoadMoreListener
 * author: nsj
 * description:继承Recyclerview的滚动事件，实现上拉加载
 * time: create at 2017/7/7 22:23
 */

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {

    public boolean isLoading = false;//记录正在加载的状态，防止多次请求
    protected int lastItemPosition;
    private int topOffset = 0;//列表顶部容差值
    private int bottomOffset = 0;//列表底部容差值

    protected LinearLayoutManager layoutManager;

    public LoadMoreListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //查找最后一个可见的item的position

       if (isFullAScreen(recyclerView)) {
            lastItemPosition = layoutManager.findLastVisibleItemPosition();
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 ==
                    layoutManager.getItemCount()) {
                if (!isLoading) {
                    onLoadMore();
                }
            }
        }else {
       }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    /**
     * 检查是否满一屏
     *
     * @param recyclerView
     * @return
     */
    public boolean isFullAScreen(RecyclerView recyclerView) {
        int visiableItemCount = recyclerView.getChildCount();
        if(visiableItemCount > 0){
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //屏幕中最后一个可见子项的position
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            //当前屏幕所看到的子项个数
            int visibleItemCount = layoutManager.getChildCount();
            //当前RecyclerView的所有子项个数
            int totalItemCount = layoutManager.getItemCount();
            //RecyclerView的滑动状态
            int state = recyclerView.getScrollState();
            if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }



    }

    public abstract void onLoadMore();

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }
}
