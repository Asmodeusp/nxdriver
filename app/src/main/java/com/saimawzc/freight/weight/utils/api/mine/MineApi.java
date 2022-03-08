package com.saimawzc.freight.weight.utils.api.mine;

import com.saimawzc.freight.dto.AddServiceDto;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.FrameDto;
import com.saimawzc.freight.dto.face.FaceDto;
import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.login.AreaDto;
import com.saimawzc.freight.dto.login.TaxiAreaDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.my.ShipTypeDo;
import com.saimawzc.freight.dto.my.car.CarIsRegsister;
import com.saimawzc.freight.dto.my.car.MyCarDto;
import com.saimawzc.freight.dto.my.car.ship.MyShipDto;
import com.saimawzc.freight.dto.my.car.SearchCarDto;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.dto.my.car.ship.ShipIsRegsister;
import com.saimawzc.freight.dto.my.carleader.CarLeaderListDto;
import com.saimawzc.freight.dto.my.carleader.CarServiceSfInfoDto;
import com.saimawzc.freight.dto.my.carleader.SearchTeamDto;
import com.saimawzc.freight.dto.my.carleader.TeamDelationDto;
import com.saimawzc.freight.dto.my.carrier.CarrierPageDto;
import com.saimawzc.freight.dto.my.driver.DriverPageDto;
import com.saimawzc.freight.dto.my.driver.SearchDrivierDto;
import com.saimawzc.freight.dto.my.identification.CarrierIndenditicationDto;
import com.saimawzc.freight.dto.my.identification.DriviceerIdentificationDto;
import com.saimawzc.freight.dto.my.lessess.LessessPageDto;
import com.saimawzc.freight.dto.my.queue.CarQueueDto;
import com.saimawzc.freight.dto.my.set.SuggestDto;
import com.saimawzc.freight.dto.taxi.service.CarInfo;
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

public interface MineApi {

    /**
     * 承运商认证
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardCys/add")
    Call<JsonResult<EmptyDto>> cysInentification(@Body RequestBody array);

    /**
     * 承运商重新认证
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardCys/update")
    Call<JsonResult<EmptyDto>> recysInentification(@Body RequestBody array);

    /**
     * 司机认证 userCardSj/addUserCardSj
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardSj/addUserCardSj")
    Call<JsonResult<EmptyDto>> sjInentification(@Body RequestBody array);




    /**
     * 司机认证 userCardSj/addUserCardSj
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardSj/updateUserCardSj")
    Call<JsonResult<EmptyDto>> resjInentification(@Body RequestBody array);

    /**
     *  获取区域
     */
    @Headers("Content-Type: application/json")
    @POST("admin/archivesRegion/findRegion")
    Call<JsonResult<List<AreaDto>>> getArea();

    /**
     *  车辆注册/car/addCar
     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/registerCar")
    Call<JsonResult<EmptyDto>> addCar(@Body RequestBody array);


    /**
     *  修改车辆
     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/updateCar")
    Call<JsonResult<EmptyDto>> modifyCar(@Body RequestBody array);

    /**
     *  船舶/car/addCar
     */
    @Headers("Content-Type: application/json")
    @POST("admin/ship/register")
    Call<JsonResult<EmptyDto>> addShip(@Body RequestBody array);

    /**
     *  船舶修改
     */
    @Headers("Content-Type: application/json")
    @POST("admin/ship/update")
    Call<JsonResult<EmptyDto>> modifyShip(@Body RequestBody array);
    /**
     *  车辆/类型    carType/selectAll
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carType/selectAll")
    Call<JsonResult<List<CarTypeDo>>> getCarType(@Body RequestBody array);

    /**
     *  车辆品牌 carBrand/selectAll
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carBrand/selectAll")
    Call<JsonResult<List<CarBrandDto>>> getCarBrand(@Body RequestBody array);

    /**
     *  车辆信息 carBrand/selectAll
     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/getInfoByCarNo")
    Call<JsonResult<CarInfo>> getCarInfoByCarNo(@Body RequestBody array);
    /**
     *  船/类型    carType/selectAll
     */
    @Headers("Content-Type: application/json")
    @POST("admin/ship/selectShipType")
    Call<JsonResult<List<ShipTypeDo>>> getShipType(@Body RequestBody array);


    /**
     *  根据ID获取车辆详情 car/selectById
     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/selectById")
    Call<JsonResult<SearchCarDto>> getCarInfo(@Body RequestBody array);


    /**
     *  根据ID获取船舶详情 car/selectById
     */
    @Headers("Content-Type: application/json")
    @POST("admin/ship/selectById")
    Call<JsonResult<SearchShipDto>> getShipInfo(@Body RequestBody array);


    /**
     *  个人中心数据sysUser/selectUserInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/selectUserInfo")
    Call<JsonResult<PersonCenterDto>> getPersoneCener();

    /**
     * 取司机认证详情userCardSj/selectInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardSj/selectInfo")
    Call<JsonResult<DriviceerIdentificationDto>> getDriviceIdentidiceInfo();

    /**
     * 承运商认证详情userCardCys/selectInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardCys/selectInfo")
    Call<JsonResult<CarrierIndenditicationDto>> getCarrivIerdentidiceInfo();


    /**
     * 修改个人数据 sysUser/modifyInfo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/modifyInfo")
    Call<JsonResult<EmptyDto>> modifyUserInfo(@Body RequestBody array);


    /**
     * 根据车牌获取车辆列表 car/selectByCarNo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/selectByCarNo")
    Call<JsonResult<List<SearchCarDto>>> getSearchCarList(@Body RequestBody array);


    /**
     * 根据船名获取车辆列表 car/selectByCarNo
     */
    @Headers("Content-Type: application/json")
    @POST("admin/ship/selectByShipNumber")
    Call<JsonResult<List<SearchShipDto>>> getSearchShipList(@Body RequestBody array);

    /**
     * 添加车辆关联数据 carRelation/addCarRelation
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carRelation/addCarRelation")
    Call<JsonResult<EmptyDto>> addCarRelation(@Body RequestBody array);

    /**
     * 解绑车辆关联数据 carRelation/addCarRelation
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carRelation/deleteCarRelation")
    Call<JsonResult<EmptyDto>> unbindCarRelation(@Body RequestBody array);
    /**
     * 添加船舶关联数据 carRelation/addCarRelation
     */
    @Headers("Content-Type: application/json")
    @POST("admin/shipRelation/add")
    Call<JsonResult<EmptyDto>> addShipRelation(@Body RequestBody array);

    /**
     * 我的承租人删除车辆 carRelation/addCarRelation
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carRelation/deleteRelation")
    Call<JsonResult<EmptyDto>> unbindLessessRelation(@Body RequestBody array);


    /**
     * 我的承运商删除carRelation/addCarRelation
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBand/deleteBind")
    Call<JsonResult<EmptyDto>> unbindCarriveRelation(@Body RequestBody array);
    /**
     * 获取我的车辆/car/selectByUserCar

     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/selectByUserCar")
    Call<JsonResult<MyCarDto>> getMyCarList(@Body RequestBody array);


    /**
     * 获取我的船舶/car/selectByUserCar

     */
    @Headers("Content-Type: application/json")
    @POST("/admin/ship/selectByUserShip")
    Call<JsonResult<MyShipDto>> getMyShipList(@Body RequestBody array);

    /**
     * 获取车辆变更记录 carChange/selectCarChange
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carChange/selectCarChange")
    Call<JsonResult<MyCarDto>> getCarChange(@Body RequestBody array);


    /**
     * 获取船舶变更记录 carChange/selectCarChange
     */
    @Headers("Content-Type: application/json")
    @POST("admin/shipChange/queryForPage")
    Call<JsonResult<MyShipDto>> getShipChange(@Body RequestBody array);

    /**
     * 通过手机号查询司机  userCardSj/selectByPhone
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardSj/selectByPhone")
    Call<JsonResult<SearchDrivierDto>> searchDriver(@Body RequestBody array);


    /**
     * /carrierBand/addSjBand
     承运商添加添加司机
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBand/addSjBand")
    Call<JsonResult<EmptyDto>> addDriverRelation(@Body RequestBody array);

    /**
     *  车辆是否为变更car/selectByCarNos
     根据车牌号查询是否存在此数据信息
     */
    @Headers("Content-Type: application/json")
    @POST("admin/car/selectByCarNos")
    Call<JsonResult<CarIsRegsister>> isChange(@Body RequestBody array);

    /**
     *  车辆是否为变更car/selectByCarNos
     根据车牌号查询是否存在此数据信息
     */
    @Headers("Content-Type: application/json")
    @POST("admin/ship/selectIsExist")
    Call<JsonResult<ShipIsRegsister>> isShipChange(@Body RequestBody array);

    /**
     * carrierBand/select
     承运商查询已添加或者待确认司机
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carrierBand/select")
    Call<JsonResult<DriverPageDto>> getMyDriver(@Body RequestBody array);


    /**
     /userCardSj/see
     司机查看我的承运商
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardSj/see")
    Call<JsonResult<CarrierPageDto>> getMyCarrier(@Body RequestBody array);

    /**
     carRelation/selectByTenant
     我的承租人列表信息
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carRelation/selectByTenant")
    Call<JsonResult<LessessPageDto>> getLessessList(@Body RequestBody array);


    /**
     获取弹框
     我的承租人列表信息
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sys/bulletFrame/selectByRole")
    Call<JsonResult<FrameDto>> getFram(@Body RequestBody array);

    /**
     userCardSj/determine
     司机确认承运商添加
     司机审核承运商
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userCardSj/determine")
    Call<JsonResult<EmptyDto>> sjagrenncys(@Body RequestBody array);


    /**
     * /同意申请 carRelation/confirmCarRelation
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carRelation/confirmCarRelation")
    Call<JsonResult<EmptyDto>> agreen(@Body RequestBody array);

    /**
     切换角色 /sysUser/changeRole
     切换角色
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/changeRole")
    Call<JsonResult<UserInfoDto>> changeRole(@Body RequestBody array);


    /**
     新增建议
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userOpinion/add")
    Call<JsonResult<EmptyDto>> addSuggest(@Body RequestBody array);

    /**
     获取建议列表
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userOpinion/selectByInfo")
    Call<JsonResult<List<SuggestDto>>> mySuggestList(@Body RequestBody array);

    /**
     根据ID获取建议详情
     */
    @Headers("Content-Type: application/json")
    @POST("admin/userOpinion/selectById")
    Call<JsonResult<SuggestDto>> mySuggesDelarion(@Body RequestBody array);
    /**
     *  获取区域
     */
    @Headers("Content-Type: application/json")
    @POST("admin/sysRegion/findSysRegion")
    Call<JsonResult<List<TaxiAreaDto>>> getAreaTaxi(@Body RequestBody array);

    /**
     获取车队长列表
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/selectCarCaptainList")
    Call<JsonResult<CarLeaderListDto>> getCarLeaderList(@Body RequestBody array);

    /**
     删除车队长
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/deleteCarCaptain")
    Call<JsonResult<EmptyDto>> deleteCarTeam(@Body RequestBody array);


    /**
     车队长删除服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/deleteFwf")
    Call<JsonResult<EmptyDto>> teamLeaderdelService(@Body RequestBody array);

    /**
     查询车队长详情
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/selectCarCaptainInfo")
    Call<JsonResult<TeamDelationDto>> getTeamDelation(@Body RequestBody array);

    /**
     车队长添加服务方搜索
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/searchFwf")
    Call<JsonResult<SearchTeamDto>> getSearchTeam(@Body RequestBody array);

    /**
     车队长添加服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/addFwf")
    Call<JsonResult<EmptyDto>> addCarService(@Body RequestBody array);

    /**
     车队长添加服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/joinFwf")
    Call<JsonResult<CarServiceSfInfoDto>> getSFiNFO(@Body RequestBody array);

    /**
     车队长添加服务方
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/selectFwfById")
    Call<JsonResult<FaceQueryDto.Facedata>> getFaceINfo(@Body RequestBody array);

    /**
     获取车队长列表
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/myCarCaptain")
    Call<JsonResult<CarQueueDto>> getQueue(@Body RequestBody array);
    /**
     司机同意或者拒绝车队长
     */
    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/checkCarCaptain")
    Call<JsonResult<EmptyDto>> SJoperQueue(@Body RequestBody array);


}
