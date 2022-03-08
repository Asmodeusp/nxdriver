package com.saimawzc.freight.ui.sendcar.driver.numLock;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.PassNumAdapter;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import com.saimawzc.freight.weight.utils.http.CallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 绑定电子锁
 * **/
public class BindNumLockFragment extends BaseFragment  {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.edNum) EditText edNum;
    @BindView(R.id.cv) RecyclerView rv;
    private List<String>mDatas=new ArrayList<>();
    private PassNumAdapter adapter;
    private String dispatchCarId;


    @OnClick({R.id.imgScan,R.id.tvOrder,R.id.btnBand,R.id.btnCannel})
    public void click(View view){
        switch (view.getId()){
            case R.id.imgScan:
                scan();
                break;
            case R.id.tvOrder:
                if(TextUtils.isEmpty(edNum.getText().toString())){
                    context.showMessage("请输入电子锁编码");
                    return;
                }
                if(mDatas.contains(edNum.getText().toString())){
                    context.showMessage("您已添加该编号");
                    return;
                }
                mDatas.add(edNum.getText().toString());
                adapter.notifyDataSetChanged();
                edNum.setText("");
                break;
            case R.id.btnBand:
                if(mDatas.size()<=0){
                    context.showMessage("请添加电子锁");
                    return;
                }
                band();
                break;
            case R.id.btnCannel:
                context.finish();
                break;
        }

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_bindpass;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"绑定电子锁");
        dispatchCarId=getArguments().getString("dispatchCarId");
        adapter=new PassNumAdapter(mDatas,mContext);
        layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    /****
     * 扫描二维码
     * **/
    private void scan() {
        //获取扫描结果

        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(扫一扫)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setDingPath(R.raw.qrcode)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫一扫")//设置Tilte文字
                .setTitleBackgroudColor(Color.BLACK)//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(context, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result1) {
                final String result = result1.replaceAll(" ", ""); //结果文字
                if(TextUtils.isEmpty(result)){
                    context.showMessage("您的扫描有误");
                    return;
                }
                if(mDatas.contains(result)){
                    context.showMessage("您已添加该编号");
                    return;
                }
                mDatas.add(result);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void band(){
        context.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("dispatchCarId",dispatchCarId);
            jsonObject.put("passwordCode",new JSONArray(mDatas));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
       context.tmsApi.bindPass(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                context.dismissLoadingDialog();
                context.finish();

            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
               context.showMessage(message);
            }
        });
    }
}
