package com.saimawzc.freight.ui.my.person;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.ocr.sdk.model.IDCardParams;
import com.bumptech.glide.Glide;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.mine.person.PersonCenterPresenter;
import com.saimawzc.freight.view.mine.person.PersonCenterView;
import com.saimawzc.freight.weight.CircleImageView;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

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

import static android.app.Activity.RESULT_OK;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS;
import static com.saimawzc.freight.base.BaseActivity.PERMISSIONS_CAMERA;

/**
 * Created by Administrator on 2020/8/7.
 * 中心
 */

public class PersonCenerFragment extends BaseFragment implements PersonCenterView{

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.imgHead)CircleImageView headIamge;
    @BindView(R.id.tvSex)TextView tvSex;
    @BindView(R.id.tvPhone)TextView tvPhone;
    private PersonCenterDto personCenterDto;
    private UserInfoDto userInfoDto;
    private PersonCenterPresenter presenter;
    private String stringHead;

    @Override
    public int initContentView() {
        return R.layout.fragment_personcenter;
    }


    @OnClick({R.id.rlhead,R.id.rlsex,R.id.tvSave})
    public void click(View view){
        switch (view.getId()){
            case R.id.rlhead:

                if(permissionChecker.isLackPermissions(PERMISSIONS_CAMERA)){
                    permissionChecker.requestPermissions();
                    context.showMessage("未获取到相机权限");
                }else {
                    presenter.showCamera();
                }
                break;
            case R.id.rlsex:
                break;
            case R.id.tvSave:
                if(TextUtils.isEmpty(stringHead)){
                    context.showMessage("未更新头像");
                    return;
                }
                presenter.changePic();
                break;
        }
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"个人资料");
        presenter=new PersonCenterPresenter(this,mContext);
        initpermissionChecker();
        userInfoDto= Hawk.get(PreferenceKey.USER_INFO);
        personCenterDto=Hawk.get(PreferenceKey.PERSON_CENTER);
        tvPhone.setText(userInfoDto.getUserAccount());
        if(personCenterDto!=null){
            Glide.with(mContext.getApplicationContext()).load(personCenterDto.getPicture()).error(R.drawable.ico_head_defalut)
                    .into(headIamge);
        }

    }

    @Override
    public void initData() {

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
        context.finish();
    }

    @Override
    public void OnDealCamera(int type) {
        if(type==0){
           showCameraAction();
        }else if(type==1){
            FunctionConfig functionConfig  = GalleryUtils.getFbdtFunction(1);
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }

    @Override
    public String stringHead() {
        return stringHead;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)){
               // Uri uri = Uri.fromFile(new File(tempImage));

                Uploadpic(BaseActivity.compress(mContext,new File(tempImage)));

            }
        }
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        Uri uri = Uri.fromFile(new File(resultList.get(0).getPhotoPath()));
                        ImageLoadUtil.displayImage(mContext, uri, headIamge);
                        Uploadpic(BaseActivity.compress(mContext,new File(resultList.get(0).getPhotoPath())));
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };
    private  void Uploadpic(File file){
        context.showLoadingDialog("图片上传中....");
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
        //files 上传参数
        MultipartBody.Part part=
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);

        context.authApi.loadImg(part).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                stringHead=response.getUrl();
                Glide.with(mContext.getApplicationContext()).load(stringHead).error(R.drawable.ico_head_defalut)
                        .into(headIamge);

            }
            @Override
            public void fail(String code, String message) {
                context.dismissLoadingDialog();
            }
        });
    }


}
