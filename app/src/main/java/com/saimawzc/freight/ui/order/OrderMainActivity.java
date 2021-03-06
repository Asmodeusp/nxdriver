package com.saimawzc.freight.ui.order;

import android.os.Bundle;
import android.util.Log;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.ui.my.setment.account.MyAccountFragment;
import com.saimawzc.freight.ui.my.setment.account.ReconciliationFragment;
import com.saimawzc.freight.ui.my.setment.account.ReconciliationListFragment;
import com.saimawzc.freight.ui.my.setment.account.ReconcilitionDelationFragment;
import com.saimawzc.freight.ui.my.setment.account.acountmanage.AccountQueryFragment;
import com.saimawzc.freight.ui.my.setment.account.acountmanage.AddAccountFragment;
import com.saimawzc.freight.ui.my.setment.account.acountmanage.WaitComfirmFragment;
import com.saimawzc.freight.ui.my.setment.account.acountmanage.WaitDispatchBillFragment;
import com.saimawzc.freight.ui.order.face.TaxationIdentificationFragment;
import com.saimawzc.freight.ui.order.waybill.AddWayBillFragment;
import com.saimawzc.freight.ui.order.waybill.ApplyPauseTrantFragment;
import com.saimawzc.freight.ui.order.waybill.BiddChooseCarFragment;
import com.saimawzc.freight.ui.order.waybill.BiddChooseDriverFragment;
import com.saimawzc.freight.ui.order.waybill.BiddRankFragment;
import com.saimawzc.freight.ui.order.waybill.JoinBiddFragment;
import com.saimawzc.freight.ui.order.waybill.OrderDelationFragment;
import com.saimawzc.freight.ui.order.waybill.OrderSubcontractFragment;
import com.saimawzc.freight.ui.order.waybill.OrderWayBillInventoryFragment;
import com.saimawzc.freight.ui.order.waybill.manage.StopTrantFragment;
import com.saimawzc.freight.ui.order.waybill.manage.PlanOrderSendCarChooseNum;
import com.saimawzc.freight.ui.order.waybill.manage.SendCarChooseDriverFragment;
import com.saimawzc.freight.ui.order.waybill.manage.SendCarFragment;
import com.saimawzc.freight.ui.sendcar.AgreenOrderFragment;
import com.saimawzc.freight.ui.sendcar.ReArrivalConfirmationFragment;
import com.saimawzc.freight.ui.sendcar.YujingFragmeng;
import com.saimawzc.freight.ui.sendcar.driver.ArrivalConfirmationFragment;
import com.saimawzc.freight.ui.sendcar.driver.ChangeCarFragment;
import com.saimawzc.freight.ui.sendcar.driver.ChangeDriverFragment;
import com.saimawzc.freight.ui.sendcar.driver.DriverTransportFragment;
import com.saimawzc.freight.ui.sendcar.driver.MapTravelFragment;
import com.saimawzc.freight.ui.sendcar.driver.ScSearchDriverFragment;
import com.saimawzc.freight.ui.sendcar.driver.SendCarDelationFragment;
import com.saimawzc.freight.ui.sendcar.driver.StopTrantDelationFragment;
import com.saimawzc.freight.ui.sendcar.driver.error.CancelOrderFragment;
import com.saimawzc.freight.ui.sendcar.driver.error.ErrorReportFragment;
import com.saimawzc.freight.ui.sendcar.driver.error.MyErrorListFragment;
import com.saimawzc.freight.ui.sendcar.driver.numLock.BindNumLockFragment;
import com.saimawzc.freight.ui.sendcar.driver.numLock.GetPassNumFragment;


public class OrderMainActivity extends BaseActivity {
    String comeFrom = "";
    BaseFragment mCurrentFragment;
    String companyId ="" ;

    @Override
    protected int getViewId() {
        return R.layout.activity_personcenter;
    }

    @Override
    protected void init() {

        if(comeFrom.equals("joinbidd")){//????????????
            mCurrentFragment=new JoinBiddFragment();
        }
        if(comeFrom.equals("swbulu")){//????????????
            mCurrentFragment=new TaxationIdentificationFragment();
        }
        if(comeFrom.equals("sqcd")){//????????????
            mCurrentFragment=new AgreenOrderFragment();
        }

        if(comeFrom.equals("rankbidd")){//????????????
            mCurrentFragment=new BiddRankFragment();
        }
        if(comeFrom.equals("biddchoosedriver")){//???????????????
            mCurrentFragment=new BiddChooseDriverFragment();
        }
        if(comeFrom.equals("eaxmgoodlist")){//???????????????
            mCurrentFragment=new ExamGoodPeopleListFragment();
        }
        if(comeFrom.equals("error")){//????????????
            mCurrentFragment=new ErrorReportFragment();
        }
        if(comeFrom.equals("recallback")){//????????????
            mCurrentFragment=new CancelOrderFragment();
        }

        if(comeFrom.equals("myreport")){//??????????????????
            mCurrentFragment=new MyErrorListFragment();
        }

        if(comeFrom.equals("choosecar")){//????????????
            mCurrentFragment=new BiddChooseCarFragment();
        }

        if(comeFrom.equals("applypause")){//????????????
            mCurrentFragment=new ApplyPauseTrantFragment();
        }

        if(comeFrom.equals("stoptrantdelation")){//????????????
            mCurrentFragment=new StopTrantDelationFragment();
        }
        if(comeFrom.equals("bindlock")){//???????????????
            mCurrentFragment=new BindNumLockFragment();
        }
        if(comeFrom.equals("getnumpass")){//?????????????????????
            mCurrentFragment=new GetPassNumFragment();
        }
        if(comeFrom.equals("queryaccount")){//???????????????
            mCurrentFragment=new AccountQueryFragment();
        }

        if(comeFrom.equals("orderdelation")){//????????????
            mCurrentFragment=new OrderDelationFragment();
        }
        if(comeFrom.equals("addwaybill")){//???????????????
            mCurrentFragment=new AddWayBillFragment();
        }
        if(comeFrom.equals("zhuanbaowaybill")){//??????
            mCurrentFragment=new OrderSubcontractFragment();
        }
        if(comeFrom.equals("biddqingdan")){//??????
            mCurrentFragment=new OrderWayBillInventoryFragment();
        }
        if(comeFrom.equals("stoptrant")){//????????????
            mCurrentFragment=new StopTrantFragment();
        }
        if(comeFrom.equals("sendcar")){//??????
            mCurrentFragment=new SendCarFragment();
        }
        if(comeFrom.equals("reconciliation")){//????????????
            mCurrentFragment=new ReconciliationFragment();
        }
        if(comeFrom.equals("reconciliationlist")){//????????????
            mCurrentFragment=new ReconciliationListFragment();
        }
        if(comeFrom.equals("reconciliondelation")){//????????????
            mCurrentFragment=new ReconcilitionDelationFragment();
        }
        if(comeFrom.equals("mycount")){//???????????????
            mCurrentFragment=new MyAccountFragment();
        }
        if(comeFrom.equals("addaccount")){//???????????????
            mCurrentFragment=new AddAccountFragment();
        }
        if(comeFrom.equals("waitaccount")){//???????????????
            mCurrentFragment=new WaitComfirmFragment();
        }
        if(comeFrom.equals("waitdistapch")){//??????????????????
            mCurrentFragment=new WaitDispatchBillFragment();
        }
        if(comeFrom.equals("chooseSj")){//???????????????
            mCurrentFragment=new SendCarChooseDriverFragment();
        }
        if(comeFrom.equals("chooseplanordernum")){//
            mCurrentFragment=new PlanOrderSendCarChooseNum();
        }
        if(comeFrom.equals("changecar")){//????????????

            mCurrentFragment=new ChangeCarFragment();
        }
        if(comeFrom.equals("changedriver")){//????????????
            mCurrentFragment=new ChangeDriverFragment();
        }
        if(comeFrom.equals("trant")){//??????
            mCurrentFragment=new DriverTransportFragment();
        }
        if(comeFrom.equals("sendcardelation")){//????????????
            mCurrentFragment=new SendCarDelationFragment();
        }
        if(comeFrom.equals("searchdriver")){//????????????
            mCurrentFragment=new ScSearchDriverFragment();
        }
        if(comeFrom.equals("arriveorder")){//????????????
            mCurrentFragment=new ArrivalConfirmationFragment();
        }
        if(comeFrom.equals("rearriveorder")){//????????????
            mCurrentFragment=new ReArrivalConfirmationFragment();
        }
        if(comeFrom.equals("logistaic")){//????????????
            mCurrentFragment=new YujingFragmeng();
        }
        if(comeFrom.equals("maptravel")){//????????????
            mCurrentFragment=new MapTravelFragment();
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
            if (bundle.getString("companyId")!=null) {
                companyId =bundle.getString("companyId");
            }
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
