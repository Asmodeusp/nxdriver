package com.saimawzc.freight.weight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 作者：tryedhp on 2017/9/4/0004 13:36
 * 邮箱：try2017yx@163.com
 */

public class NoData extends RelativeLayout {
    @BindView(R.id.nodata_bg)
    ImageView nodataBg;
    TextView nodataBt;
    private Context context;

    public NoData(Context context) {
        super(context);
        init(context);
    }

    public NoData(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoData(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_nodata, this);
        ButterKnife.bind(this);
        setText("呃呃!\n这里什么都没有!");
    }

    public void setText(String s) {

    }

    public void setClick(String s, OnClickListener clickListener) {
        if (!TextUtils.isEmpty(s)) {
            nodataBt.setVisibility(VISIBLE);
            nodataBt.setText(s);
            nodataBt.setOnClickListener(clickListener);
        } else {
            nodataBt.setVisibility(INVISIBLE);
        }
    }
}
