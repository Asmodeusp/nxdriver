package com.saimawzc.freight.weight;

import android.graphics.RectF;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.position.OnBaseCallback;

/**
 * Created by caizepeng on 16/8/20.
 */
public class OnTopCoutomPosCallback extends OnBaseCallback {

    public OnTopCoutomPosCallback() {
    }

    public OnTopCoutomPosCallback(float offset) {
        super(offset);

    }

    @Override
    public void getPosition(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
        marginInfo.leftMargin = offset ;
        marginInfo.bottomMargin = bottomMargin+rectF.height()+20;
    }
}
