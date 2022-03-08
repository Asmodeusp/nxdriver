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

        if(comeFrom.equals("joinbidd")){//参与竞价
            mCurrentFragment=new JoinBiddFragment();
        }
        if(comeFrom.equals("swbulu")){//税务补录
            mCurrentFragment=new TaxationIdentificationFragment();
        }
        if(comeFrom.equals("sqcd")){//申请撤单
            mCurrentFragment=new AgreenOrderFragment();
        }

        if(comeFrom.equals("rankbidd")){//竞价排行
            mCurrentFragment=new BiddRankFragment();
        }
        if(comeFrom.equals("biddchoosedriver")){//竞价选司机
            mCurrentFragment=new BiddChooseDriverFragment();
        }
        if(comeFrom.equals("eaxmgoodlist")){//收货人列表
            mCurrentFragment=new ExamGoodPeopleListFragment();
        }
        if(comeFrom.equals("error")){//异常上报
            mCurrentFragment=new ErrorReportFragment();
        }
        if(comeFrom.equals("recallback")){//申请撤单
            mCurrentFragment=new CancelOrderFragment();
        }

        if(comeFrom.equals("myreport")){//我的异常上报
            mCurrentFragment=new MyErrorListFragment();
        }

        if(comeFrom.equals("choosecar")){//竞价选车
            mCurrentFragment=new BiddChooseCarFragment();
        }

        if(comeFrom.equals("applypause")){//申请停运
            mCurrentFragment=new ApplyPauseTrantFragment();
        }

        if(comeFrom.equals("stoptrantdelation")){//停运详情
            mCurrentFragment=new StopTrantDelationFragment();
        }
        if(comeFrom.equals("bindlock")){//绑定电子锁
            mCurrentFragment=new BindNumLockFragment();
        }
        if(comeFrom.equals("getnumpass")){//获取电子锁密码
            mCurrentFragment=new GetPassNumFragment();
        }
        if(comeFrom.equals("queryaccount")){//结算单查询
            mCurrentFragment=new AccountQueryFragment();
        }

        if(comeFrom.equals("orderdelation")){//订单详情
            mCurrentFragment=new OrderDelationFragment();
        }
        if(comeFrom.equals("addwaybill")){//新增预运单
            mCurrentFragment=new AddWayBillFragment();
        }
        if(comeFrom.equals("zhuanbaowaybill")){//转包
            mCurrentFragment=new OrderSubcontractFragment();
        }
        if(comeFrom.equals("biddqingdan")){//清单
            mCurrentFragment=new OrderWayBillInventoryFragment();
        }
        if(comeFrom.equals("stoptrant")){//申请停运
            mCurrentFragment=new StopTrantFragment();
        }
        if(comeFrom.equals("sendcar")){//派车
            mCurrentFragment=new SendCarFragment();
        }
        if(comeFrom.equals("reconciliation")){//对账查询
            mCurrentFragment=new ReconciliationFragment();
        }
        if(comeFrom.equals("reconciliationlist")){//对账管理
            mCurrentFragment=new ReconciliationListFragment();
        }
        if(comeFrom.equals("reconciliondelation")){//对账详情
            mCurrentFragment=new ReconcilitionDelationFragment();
        }
        if(comeFrom.equals("mycount")){//我的结算单
            mCurrentFragment=new MyAccountFragment();
        }
        if(comeFrom.equals("addaccount")){//添加结算单
            mCurrentFragment=new AddAccountFragment();
        }
        if(comeFrom.equals("waitaccount")){//待结算运单
            mCurrentFragment=new WaitComfirmFragment();
        }
        if(comeFrom.equals("waitdistapch")){//待结算派车单
            mCurrentFragment=new WaitDispatchBillFragment();
        }
        if(comeFrom.equals("chooseSj")){//派车选司机
            mCurrentFragment=new SendCarChooseDriverFragment();
        }
        if(comeFrom.equals("chooseplanordernum")){//
            mCurrentFragment=new PlanOrderSendCarChooseNum();
        }
        if(comeFrom.equals("changecar")){//异常换车

            mCurrentFragment=new ChangeCarFragment();
        }
        if(comeFrom.equals("changedriver")){//更换司机
            mCurrentFragment=new ChangeDriverFragment();
        }
        if(comeFrom.equals("trant")){//运输
            mCurrentFragment=new DriverTransportFragment();
        }
        if(comeFrom.equals("sendcardelation")){//派车详情
            mCurrentFragment=new SendCarDelationFragment();
        }
        if(comeFrom.equals("searchdriver")){//搜索司机
            mCurrentFragment=new ScSearchDriverFragment();
        }
        if(comeFrom.equals("arriveorder")){//确认收货
            mCurrentFragment=new ArrivalConfirmationFragment();
        }
        if(comeFrom.equals("rearriveorder")){//确认收货
            mCurrentFragment=new ReArrivalConfirmationFragment();
        }
        if(comeFrom.equals("logistaic")){//物流信息
            mCurrentFragment=new YujingFragmeng();
        }
        if(comeFrom.equals("maptravel")){//地图轨迹
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
