package com.saimawzc.freight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saimawzc.freight.R;


/**
 * package:
 * author: nsj
 * description:上拉加载的footer
 * time: create at 2017/8/15
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;

    public FooterHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
    }
}
