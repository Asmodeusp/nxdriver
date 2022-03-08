package com.saimawzc.freight.ui.sendcar.driver.error;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.sendcar.GridViewAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.base.BaseFragment;
import com.saimawzc.freight.dto.order.error.ErrorReportDto;
import com.saimawzc.freight.dto.pic.PicDto;
import com.saimawzc.freight.presenter.order.ErrorReportPresenter;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.view.order.error.ErrorView;
import com.saimawzc.freight.weight.utils.GalleryUtils;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.WheelDialog;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;

import java.io.File;
import java.util.ArrayList;
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
/****
 * 异常上报
 * ***/
public class ErrorReportFragment
        extends BaseFragment implements ErrorView {

    private String dispatchCarId;
    private String location;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private String typeId;
    @BindView(R.id.tvtypeName) TextView tvTypeName;
    @BindView(R.id.edreason) EditText edMiaoshu;
    private ErrorReportPresenter presenter;
    private GridViewAdapter mGridViewAddImgAdapter;
    private ArrayList<String> mPicList = new ArrayList<>();
    @BindView(R.id.gridView) GridView gridView;

    @BindView(R.id.right_btn)TextView tvRightBtn;

    @Override
    public int initContentView() {
        return R.layout.fragment_errorreport;
    }

    @Override
    public void initView() {
        mContext=getActivity();
        context.setToolbar(toolbar,"异常上报");
        dispatchCarId=getArguments().getString("dispatchCarId");
        location=getArguments().getString("location");
        presenter=new ErrorReportPresenter(this,mContext);
    }

    @OnClick({R.id.rltype,R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.rltype:
                presenter.getErrorType();
                break;
            case R.id.tvOrder:
                if(!RepeatClickUtil.isFastClick()){
                    context.showMessage("您操作太频繁，请稍后再试");
                    return;
                }
                if(TextUtils.isEmpty(typeId)){
                    context.showMessage("请选择异常类型");
                    return;
                }
                if(context.isEmptyStr(edMiaoshu)){
                    context.showMessage("请输入描述");
                    return;
                }
                if(mPicList==null||mPicList.size()<=0){
                    presenter.AddError(dispatchCarId,typeId,edMiaoshu.getText().toString(),"",location);
                }else {
                    UploadpMore(mPicList);
                }
                break;
        }
    }


    @Override
    public void initData() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount()-1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == 20) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        presenter.showCamera(mContext);
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
        tvRightBtn.setText("我的上报");
        tvRightBtn.setVisibility(View.VISIBLE);
        tvRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("from","myreport");
                bundle.putString("id",dispatchCarId);
                readyGo(OrderMainActivity.class,bundle);

            }
        });
    }
    private WheelDialog wheelDialog;
    @Override
    public void getErrorType(List<ErrorReportDto> typelist) {
        if(typelist.size()<=0){
            context.showMessage("没有查询到异常类型");
            return;
        }
        List<String> strings=new ArrayList<>();
        for(int i=0;i<typelist.size();i++){
            strings.add(typelist.get(i).getTypeName());
        }
        wheelDialog=new WheelDialog(mContext,typelist,strings);
        wheelDialog.Show(new WheelListener() {
            @Override
            public void callback(String name, String id) {
                typeId=id;
                tvTypeName.setText(name);
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
        context.finish();

    }
    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra("imglist", mPicList);
        intent.putExtra("currentpos", position);
        startActivityForResult(intent, 11);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && resultCode == RESULT_OK){
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra("imglist"); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        } else if(requestCode==REQUEST_CODE_CAMERA && resultCode == RESULT_OK){//拍照
            if(!context.isEmptyStr(tempImage)) {
                mPicList.add(tempImage);
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void successful(int type) {
        if(type==0){
            showCameraAction();
        }else {
            FunctionConfig functionConfig  = GalleryUtils.getFbdtFunction(9-mPicList.size());
            GalleryFinal.openGalleryMuti(1001,
                    functionConfig, mOnHanlderResultCallback);
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback =
            new GalleryFinal.OnHanlderResultCallback() {
                @Override
                public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
                    if (resultList != null) {
                        for(int i=0;i<resultList.size();i++){
                            mPicList.add(resultList.get(i).getPhotoPath());
                        }
                        mGridViewAddImgAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onHanlderFailure(int requestCode, String errorMsg) {
                    context.showMessage(errorMsg);
                }
            };

    private  void UploadpMore(List<String> paths){
        if(paths==null||paths.size()<=0){
            context.showMessage("图片文件不能为空");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        context.showLoadingDialog("图片上传中...");
        for (String path : paths) {//List<String> paths多个文件本地路径地址
            File tempFile=new File(path);
            if(tempFile!=null){
                File file = BaseActivity.compress(mContext,tempFile);
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"), file);
                builder.addFormDataPart("files",file.getName(),requestBody);//files 文件上传参数
            }
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        if(parts==null){
            return;
        }
        context.authApi.uploadMorepic(parts).enqueue(new CallBack<PicDto>() {
            @Override
            public void success(PicDto response) {
                context.dismissLoadingDialog();
                if(!TextUtils.isEmpty(response.getUrl())){
                    presenter.AddError(dispatchCarId,typeId,edMiaoshu.getText().toString(),response.getUrl(),location);
                }
            }
            @Override
            public void fail(String code, String message) {
                context.showMessage(message);
                context.dismissLoadingDialog();
            }
        });
    }
}
