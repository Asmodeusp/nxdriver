package com.saimawzc.freight.ui.my.identification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.CameraListener;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.view.mine.UserIdentificaionView;
import com.saimawzc.freight.weight.utils.CameraDialogUtil;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.werb.permissionschecker.PermissionChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/7/31.
 */
public class UserIdentificationActivity extends BaseActivity implements UserIdentificaionView{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.right_btn)TextView tvRightBtn;
    @BindView(R.id.imgAddpic)ImageView imgAddpic;
    private CameraDialogUtil  cameraDialogUtil;

    @Override
    protected int getViewId() {
        return R.layout.acivity_user_rz;
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"用户认证");
        tvRightBtn.setText("提交");
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cysRz();
            }
        });
        permissionChecker=new PermissionChecker(this);
        permissionChecker.setTitle(getString(R.string.check_info_title));
        permissionChecker.setMessage(getString(R.string.check_info_message));

    }
    FunctionConfig functionConfig;
    @OnClick({R.id.imgAddpic})
    public void click(View view){
        switch (view.getId()){
            case R.id.imgAddpic:

                    if(cameraDialogUtil==null){
                        cameraDialogUtil=new CameraDialogUtil(mContext);
                    }
                    cameraDialogUtil.showDialog(new CameraListener() {
                        @Override
                        public void takePic() {
                            if(functionConfig==null){
                                functionConfig= GalleryUtils.getFbdtFunction(1);
                            }

                            GalleryFinal.openGalleryMuti(cameraDialogUtil.REQUEST_CODE_GALLERY,
                                    functionConfig, mOnHanlderResultCallback);
                        }
                        @Override
                        public void chooseLocal() {
                            showCameraAction();
                        }
                        @Override
                        public void cancel() {
                            cameraDialogUtil.dialog.dismiss();

                        }
                    });
                break;
        }
    }
    @Override
    protected void initListener() {
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
            if (resultList != null) {
                Log.d("msg","选择图片"+resultList.size());
                Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                ImageLoadUtil.displayImage(mContext, uri, imgAddpic);
                Uploadpic(new File(resultList.get(0).getPhotoPath()));
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
            if(!isEmptyStr(tempImage)){
                Uri uri = Uri.fromFile(new File(tempImage));
                ImageLoadUtil.displayImage(mContext, uri, imgAddpic);
                Uploadpic(new File(tempImage));
            }
        }
    }

    private  void Uploadpic(File file){

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        UserInfoDto loginDo= Hawk.get(PreferenceKey.USER_INFO);
        Log.e("token",loginDo.getToken());

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files 上传参数
        MultipartBody.Part part= MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {

            }
            @Override
            public void fail(String code, String message) {

            }
        });
    }


    private void cysRz(){
        JSONObject jsonObject=new JSONObject();
         UserInfoDto loginDo=Hawk.get(PreferenceKey.USER_INFO);
        try {
            jsonObject.put("account",loginDo.getUserAccount());
            jsonObject.put("antPersonImg","https://smwl-search-car.bj.bcebos.com/materials/ed0f13ac-d5dc-4606-a8ba-130852b89961.jpg");
            jsonObject.put("authState",0);
            jsonObject.put("companyName","测试公司");
            jsonObject.put("createTime","2020-08-01");
            jsonObject.put("entrustImg","https://smwl-search-car.bj.bcebos.com/materials/ed0f13ac-d5dc-4606-a8ba-130852b89961.jpg");
            jsonObject.put("legalPerson","测试法人");
            jsonObject.put("legalPersonIdCard","430524199209195993");
            jsonObject.put("personImg","https://smwl-search-car.bj.bcebos.com/materials/ed0f13ac-d5dc-4606-a8ba-130852b89961.jpg");
            jsonObject.put("remark","备注");
            jsonObject.put("systemIdent","1");//安卓获取IOS，安卓1
            jsonObject.put("tradeImg","https://smwl-search-car.bj.bcebos.com/materials/ed0f13ac-d5dc-4606-a8ba-130852b89961.jpg");
            jsonObject.put("tradeNum","1234455");
            jsonObject.put("transportImg","https://smwl-search-car.bj.bcebos.com/materials/ed0f13ac-d5dc-4606-a8ba-130852b89961.jpg");
            jsonObject.put("transportNum","1234455");
            jsonObject.put("userCode",loginDo.getUserCode());
            jsonObject.put("userName","dfgdg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("msg",jsonObject.toString());
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());

        mineApi.cysInentification(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {

            }

            @Override
            public void fail(String code, String message) {

            }
        });
    }





    @Override
    public void showLoading() {
        showLoading();
    }

    @Override
    public void dissLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void Toast(String str) {
       showMessage(str);
    }

    @Override
    public void oncomplete() {//完成

    }
}
