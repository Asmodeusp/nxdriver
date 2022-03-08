package com.saimawzc.freight.ui.my;

import android.os.Bundle;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.my.car.MyCarChangeFragment;
import com.saimawzc.freight.ui.my.car.MyCarFragment;
import com.saimawzc.freight.ui.my.car.ship.MyShipChangeFragment;
import com.saimawzc.freight.ui.my.car.ship.MyShipFragment;
import com.saimawzc.freight.ui.my.car.RegisterCarFragment;
import com.saimawzc.freight.ui.my.car.ship.RegisterShipFragment;
import com.saimawzc.freight.ui.my.car.SearchCarFragment;
import com.saimawzc.freight.ui.my.car.ship.SearchShipFragment;
import com.saimawzc.freight.ui.my.carrier.MyCarrierFragment;
import com.saimawzc.freight.ui.my.driver.MyDriverFragment;
import com.saimawzc.freight.ui.my.driver.SearchDriviFragment;
import com.saimawzc.freight.ui.my.identification.ChooseCarrierFragment;
import com.saimawzc.freight.ui.my.identification.DriverCarrierFragment;
import com.saimawzc.freight.ui.my.identification.NomalTaxesCarriverFragment;
import com.saimawzc.freight.ui.my.identification.PersonalCarrierFragment;
import com.saimawzc.freight.ui.my.identification.SmallCompanyCarrierFragment;
import com.saimawzc.freight.ui.my.lesses.MyLessesFragment;
import com.saimawzc.freight.ui.my.set.MySetFragment;
import com.saimawzc.freight.ui.my.set.keepLiveSetFragment;
import com.saimawzc.freight.ui.my.set.suggest.AddSuggetsFragment;
import com.saimawzc.freight.ui.my.set.suggest.SuggestDealDelationFragment;
import com.saimawzc.freight.ui.my.set.suggest.SuggestListFragment;
import com.saimawzc.freight.ui.my.setment.MySettlementFragment;
import com.saimawzc.freight.ui.my.person.PersonCenerFragment;
import com.saimawzc.freight.ui.sendcar.driver.SendCarSearchFragment;
import com.saimawzc.freight.ui.wallet.BankBindCardFragment;
import com.saimawzc.freight.ui.wallet.ChooseBigBankFragment;
import com.saimawzc.freight.ui.wallet.ChooseOpenBankFragment;
import com.saimawzc.freight.ui.wallet.MyWalletFragment;
import com.saimawzc.freight.ui.wallet.TradeDelationFragment;
import com.saimawzc.freight.ui.wallet.WalletSignFragment;
import com.saimawzc.freight.ui.wallet.WalletWithdrawFragment;
/**
 * Created by Administrator on 2020/7/31.
 */
public class PersonCenterActivity extends BaseActivity {
    String comeFrom = "";
    String companyId = "";
    BaseFragment mCurrentFragment;
    @Override
    protected int getViewId() {
        return R.layout.activity_personcenter;
    }
    @Override
    protected void init() {

        if (comeFrom.equals("mycar")) {//我的车辆
            mCurrentFragment=new MyCarFragment();
        }
        if (comeFrom.equals("walletdelation")) {//
            mCurrentFragment=new TradeDelationFragment();
        }
        if (comeFrom.equals("addbankcard")) {//
            mCurrentFragment=new BankBindCardFragment();
        }
        if (comeFrom.equals("keepset")) {//保活设置
            mCurrentFragment=new keepLiveSetFragment();
        }
        if (comeFrom.equals("set")) {//设置
            mCurrentFragment=new MySetFragment();
        }
        if (comeFrom.equals("addsuggest")) {//新增建议
            mCurrentFragment=new AddSuggetsFragment();
        }
        if (comeFrom.equals("suggestlist")) {//建议列表
            mCurrentFragment=new SuggestListFragment();
        }
        if (comeFrom.equals("suggestdelation")) {//建议详情
            mCurrentFragment=new SuggestDealDelationFragment();
        }
        if (comeFrom.equals("choosebank")) {//选择银行
            mCurrentFragment=new ChooseOpenBankFragment();
        }
        if (comeFrom.equals("choosebigbank")) {//选择大额行号
            mCurrentFragment=new ChooseBigBankFragment();
        }
        if (comeFrom.equals("withdraw")) {//提现
            mCurrentFragment=new WalletWithdrawFragment();
        }
        if (comeFrom.equals("sign")) {//钱包签约
            mCurrentFragment=new WalletSignFragment();
        }
        if (comeFrom.equals("wallet")) {//我的钱包
            mCurrentFragment=new MyWalletFragment();
        }
        if (comeFrom.equals("myship")) {//我的船舶
            mCurrentFragment=new MyShipFragment();
        }
        if (comeFrom.equals("shipchange")) {//船舶
            mCurrentFragment=new MyShipChangeFragment();
        }
        if (comeFrom.equals("searchShip")) {//搜索船舶
            mCurrentFragment=new SearchShipFragment();
        }
        if(comeFrom.equals("useridentification")){
            mCurrentFragment=new ChooseCarrierFragment();
        }
        if(comeFrom.equals("personalcarrier")){//个人承运商认证
            mCurrentFragment=new PersonalCarrierFragment();
        }
        if(comeFrom.equals("nomaltaxicarrier")){//一般纳税人
            mCurrentFragment=new NomalTaxesCarriverFragment();
        }
        if(comeFrom.equals("samllcarrive")){//小规模企业
            mCurrentFragment=new SmallCompanyCarrierFragment();
        }
        if(comeFrom.equals("driveridentification")){//司机认证
            mCurrentFragment=new DriverCarrierFragment();
        }
        if(comeFrom.equals("resistercar")){//注册车辆
            mCurrentFragment=new RegisterCarFragment();
        }
        if(comeFrom.equals("resistership")){//注册船舶
            mCurrentFragment=new RegisterShipFragment();
        }
        if(comeFrom.equals("searchCar")){//搜索车辆
            mCurrentFragment=new SearchCarFragment();
        }
        if(comeFrom.equals("mydriver")){//我的司机
            mCurrentFragment=new MyDriverFragment();
        }
        if(comeFrom.equals("mylessess")){//我的承租人
            mCurrentFragment=new MyLessesFragment();
        }
        if(comeFrom.equals("personcenter")){//个人中心
            mCurrentFragment=new PersonCenerFragment();
        }
        if(comeFrom.equals("adddriver")){//添加司机
            mCurrentFragment=new SearchDriviFragment();
        }
        if(comeFrom.equals("mycarrive")){//我的承运商
            mCurrentFragment=new MyCarrierFragment();
        }
        if(comeFrom.equals("carchange")){//车辆变更
            mCurrentFragment=new MyCarChangeFragment();
        }
        if(comeFrom.equals("mysettlement")){//我的结算
            mCurrentFragment=new MySettlementFragment();
        }
        if(comeFrom.equals("changecarsearch")){
            mCurrentFragment=new SendCarSearchFragment();
        }
        if(comeFrom.equals("sijicarriver")){//司机认证
            mCurrentFragment=new ChooseDriverFragment();
        }
        if(mCurrentFragment!=null){
            mCurrentFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mCurrentFragment)
                    .commit();
        }
    }
    @Override
    protected void initListener() {
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        if(bundle!=null){
            comeFrom=bundle.getString("from");
            companyId=bundle.getString("companyId");

        }
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
