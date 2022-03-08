package com.saimawzc.freight.ui.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.ui.MainActivity;
import com.saimawzc.freight.ui.login.LoginActivity;
import com.saimawzc.freight.ui.my.PersonCenterActivity;
import com.saimawzc.freight.weight.utils.api.auto.AuthApi;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.sp.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
public class MyPushMessageReceiver extends PushMessageReceiver {
    /**
     * TAG to Log
     */
    public static final String TAG = "pushmsg";

    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            // 绑定成功
            Log.d(TAG, "绑定成功");
            SPUtils.put(BaseApplication.getInstance(),"channelId",channelId);
//            submitPushInfo(channelId);
        }
    }

    /**
     * 接收透传消息的函数。
     *
     * @param context             上下文
     * @param message             推送的消息
     * @param customContentString 自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = "透传消息 onMessage=\"" + message
                + "\" customContentString=" + customContentString;
        Log.d(TAG, messageString);

    }

    /**
     * 接收通知到达的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */

    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {

        String notifyString = "通知到达 onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.d(TAG, notifyString);
        if(!TextUtils.isEmpty(customContentString)){

            try{
                JSONObject object=new JSONObject(customContentString);
                String roleType =object.getString("roleType");//2承运商 3司机
                String type=object.getString("type");
                if(roleType.equals("3")){
                    if(type.equals("14")){
                        Log.e("msg","更新");
                        EventBus.getDefault().post(Constants.reshAuroAdto);
                    }

                }
            }catch (Exception e){

            }

        }


    }

    /**
     * 接收通知点击的函数。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知点击 onNotificationClicked title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.d(TAG, notifyString);
        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
        if (!TextUtils.isEmpty(customContentString)) {
            // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
            updateContent(context, customContentString);
        }

    }

    /**
     * setTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags 设置成功的tag
     * @param failTags    设置失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;

    }

    /**
     * delTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags 成功删除的tag
     * @param failTags    删除失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " successTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

    }

    /**
     * listTags() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
     * @param tags      当前应用设置的所有tag。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;

    }

    /**
     * PushManager.stopWork() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId 分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            // 解绑定成功
            Log.d(TAG, "解绑成功");
        }

    }

    private void updateContent(Context context, String content) {

        if(!TextUtils.isEmpty(content)){
            try {
                Log.e("msg","推送内容"+content);
                UserInfoDto userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
                JSONObject object=new JSONObject(content);
                String roleType =object.getString("roleType");//2承运商 3司机
                String type=object.getString("type");
                if(!TextUtils.isEmpty(roleType)&&!TextUtils.isEmpty(type)){
                    if(userInfoDto==null){
                        Bundle bundle=new Bundle();
                        startUserActivity(context, LoginActivity.class,bundle);
                        return;
                    }
                    if(!(userInfoDto.getRole()+"").equals(roleType)){
                       // Bundle bundle=new Bundle();
                        //startUserActivity(context, LoginActivity.class,bundle);
                        return;
                    }
                    if(roleType.equals("2")){//承运商
                        if(type.equals("4")){//货主指派 跳待确认
                            Bundle bundle=new Bundle();
                            startUserActivity(context, MainActivity.class,bundle);
                        }else if(type.equals("5")){//货主竞价 跳待抢单
                            Bundle bundle=new Bundle();
                            startUserActivity(context, MainActivity.class,bundle);
                        }
                    }else if(roleType.equals("3")){//司机
                        if(type.equals("1")){//添加司机
                            Bundle bundle=new Bundle();
                            bundle.putString("from","mycarrive");
                            startUserActivity(context,PersonCenterActivity.class,bundle);
                        }else if(type.equals("2")){//添加车辆
                            Bundle   bundle=new Bundle();
                            bundle.putString("from","mylessess");
                            startUserActivity(context,PersonCenterActivity.class,bundle);
                        }else if(type.equals("3")){//我的承租人
//                            Bundle bundle=new Bundle();
//                            bundle.putString("from","mylessess");
//                            startUserActivity(context,PersonCenterActivity.class,bundle);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthApi authApi= Http.http.createApi(AuthApi.class);


    /**
     * 启动新的Activity
     * @param context 当前Activity
     * @param cls     要启动的Activity的类
     */
    public  void startUserActivity(Context context , Class cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(intent);
    }
}
