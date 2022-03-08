package com.saimawzc.freight.weight.utils.api.tms;

import com.saimawzc.freight.dto.LcInfoDto;
import com.saimawzc.freight.dto.WaybillEncloDto;
import com.saimawzc.freight.dto.my.RouteDto;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.car.TrafficDto;
import com.saimawzc.freight.dto.my.carleader.LeaderEmptyDto;
import com.saimawzc.freight.dto.order.NeedOpenFenceDto;
import com.saimawzc.freight.dto.order.PassNumDto;
import com.saimawzc.freight.dto.order.SignWeightDto;
import com.saimawzc.freight.dto.order.error.ErrorReportDto;
import com.saimawzc.freight.dto.order.error.MyErrDto;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.dto.sendcar.TrantDto;
import com.saimawzc.freight.dto.sendcar.TrantSamllOrderDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;
import com.saimawzc.freight.dto.sendcar.WarnInfoDto;
import com.saimawzc.freight.dto.travel.BeidouTravelDto;
import com.saimawzc.freight.weight.utils.http.JsonResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TmsApi {

    /**
     * 未完成派车单
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/selectUnComplete")
    Call<JsonResult<WaitExecuteDto>> getWaitdate(@Body RequestBody array);


    /**
     * 开启任务
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/dispatchTransport")
    Call<JsonResult<List<LeaderEmptyDto>>> startTask(@Body RequestBody array);

    /**
     * 添加车队长
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/addCdz")
    Call<JsonResult<EmptyDto>> addCarQueue(@Body RequestBody array);
    /**
     * 已完成派车单
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/selectComplete")
    Call<JsonResult<CompleteExecuteDto>> getCompelete(@Body RequestBody array);
    /**
     *获取派车详情
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/selectDispatchCarDetail")
    Call<JsonResult<SendCarDelatiodto>> getSendCarDelation(@Body RequestBody array);

    /**
     *异常换车
     */
    @Headers("Content-Type: application/json")
    @POST("tms/changeCarLog/add")
    Call<JsonResult<EmptyDto>> changeCar(@Body RequestBody array);

    /**
     *获取到货通知查询
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/selectConfirm")
    Call<JsonResult<ArriverOrderDto>> getArriverDto(@Body RequestBody array);

    /**
     *获取过磅量数据
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/getWeight")
    Call<JsonResult<SignWeightDto>> getSignWeight(@Body RequestBody array);

    /**
     *异常换司机
     */
    @Headers("Content-Type: application/json")
    @POST("tms/changeDriverLog/add")
    Call<JsonResult<EmptyDto>> changedriver(@Body RequestBody array);

    /**
     *获取异常换车的车辆列表
     */
    @Headers("Content-Type: application/json")
    @POST("tms/car/selectByRoleIdCar")
    Call<JsonResult<List<SearchCarDto>>> getSearchCarList(@Body RequestBody array);


    /**
     *获取异常换车司机信息
     */
    @Headers("Content-Type: application/json")
    @POST("tms/carrier/selectByCysIdInfo")
    Call<JsonResult<List<ScSearchDriverDto>>> getScDriverList(@Body RequestBody array);


    /**
     *获取运输详情
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/selectTransport")
    Call<JsonResult<TrantDto>> getTrant(@Body RequestBody array);

    /**
     *打卡
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/clockIn")
    Call<JsonResult<EmptyDto>> trantDaka(@Body RequestBody array);

    /**
     *重新打卡
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/weighingDoubtUpdate")
    Call<JsonResult<EmptyDto>> retrantDaka(@Body RequestBody array);

    /**
     *自动到货确认
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/autoSjSignIn")
    Call<JsonResult<EmptyDto>> autoArriver(@Body RequestBody array);

    /**
     *获取运输详情
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/mapTrack")
    Call<JsonResult<RouteDto>> getRoute(@Body RequestBody array);


    /**
     *获取预运单路径  小单
     */
    @Headers("Content-Type: application/json")
    @POST("/tms/dispatchCar/transportMapTrack")
    Call<JsonResult<TrantSamllOrderDto>> getSmallOrderRulet(@Body RequestBody array);

    /**
     *获取物流信息
     */
    @Headers("Content-Type: application/json")
    @POST("tms/dispatchCar/selectTransportLog")
    Call<JsonResult<List<LogistoicDto>>> getLogistoc(@Body RequestBody array);

    /**
     *获取预警信息
     */
    @Headers("Content-Type: application/json")
    @POST("oms/common/disPatchCar/selectWaybillWarnInfo")
    Call<JsonResult<List<WarnInfoDto>>> getWarnInfo(@Body RequestBody array);

    /**
     *围栏推送
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/pushEnclosureReq")
    Call<JsonResult<EmptyDto>> pushWeiLan(@Body RequestBody array);

    /**
     *获取需要开启围栏的订单
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/needOpenEncBill")
    Call<JsonResult<NeedOpenFenceDto>> getNeedFenceList(@Body RequestBody array);

    /**
     *获取需要开启围栏的订单
     */
    @Headers("Content-Type: application/json")
    @POST("tms/smartLock/driverBindLock")
    Call<JsonResult<EmptyDto>> bindPass(@Body RequestBody array);

    /**
     *获取需要开启围栏的订单
     */
    @Headers("Content-Type: application/json")
    @POST("tms/smartLock/driverGetPassword")
    Call<JsonResult<PassNumDto>> getPassNum(@Body RequestBody array);

    /**
     *获取北斗轨迹
     */
    @Headers("Content-Type: application/json")
    @POST("tms/track/selectTrackResult")
    Call<JsonResult<BeidouTravelDto>> getBeiDouTravel(@Body RequestBody array);


    /**
     *获取异常上报类型
     */
    @Headers("Content-Type: application/json")
    @POST("tms/exceptionReportType/selectAll")
    Call<JsonResult<List<ErrorReportDto>>> getErrorList(@Body RequestBody array);

    /**
     *新增异常上报
     */
    @Headers("Content-Type: application/json")
    @POST("tms/exceptionReport/add")
    Call<JsonResult<EmptyDto>> addErrorReport(@Body RequestBody array);


    /**
     *申请撤销订单
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/cancelTheOrder")
    Call<JsonResult<EmptyDto>> applyCancelOrder(@Body RequestBody array);


    /**
     *获取异常上报列表
     */
    @Headers("Content-Type: application/json")
    @POST("tms/exceptionReport/selectWaybillInfo")
    Call<JsonResult<List<MyErrDto>>> getMyErrorList(@Body RequestBody array);


    /**
     *获取中交系统车辆信息
     */
    @Headers("Content-Type: application/json")
    @POST("tms/track/selectZhiYunCarInfo")
    Call<JsonResult<TrafficDto>> getTraffic(@Body RequestBody array);

    /**
     *获取中交系统车辆信息
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/isFenceClock")
    Call<JsonResult<EmptyDto>> getIsFeece(@Body RequestBody array);

    /**
     *查询运单围栏信息
     */
    @Headers("Content-Type: application/json")
    @POST("tms/waybill/selectWaybillEnclosureInfo")
    Call<JsonResult<WaybillEncloDto>> getYdWeiLanInfo(@Body RequestBody array);

    /**
     *接口料仓信息查询
     */
    @Headers("Content-Type: application/json")
    @POST("tms/ebc/lc/queryLcInfo")
    Call<JsonResult<LcInfoDto>> queryLcInfo(@Body RequestBody array);
    /**
     *料仓关锁
     */
    @Headers("Content-Type: application/json")
    @POST("tms/docking/yisi/siloScanLock")
    Call<JsonResult<LcInfoDto>> siloScanLock(@Body RequestBody array);
    /**
     *料仓关锁
     */
    @Headers("Content-Type: application/json")
    @POST("tms/docking/yisi/siloScanUnlock")
    Call<JsonResult<LcInfoDto>> siloScanUnlock(@Body RequestBody array);

}
