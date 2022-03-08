package com.saimawzc.freight.weight.utils.api.order;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.account.ConsignmentCompanyDto;
import com.saimawzc.freight.dto.order.CancelOrderDto;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarDriverForPage;
import com.saimawzc.freight.dto.order.CarInfolDto;
import com.saimawzc.freight.dto.order.CarModelDto;
import com.saimawzc.freight.dto.order.ExamGoodDto;
import com.saimawzc.freight.dto.order.ManageListDto;
import com.saimawzc.freight.dto.order.OrderDelationDto;
import com.saimawzc.freight.dto.order.OrderInventoryDto;
import com.saimawzc.freight.dto.order.OrderManageRoleDto;
import com.saimawzc.freight.dto.order.PlanOrderReshDto;
import com.saimawzc.freight.dto.order.SendCarDto;
import com.saimawzc.freight.dto.order.StopTrantDelationDto;
import com.saimawzc.freight.dto.order.bidd.JoinBiddDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.dto.order.waybill.RankPageDto;
import com.saimawzc.freight.dto.travel.PresupTravelDto;
import com.saimawzc.freight.weight.utils.http.JsonResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2020/8/1.
 * 我的
 */

public interface OrderApi {

    /**
     * 获取运单计划订单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBillMid/queryForPage")
    Call<JsonResult<MyPlanOrderDto>> getMyPlanOrder(@Body RequestBody array);

    //获取托运公司
    @Headers("Content-Type: application/json")
    @POST("bms/common/company/selectShippingCompany")
    Call<JsonResult<List<ConsignmentCompanyDto>>> getConsignmentCompanyList();

    //获取组织机构
    @Headers("Content-Type: application/json")
    @POST("bms/common/company/selectByCompany")
    Call<String> getAuthorityList();
    /**
     * 获取运单计预运单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBill/queryCysForPage")
    Call<JsonResult<WayBillDto>> getWayBillList(@Body RequestBody array);

    /**
     * 获取运单调度单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/disPatch/queryCysForPage")
    Call<JsonResult<ManageListDto>> getManageOrderList(@Body RequestBody array);


    /**
     * 获取收益待确定
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/omsWayBill/queryForPage")
    Call<JsonResult<WaitOrderDto>> getWaitData(@Body RequestBody array);

    /**
     * 承运商获取抢单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/biddDetail/queryForPageGrabOrder")
    Call<JsonResult<RobOrderDto>> getRobData(@Body RequestBody array);


    /**
     * 获取分享订单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/omsWayBill/queryForSharePage")
    Call<JsonResult<WaitOrderDto>> getShareData(@Body RequestBody array);

    //获取预运单清单
    @Headers("Content-Type: application/json")
    @POST("oms/common/wayBill/selectMaterialsList")
    Call<JsonResult<OrderInventoryDto> >getWayBillQd(@Body RequestBody array);

    //获取预运单详情
    @Headers("Content-Type: application/json")
    @POST("oms/hz/wayBill/selectWayBillById")
    Call<JsonResult<OrderDelationDto> >getWayBillOrderDelation(@Body RequestBody array);

    //获取承运商预运单详情
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBill/selectWayBillById")
    Call<JsonResult<OrderDelationDto> >getcysWayBillOrderDelation(@Body RequestBody array);

    //获取计划订单详情
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBillMid/selectByInfo")
    Call<JsonResult<OrderDelationDto> >getPlanOrderDelation(@Body RequestBody array);

    //获取价格排行
    @Headers("Content-Type: application/json")
    @POST("oms/common/biddDetail/selectRanking")
    Call<JsonResult<RankPageDto> >getRankList(@Body RequestBody array);
    //获取车型
    @Headers("Content-Type: application/json")
    @POST("oms/common/car/selectAllCarType")
    Call<JsonResult<List<CarModelDto>>>getCarModel(@Body RequestBody array);

    //获取车辆信息
    @Headers("Content-Type: application/json")
    @POST("oms/cys/car/queryRoleForPage")
    Call<JsonResult<CarInfolDto> >getCarInfo(@Body RequestBody array);


    //获取司机
    @Headers("Content-Type: application/json")
    @POST("oms/cys/customer/selectSjList")
    Call<JsonResult<List<CarDriverDto>> >getDriver(@Body RequestBody array);


    //分页获取司机
    @Headers("Content-Type: application/json")
    @POST("admin/car/selectSjList")
    Call<JsonResult<CarDriverForPage> >getDriverByPage(@Body RequestBody array);

    //派车
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBill/sendCars")
    Call<JsonResult<EmptyDto> >sendCar(@Body RequestBody array);


    //确认指派
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBill/wayBillPlanAssign")
    Call<JsonResult<EmptyDto> >roderAssign(@Body RequestBody array);

    //承运商新增预运单
    @Headers("Content-Type: application/json")
    @POST("oms/cys/omsWayBill/addWaybill")
    Call<JsonResult<EmptyDto> >addWayBill(@Body RequestBody array);


    //获取竞价详情
    @Headers("Content-Type: application/json")
    @POST("oms/cys/omsWayBill/selectBidd")
    Call<JsonResult<JoinBiddDto> >getBiddDelation(@Body RequestBody array);

    //承运商确认竞价
    @Headers("Content-Type: application/json")
    @POST("oms/cys/omsWayBill/bidd")
    Call<JsonResult<EmptyDto> >csybidd(@Body RequestBody array);

    //获取调度单详情
    @Headers("Content-Type: application/json")
    @POST("oms/common/disPatch/selectWayBillByDispatchId")
    Call<JsonResult<OrderManageRoleDto> >getRuleList(@Body RequestBody array);

    //删除
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBill/deleteCysById")
    Call<JsonResult<EmptyDto> >cysdelete(@Body RequestBody array);

    //删除
    @Headers("Content-Type: application/json")
    @POST("oms/cys/disPatch/deleteCysById")
    Call<JsonResult<EmptyDto> >cysdeletemanage(@Body RequestBody array);

    //关闭派车单
    @Headers("Content-Type: application/json")
    @POST("oms/cys/disPatch/offTransport")
    Call<JsonResult<EmptyDto> >stopTrant(@Body RequestBody array);
    //申请停运
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBillMid/closeWayBill")
    Call<JsonResult<EmptyDto> >ApplyPauseTrant(@Body RequestBody array);

    //获取停运详情
    @Headers("Content-Type: application/json")
    @POST("oms/cys/disPatch/offTransportDetails")
    Call<JsonResult<StopTrantDelationDto>>getStoptrantdelation(@Body RequestBody array);

    //申请停运
    @Headers("Content-Type: application/json")
    @POST("oms/cys/disPatch/sendCarList")
    Call<JsonResult<SendCarDto> >getSendCarList(@Body RequestBody array);

    //上传停留时间
    @Headers("Content-Type: application/json")
    @POST("oms/warnOffline/stopWarn")
    Call<JsonResult<EmptyDto> >uplaodStayTime(@Body RequestBody array);


    //获取预设轨迹
    @Headers("Content-Type: application/json")
    @POST("oms/trackRoute/selectByWaybillIdInfo")
    Call<JsonResult<PresupTravelDto> >getPreSupTravel(@Body RequestBody array);

    ///司机竞价
    /**
     * 司机获取抢单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/sj/biddDetail/queryForPageGrabOrder")
    Call<JsonResult<RobOrderDto>> getsjRobData(@Body RequestBody array);

    //获取司机计划订单详情
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBillMid/selectByInfo")
    Call<JsonResult<OrderDelationDto> >getSjPlanOrderDelation(@Body RequestBody array);
    //获取司机预运单详情
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBill/selectWayBillById")
    Call<JsonResult<OrderDelationDto> >getsjWayBillOrderDelation(@Body RequestBody array);

    //获取司机竞价详情
    @Headers("Content-Type: application/json")
    @POST("oms/sj/omsWayBill/selectBidd")
    Call<JsonResult<JoinBiddDto> >getsjBiddDelation(@Body RequestBody array);

    //司机确认竞价
    @Headers("Content-Type: application/json")
    @POST("oms/sj/omsWayBill/bidd")
    Call<JsonResult<EmptyDto> >sjbidd(@Body RequestBody array);

    /**
     * 司机获取运单计划订单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBillMid/queryForPage")
    Call<JsonResult<MyPlanOrderDto>> getSjMyPlanOrder(@Body RequestBody array);

    /**
     * 司机获取运单计划订单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/thirdWaybill/refreshCys")
    Call<JsonResult<PlanOrderReshDto>> reshData(@Body RequestBody array);

    /**
     * 司机获取运单计预运单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBill/queryCysForPage")
    Call<JsonResult<WayBillDto>> getsjWayBillList(@Body RequestBody array);

    //司机删除
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBill/deleteCysById")
    Call<JsonResult<EmptyDto> >sjdelete(@Body RequestBody array);

    /**
     * 获取运单调度单列表
     */
    @Headers("Content-Type: application/json")
    @POST("oms/sj/disPatch/queryCysForPage")
    Call<JsonResult<ManageListDto>> getsjManageOrderList(@Body RequestBody array);
    //删除
    @Headers("Content-Type: application/json")
    @POST("oms/sj/disPatch/deleteCysById")
    Call<JsonResult<EmptyDto> >sjdeletemanage(@Body RequestBody array);

    //承运商新增预运单
    @Headers("Content-Type: application/json")
    @POST("oms/sj/omsWayBill/addWaybill")
    Call<JsonResult<EmptyDto> >addsjWayBill(@Body RequestBody array);

    //申请停运
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBillMid/closeWayBill")
    Call<JsonResult<EmptyDto> >ApplysjPauseTrant(@Body RequestBody array);

    //司机获取车辆信息
    @Headers("Content-Type: application/json")
    @POST("oms/sj/car/queryRoleForPage")
    Call<JsonResult<CarInfolDto> >getsjCarInfo(@Body RequestBody array);

    //司机派车
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBill/sendCars")
    Call<JsonResult<EmptyDto> >sjsendCar(@Body RequestBody array);

    //获取收货人列表
    @Headers("Content-Type: application/json")
    @POST("oms/sj/wayBill/selectShrInfo")
    Call<JsonResult<List<ExamGoodDto>> >getExamGoods(@Body RequestBody array);
    /**
     *承运商审核
     */
    @Headers("Content-Type: application/json")
    @POST("oms/cys/wayBill/cancelOrder")
    Call<JsonResult<EmptyDto>> applyCancelOrder(@Body RequestBody array);

    //获取审核列表
    @Headers("Content-Type: application/json")
    @POST("oms/common/wayBill/selectCancelOrder")
    Call<JsonResult<List<CancelOrderDto>> >getCancelList(@Body RequestBody array);


    //获取车辆是否有北斗
    @Headers("Content-Type: application/json")
    @POST("oms/common/wayBill/selectCarIfTrack")
    Call<JsonResult<EmptyDto> >carIsBeiDou(@Body RequestBody array);
}
