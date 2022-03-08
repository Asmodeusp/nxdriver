package com.saimawzc.freight.view.wallet;

import com.saimawzc.freight.view.BaseView;

public interface WithDrawView extends BaseView {
    void getCode(String code);
    void oncomplete(int status);
}
