package com.saimawzc.freight.modle.wallet;


import com.saimawzc.freight.view.wallet.WithDrawView;

public interface WithDrawModel {

    void getCode(WithDrawView view);

    void withDraw(WithDrawView view,String money,String code);
}
