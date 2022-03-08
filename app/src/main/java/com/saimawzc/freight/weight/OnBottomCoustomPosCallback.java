package com.saimawzc.freight.weight;

import android.graphics.RectF;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.position.OnBaseCallback;

/**
 * Created by caizepeng on 16/8/20.
 */
public  class OnBottomCoustomPosCallback extends OnBaseCallback{
    public OnBottomCoustomPosCallback() {
    }

    public OnBottomCoustomPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.rightMargin = rightMargin+200;
        marginInfo.topMargin = rectF.top + rectF.height()+offset;
    }

}
