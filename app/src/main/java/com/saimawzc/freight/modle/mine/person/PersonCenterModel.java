package com.saimawzc.freight.modle.mine.person;

import android.content.Context;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.mine.person.PersonCenterView;

/**
 * Created by Administrator on 2020/8/8.
 * 个人中心
 */

public interface PersonCenterModel {

    void changePic(PersonCenterView view, BaseListener listener);
    void changeSex(PersonCenterView view, BaseListener listener);
    void showCamera( Context context, final BaseListener listener);

}
