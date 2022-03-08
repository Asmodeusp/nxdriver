package com.saimawzc.freight.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScrollGridview extends GridView {

    public NoScrollGridview(Context context) {
        super(context);
    }

    public NoScrollGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoScrollGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
