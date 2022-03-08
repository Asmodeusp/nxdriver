package com.saimawzc.freight.base;

/**
 * Created by Administrator on 2020/7/30.
 */

public interface BaseListener {

    void successful();

    void onFail(String str);

    void successful(int type);
}
