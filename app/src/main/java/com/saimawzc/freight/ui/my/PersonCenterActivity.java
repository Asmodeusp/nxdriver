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

        if (comeFrom.equals("mycar")) {//????????????
            mCurrentFragment=new MyCarFragment();
        }
        if (comeFrom.equals("walletdelation")) {//
            mCurrentFragment=new TradeDelationFragment();
        }
        if (comeFrom.equals("addbankcard")) {//
            mCurrentFragment=new BankBindCardFragment();
        }
        if (comeFrom.equals("keepset")) {//????????????
            mCurrentFragment=new keepLiveSetFragment();
        }
        if (comeFrom.equals("set")) {//??????
            mCurrentFragment=new MySetFragment();
        }
        if (comeFrom.equals("addsuggest")) {//????????????
            mCurrentFragment=new AddSuggetsFragment();
        }
        if (comeFrom.equals("suggestlist")) {//????????????
            mCurrentFragment=new SuggestListFragment();
        }
        if (comeFrom.equals("suggestdelation")) {//????????????
            mCurrentFragment=new SuggestDealDelationFragment();
        }
        if (comeFrom.equals("choosebank")) {//????????????
            mCurrentFragment=new ChooseOpenBankFragment();
        }
        if (comeFrom.equals("choosebigbank")) {//??????????????????
            mCurrentFragment=new ChooseBigBankFragment();
        }
        if (comeFrom.equals("withdraw")) {//??????
            mCurrentFragment=new WalletWithdrawFragment();
        }
        if (comeFrom.equals("sign")) {//????????????
            mCurrentFragment=new WalletSignFragment();
        }
        if (comeFrom.equals("wallet")) {//????????????
            mCurrentFragment=new MyWalletFragment();
        }
        if (comeFrom.equals("myship")) {//????????????
            mCurrentFragment=new MyShipFragment();
        }
        if (comeFrom.equals("shipchange")) {//??????
            mCurrentFragment=new MyShipChangeFragment();
        }
        if (comeFrom.equals("searchShip")) {//????????????
            mCurrentFragment=new SearchShipFragment();
        }
        if(comeFrom.equals("useridentification")){
            mCurrentFragment=new ChooseCarrierFragment();
        }
        if(comeFrom.equals("personalcarrier")){//?????????????????????
            mCurrentFragment=new PersonalCarrierFragment();
        }
        if(comeFrom.equals("nomaltaxicarrier")){//???????????????
            mCurrentFragment=new NomalTaxesCarriverFragment();
        }
        if(comeFrom.equals("samllcarrive")){//???????????????
            mCurrentFragment=new SmallCompanyCarrierFragment();
        }
        if(comeFrom.equals("driveridentification")){//????????????
            mCurrentFragment=new DriverCarrierFragment();
        }
        if(comeFrom.equals("resistercar")){//????????????
            mCurrentFragment=new RegisterCarFragment();
        }
        if(comeFrom.equals("resistership")){//????????????
            mCurrentFragment=new RegisterShipFragment();
        }
        if(comeFrom.equals("searchCar")){//????????????
            mCurrentFragment=new SearchCarFragment();
        }
        if(comeFrom.equals("mydriver")){//????????????
            mCurrentFragment=new MyDriverFragment();
        }
        if(comeFrom.equals("mylessess")){//???????????????
            mCurrentFragment=new MyLessesFragment();
        }
        if(comeFrom.equals("personcenter")){//????????????
            mCurrentFragment=new PersonCenerFragment();
        }
        if(comeFrom.equals("adddriver")){//????????????
            mCurrentFragment=new SearchDriviFragment();
        }
        if(comeFrom.equals("mycarrive")){//???????????????
            mCurrentFragment=new MyCarrierFragment();
        }
        if(comeFrom.equals("carchange")){//????????????
            mCurrentFragment=new MyCarChangeFragment();
        }
        if(comeFrom.equals("mysettlement")){//????????????
            mCurrentFragment=new MySettlementFragment();
        }
        if(comeFrom.equals("changecarsearch")){
            mCurrentFragment=new SendCarSearchFragment();
        }
        if(comeFrom.equals("sijicarriver")){//????????????
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
