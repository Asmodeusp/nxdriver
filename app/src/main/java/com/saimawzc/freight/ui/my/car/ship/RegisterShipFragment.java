package com.saimawzc.freight.ui.my.car.ship;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.my.ShipTypeDo;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.dto.my.car.ship.ShipIsRegsister;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.mine.car.ResisterShipPresenter;
import com.saimawzc.freight.view.mine.car.ship.ResisterShipView;
import com.saimawzc.freight.weight.LengthFilter;
import com.saimawzc.freight.weight.SwitchButton;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.WheelDialog;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2020/8/4.
 * 注册船舶
 */
public class RegisterShipFragment extends BaseFragment implements ResisterShipView {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imgRoadTransport)ImageView viewRoadTransport;//道路运输证
    @BindView(R.id.imgDrivinglicense)ImageView viewDrivinglicense;//行驶证
    @BindView(R.id.imagecarinfo)ImageView viewCarInfo;//车辆信息
    @BindView(R.id.toggleship) SwitchButton switchShip;//是否加入船运网
    @BindView(R.id.toggleadd)SwitchButton switchAdd;//允许被添加
    @BindView(R.id.edshipname)EditText editShipName;
    @BindView(R.id.edshipId)EditText editShipID;
    @BindView(R.id.tvplate)TextView tvPlate;
    @BindView(R.id.carmodel)TextView tvCarModel;//车辆类型
    @BindView(R.id.edCarLength)EditText edCaeLength;//车辆长度
    @BindView(R.id.edCarWith)EditText edCarWith;//车辆宽度
    @BindView(R.id.edCarHeight)EditText edCarHeight;//车辆高度
    @BindView(R.id.right_btn)TextView btnRight;
    @BindView(R.id.edPassNum)EditText editPassNum;//运输证号
    @BindView(R.id.carweight)EditText editcarWeight;//运输
    @BindView(R.id.edinvitEnter)TextView edinvitEnter;//邀请码
    @BindView(R.id.edusername)EditText editUserName;//业主姓名
    private String stringTransport;
    private String stringDrivinglicense;
    private String stringCarInfo;
    private ResisterShipPresenter presenter;
    private int chooseType;
    private boolean ismodify=false;
    private String xiugia=0+"";



    @Override
    public int initContentView() {
        return R.layout.fragment_register_ship;
    }

    @Override
    public void initView() {
        mContext=getActivity();

        btnRight.setText("提交审核");
        getLocation();
        presenter=new ResisterShipPresenter(this,mContext);
        edCaeLength.setFilters(new InputFilter[] {new LengthFilter(2)});
        edCarHeight.setFilters(new InputFilter[] {new LengthFilter(2)});
        edCarWith.setFilters(new InputFilter[] {new LengthFilter(2)});
        editcarWeight.setFilters(new InputFilter[] {new LengthFilter(3)});
        try{
            ismodify=getArguments().getBoolean("ismodify");
        }catch (Exception e){

        }
        if(getArguments().getString("type").equals("shipinfo")){//查看船舶详情
            presenter.getShipInfo(getArguments().getString("id"));
            context.setToolbar(toolbar,"船舶详情");
            if(ismodify){
                xiugia=1+"";
                btnRight.setVisibility(View.VISIBLE);
                btnRight.setText("船舶修改");
                editShipID.setEnabled(false);
            }else {
                btnRight.setVisibility(View.GONE);
            }
        }else {
            context.setToolbar(toolbar,"船舶注册");
            btnRight.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.imgRoadTransport,R.id.imgDrivinglicense,
            R.id.imagecarinfo,R.id.carmodel})
    public void click(View view){

        if(getArguments().getString("type").equals("shipinfo")){//船舶详情
            if(ismodify=false){
                return;
            }
        }
        switch (view.getId()){
            case R.id.imgRoadTransport://运输证
               presenter.showCamera(mContext,0);
                break;
            case R.id.imgDrivinglicense://行驶证
                presenter.showCamera(mContext,1);
                break;
            case R.id.imagecarinfo://车辆信息
                presenter.showCamera(mContext,2);
                break;
            case R.id.carmodel://车辆类型
                presenter.getCarType();
                break;

        }
    }

    @Override
    public void initData() {


        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(TextUtils.isEmpty(stringTransport)){
                    context.showMessage("水路运输许可证不能为空");
                    return;
                }
                if(TextUtils.isEmpty(stringDrivinglicense)){
                    context.showMessage("行驶证不能为空");
                    return;
                }
                if(TextUtils.isEmpty(stringCarInfo)){
                    context.showMessage("船舶信息不能为空");
                    return;
                }
                if(context.isEmptyStr(editShipName)){
                    context.showMessage("船舶不能为空");
                    return;
                }
                if(context.isEmptyStr(editShipID.getText().toString())){
                    context.showMessage("船运网ID不能为空");
                    return;
                }
                if(context.isEmptyStr(tvCarModel.getText().toString())){
                    context.showMessage("船类型不能为空");
                    return;
                }
                if(context.isEmptyStr(edCaeLength.getText().toString())){
                    context.showMessage("船舶长度不能为空");
                    return;
                }
                if(context.isEmptyStr(edCarHeight.getText().toString())){
                    context.showMessage("船舶高度不能为空");
                    return;
                }
                if(context.isEmptyStr(edCarWith.getText().toString())){
                    context.showMessage("船舶宽度不能为空");
                    return;
                }
                if(context.isEmptyStr(editcarWeight)){
                    context.showMessage("容积不能为空");
                    return;
                }
                if(context.isEmptyStr(editUserName.getText().toString())){
                    context.showMessage("业主名称不能为空");
                    return;
                }
                if(context.isEmptyStr(editPassNum.getText().toString())){
                    context.showMessage("运输证号不能为空");
                    return;
                }
                if(currenrtLocation==null){
                    getLocation();
                    context.showMessage("未获取到当前位置信息，请确认定位是否开启");
                    return;
                }
                if(ismodify||xiugia.equals("1")){
                    presenter.ismodify(getArguments().getString("id"));

                }else {
                    presenter.isResister(editShipID.getText().toString());
                }

            }
        });

        edinvitEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

    }
    @Override
    public void showLoading() {
        context.showLoadingDialog();
    }
    @Override
    public void dissLoading() {
        context.dismissLoadingDialog();
    }
    @Override
    public void Toast(String str) {
        context.showMessage(str);
    }

    @Override
    public void oncomplete() {
        EventBus.getDefault().post(Constants.reshShip);
        context.finish();
    }
    @Override
    public void OnDealCamera(int type) {
        chooseType=type;
        if(type<=2){
            showCameraAction();
        }else if(type<100){
            FunctionConfig functionConfig  = GalleryUtils.getFbdtFunction(1);
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }


    }

    @Override
    public String shipLength() {
        return edCaeLength.getText().toString();
    }

    @Override
    public String tranPermit() {//运输许可证
        return stringTransport;
    }
    @Override
    public String sideView() {//车辆侧面图
        return stringCarInfo;
    }
    @Override
    public String drivingLicense() {//行驶证
        return stringDrivinglicense;
    }
    @Override
    public String tranNo() {
        return editPassNum.getText().toString();
    }
    @Override
    public String businessName() {
        return editUserName.getText().toString();
    }

    @Override
    public String shipWidth() {
        return edCarWith.getText().toString();
    }

    @Override
    public String shipHeight() {
        return edCarHeight.getText().toString();
    }

    @Override
    public String shipName() {
        return editShipName.getText().toString();
    }

    @Override
    public String shipNumberId() {
        return editShipID.getText().toString();
    }

    @Override
    public String shipType() {
        return shipId;
    }

    @Override
    public String shipVolume() {//容积
        return editcarWeight.getText().toString();
    }

    @Override
    public String invitEnter() {
        return edinvitEnter.getText().toString();
    }

    @Override
    public String ifQualification() {
        if(switchShip.isChecked()){
            return 1+"";
        }else {
            return 0+"";
        }

    }

    @Override
    public String allowAdd() {
        if(switchAdd.isChecked()){
            return 1+"";
        }else {
            return 2+"";
        }
    }
    String shipId;
    private WheelDialog wheelDialog;
    @Override
    public void getShipType(List<ShipTypeDo> shipTypeDos) {

        if(shipTypeDos.size()<=0){
            context.showMessage("未找到相关类型");
            return;
        }
        List<String> strings=new ArrayList<>();
        for(int i=0;i<shipTypeDos.size();i++){
            strings.add(shipTypeDos.get(i).getShipTypeName());
        }

        wheelDialog=new WheelDialog(mContext,shipTypeDos,strings);
        wheelDialog.Show(new WheelListener() {
            @Override
            public void callback(String name, String id) {
                tvCarModel.setText(name);
                shipId=id;
            }
        });

    }

    @Override
    public void getShipInfo(SearchShipDto dto) {
        if(dto!=null){
            if(dto.getCheckStatus()==3){//认证失败
                dialog = new NormalDialog(mContext).isTitleShow(true)
                        .title("认证失败")
                        .content(dto.getCheckOpinion())
                        .showAnim(new BounceTopEnter()).
                                dismissAnim(new SlideBottomExit())
                        .btnNum(1).btnText("确定");
                dialog.setOnBtnClickL(
                        new OnBtnClickL() {
                            @Override
                            public void onBtnClick() {
                                if(!context.isDestroy(context)){
                                    dialog.dismiss();
                                }
                            }
                        });
                if(dialog!=null){
                    dialog.show();
                }
            }
            ImageLoadUtil.displayImage(mContext,dto.getSideView(),viewCarInfo);
            ImageLoadUtil.displayImage(mContext,dto.getTranPermit(),viewRoadTransport);
            ImageLoadUtil.displayImage(mContext,dto.getDrivingLicense(),viewDrivinglicense);
            stringTransport=dto.getTranPermit();
            stringDrivinglicense=dto.getDrivingLicense();
            stringCarInfo=dto.getSideView();
            edinvitEnter.setClickable(false);
            if(dto.getIfQualification()==0){//没有资质
                switchShip.setChecked(false);
            }else {
                switchShip.setChecked(true);
            }

            if(dto.getAllowAdd()==2){
                switchAdd.setChecked(false);
            }else {
                switchAdd.setChecked(true);
            }
            editShipName.setText(dto.getShipName());
            editShipID.setText(dto.getShipNumberId()+"");
            tvCarModel.setText(dto.getShipTypeName());
            shipId=dto.getShipType();
            edCaeLength.setText(dto.getShipLength()+"");
            edCarWith.setText(dto.getShipWidth()+"");
            edCarHeight.setText(dto.getShipHeight()+"");
            editcarWeight.setText(dto.getShipVolume()+"");
            editUserName.setText(dto.getBusinessName());
            editPassNum.setText(dto.getTranNo());
            edinvitEnter.setText(dto.getInvitEnter());
        }

    }
    /***车辆被是否被注册
     *
     * **/
    private NormalDialog dialog;
    @Override
    public void isResister(ShipIsRegsister dto) {
        if(dto==null||TextUtils.isEmpty(dto.getBusinessName())){
            presenter.carriveRz();
        }else {
            dialog = new NormalDialog(mContext).isTitleShow(false)
                    .content("该船号已经被"+dto.getBusinessName()+"注册，是否继续注册?")
                    .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                    .btnNum(2).btnText("取消", "确定");
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            if(!context.isDestroy(context)){
                                dialog.dismiss();
                            }
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            presenter.carriveRz();
                            if(!context.isDestroy(context)){
                                dialog.dismiss();
                            }
                        }
                    });
            dialog.show();
        }
    }

    @Override
    public String location() {
        if(currenrtLocation==null){
            return "";
        }else {
            return currenrtLocation.getLongitude()+","+currenrtLocation.getLatitude();
        }
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                        if(chooseType==3){//身份证正面
                            ImageLoadUtil.displayImage(mContext, uri, viewRoadTransport);
                        }else if(chooseType==4){//身份证反面
                            ImageLoadUtil.displayImage(mContext, uri, viewDrivinglicense);
                        }else if(chooseType==5){
                            ImageLoadUtil.displayImage(mContext, uri, viewCarInfo);
                        }
                        Uploadpic(BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath())));
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            if(!context.isEmptyStr(tempImage)){
                Uri uri = Uri.fromFile(new File(tempImage));
                if(chooseType==0){//身份证正面
                    ImageLoadUtil.displayImage(mContext, uri, viewRoadTransport);

                }else if(chooseType==1){
                    ImageLoadUtil.displayImage(mContext, uri, viewDrivinglicense);

                }else if(chooseType==2){
                    ImageLoadUtil.displayImage(mContext, uri, viewCarInfo);
                }
                Uploadpic(BaseActivity.compress(mContext,new File(tempImage)));
            }
        }
    }

    private  void Uploadpic(File file){
        context.showLoadingDialog("图片上传中...");
        File temp=BaseActivity.compress(mContext,file);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), temp);

        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", temp.getName(), requestBody);

        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(chooseType==0||chooseType==3){
                    stringTransport=response.getUrl();
                    context.showMessage("道路运输证上传成功");
                }else if(chooseType==1||chooseType==4){
                    stringDrivinglicense=response.getUrl();
                    context.showMessage("行驶证上传成功");
                } else if(chooseType==2||chooseType==5){
                    stringCarInfo=response.getUrl();
                    context.showMessage("车辆信息上传成功");
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();

            }
        });
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
                edinvitEnter.setText(result);

            }
        });

    }

    /***
     * 获取经纬度
     * **/
    private  BDLocation currenrtLocation;
    public  void getLocation(){

        mLocationClient = new LocationClient(mContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setNeedNewVersionRgc(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        //option.setScanSpan(3000);//是否需要连续定位
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        if(Build.VERSION.SDK_INT >= 26){
            if(!mLocationClient.isStarted()){
                mLocationClient.restart();
            }
        }
    }
    public LocationClient mLocationClient = null;

    private MyLocationListener myListener = new MyLocationListener();
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            if (location == null||
                    location.getLocType()==62 ) {
                return;
            }
            currenrtLocation=location;

        }
    }

}
