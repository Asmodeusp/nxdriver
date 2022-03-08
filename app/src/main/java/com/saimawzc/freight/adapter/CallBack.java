package com.saimawzc.freight.adapter;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2020/8/3.
 *
 * recycleView 侧滑删除
 */

public class CallBack extends ItemTouchHelper.Callback {
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                         int direction) {
        /**
         * call max distance start onSwiped call
         */
    }
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            /**
             * get {@link TextView#getWidth()}
             */

            ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
            TextView textView = (TextView) viewGroup.getChildAt(1);
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            if (Math.abs(dX) <= layoutParams.width) {
                /**
                 * move {@link RecyclerView.ViewHolder} distance
                 */
                viewHolder.itemView.scrollTo((int) -dX, 0);
                /**
                 * callAction or register click bind view
                 */
            }
        }
    }
}
