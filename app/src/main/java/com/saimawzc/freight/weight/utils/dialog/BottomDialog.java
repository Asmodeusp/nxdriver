package com.saimawzc.freight.weight.utils.dialog;

import android.content.Context;
import android.view.Window;

import com.saimawzc.freight.R;

/**
 * Created by Administrator on 2018-03-26.
 */

public class BottomDialog extends EjectDialog {

    private Window window = null;

    public BottomDialog(Context context, int theme, int res) {
        super(context, theme, res);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.mainfstyle); //设置窗口弹出动画

    }

}
