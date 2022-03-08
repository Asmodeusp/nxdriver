package com.saimawzc.freight.weight;

import android.graphics.RectF;
import android.util.Log;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.position.OnBaseCallback;

/**
 * Created by caizepeng on 16/8/20.
 */
public class OnRightCountomPosCallback extends OnBaseCallback {
    public OnRightCountomPosCallback() {
    }

    public OnRightCountomPosCallback(float offset) {
        super(offset);
    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = rectF.right + offset;
        marginInfo.topMargin = rectF.top-90;
    }
}
