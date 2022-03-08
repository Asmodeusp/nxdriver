package com.saimawzc.freight.view.mine.person;

import android.content.Context;

import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/7/31.
 */

public interface ChangeRoleView extends BaseView {

    Context getContext();
    void oncomplete(int role);
}
