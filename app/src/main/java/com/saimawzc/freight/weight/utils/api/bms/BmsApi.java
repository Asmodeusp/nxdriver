package com.saimawzc.freight.weight.utils.api.bms;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.account.AccountDelationDto;
import com.saimawzc.freight.dto.account.AccountQueryPageDto;
import com.saimawzc.freight.dto.account.AccountType;
import com.saimawzc.freight.dto.account.MySetmentPageQueryDto;
import com.saimawzc.freight.dto.account.WaitComfirmQueryPageDto;
import com.saimawzc.freight.dto.account.WaitDispatchQueryPageDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import com.saimawzc.freight.dto.wallet.TradeDelationDto;
import com.saimawzc.freight.weight.utils.http.JsonResult;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BmsApi {

    /**
     * 对账单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/record/selectRecordList")
    Call<JsonResult<AccountQueryPageDto>> getAccountList(@Body RequestBody array);

    /**
     * 对账单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/record/selectByIdDetails")
    Call<JsonResult<AccountDelationDto>> getAccountDelation(@Body RequestBody array);
    /**
     * 对账单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/settle/selectSettleList")
    Call<JsonResult<MySetmentPageQueryDto>> getmysetmentlist(@Body RequestBody array);

    /**
     * 获取结算大单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/cys/recordSettle/selectPendSettlePlan")
    Call<JsonResult<WaitComfirmQueryPageDto>> getBigorderlist(@Body RequestBody array);

    /**
     * 获取结算大单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/cys/recordSettle/selectPendSettleSmall")
    Call<JsonResult<WaitDispatchQueryPageDto>> getsmallOrderList(@Body RequestBody array);

    /**
     *   sysUser/register
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("bms/cys/recordSettle/add")
    Call<JsonResult<EmptyDto>> addSetment(@Body RequestBody array);

    /**
     *   sysUser/register
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("bms/common/settle/editSettleStatus")
    Call<JsonResult<EmptyDto>> confirm(@Body RequestBody array);
    /**
     * 获取结算大单列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/common/record/selectRecordStatus")
    Call<JsonResult<List<AccountType>>> getaccountType(@Body RequestBody array);
    /**
     * 获取民生银行列表
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/findMinShengBankNo")
    Call<JsonResult<List<MsBankDto>>> GETmSbANKlIST(@Body RequestBody array);
    /**
     * 获取大额行号
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/findOpenBranch")
    Call<JsonResult<List<MsBankDto>>> getBigBank(@Body RequestBody array);
    /**
     * 卡bin
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/findCardBinInfo")
    Call<JsonResult<MsBankDto>> cardBin(@Body RequestBody array);
    /**
     * 获取子账户信息
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/findInfo")
    Call<JsonResult<SonAccountDto>> getSonAcountInfo(@Body RequestBody array);
    /**
     * 获取交易明细
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/findTransRecordByPage")
    Call<JsonResult<TradeDelationDto>> getTradeDelation(@Body RequestBody array);
    /**
     * 获取验证码
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/getVerificationCode")
    Call<JsonResult<EmptyDto>> getCode(@Body RequestBody array);
    /**
     * 民生银行提现
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/withdraw")
    Call<JsonResult<EmptyDto>> withwaraw(@Body RequestBody array);
    /**
     * 签约
     */
    @Headers("Content-Type: application/json")
    @POST("bms/bank/sign")
    Call<JsonResult<EmptyDto>> sign(@Body RequestBody array);


    @Headers("Content-Type: application/json")
    @POST("bms/bank/bindBankNo")
    Call<JsonResult<EmptyDto>> bk(@Body RequestBody array);

    @Headers(value={"Content-Type: application/json"})
    @POST(value="bms/bank/defaultCardNo")
    Call<JsonResult<EmptyDto>> setDefaultCard(@Body RequestBody array);

}
