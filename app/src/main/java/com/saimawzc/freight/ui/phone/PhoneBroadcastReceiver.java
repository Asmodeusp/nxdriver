package com.saimawzc.freight.ui.phone;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.baidu.trace.api.fence.FenceListRequest;
import com.baidu.trace.model.CoordType;
import com.saimawzc.freight.base.BaseApplication;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.order.NeedOpenFenceDto;
import com.saimawzc.freight.weight.utils.api.tms.TmsApi;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.saimawzc.freight.weight.utils.trace.TraceUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhoneBroadcastReceiver  extends BroadcastReceiver {
    private static final String TAG = "message";

    private static String mIncomingNumber = null;
    @Override
    public void onReceive(Context context, Intent intent) {

          // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            switch (tManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:

                    break;
                case TelephonyManager.CALL_STATE_IDLE://挂断电话
                    EventBus.getDefault().post(Constants.reshCall);
                    break;
            }

    }

}
