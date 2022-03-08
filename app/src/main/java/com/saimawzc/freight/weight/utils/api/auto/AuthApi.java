package com.saimawzc.freight.weight.utils.api.auto;

import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.VersonDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.weight.utils.http.JsonResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * PackageName : cn.ewhale.fangshibo.api
 * Author : Helen Liu
 * Date : 2017/03/31
 * Time : 15:18
 */
public interface AuthApi {

    /**
     * 获取验证码
     *
     * @param
     * @param
     * @return
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/getVCode")
    Call<JsonResult<EmptyDto>> getCode(@Body RequestBody array);
    /**
     * 注册  sysUser/register
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/register")
    Call<JsonResult<EmptyDto>> resister(@Body RequestBody array);

    /**
     * 登录
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/login")
    Call<JsonResult<UserInfoDto>> login(@Body RequestBody array);

    /**
     * 通过验证码登录
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/loginByCode")
    Call<JsonResult<UserInfoDto>> loginByCode(@Body RequestBody array);

    /**
     * 忘记密码
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/forgetPwd")
    Call<JsonResult<EmptyDto>> forgetPass(@Body RequestBody array);


    /****
     * 上传文件 sysUser/uploadFile
     * */
    @Multipart
    @POST("admin/sysUser/uploadFile")
    Call<JsonResult<PicDto>> loadImg
    (@Part MultipartBody.Part file);//@Body  RequestBody file


    /****
     * 多上传文件 sysUser/uploadFile
     * */
    @Multipart
    @POST("admin/sysUser/manyUploadFile")
    Call<JsonResult<PicDto>> uploadMorepic
    ( @Part List<MultipartBody.Part> file);//@Body  RequestBody file

    /**
     * 更新推送文件
     */
    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/updateChannelId")
    Call<JsonResult<EmptyDto>> updatePushInfo(@Body RequestBody array);


    /***
     * 获取版本更新的接口
     * **/
    @Headers("Content-Type: application/json")
    @POST("admin/sys/global/selectByType")
    Call<JsonResult<VersonDto>> getVerson(@Body RequestBody array);


    /***
     * 注销
     * **/
    @Headers("Content-Type: application/json")
    @POST("admin/sysUser/userRoleCancel")
    Call<JsonResult<EmptyDto>> unResister();

    /****
     * 下载视频
     * */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);

    @Headers("Content-Type: application/json")
    @POST("admin/carCaptain/addCarCaptain")
    Call<JsonResult<EmptyDto>> createam(@Body RequestBody array);
}
