package com.saimawzc.freight.weight.utils.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Allen Liu on 2017/2/23.
 */
public class EjectDialog extends Dialog {
    private int res;

    public EjectDialog(Context context, int theme, int res) {
        super(context, theme);
        // TODO 自动生成的构造函数存根
        setContentView(res);
        this.res = res;

        setCanceledOnTouchOutside(false);

    }

}