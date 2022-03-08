package com.saimawzc.freight.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.login.UserInfoDto;
import com.saimawzc.freight.dto.my.PersonCenterDto;
import com.saimawzc.freight.weight.utils.FileUtil;
import com.saimawzc.freight.weight.utils.SdCardUtil;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;
import com.werb.permissionschecker.PermissionChecker;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.ButterKnife;
import static com.saimawzc.freight.ui.my.identification.DriverCarrierFragment.REQUEST_CODE_PIC;

/**
 * 项目名称：TOPRACING
 * 类描述：基础Fragment，封装了基本方法
 * 创建人：nsj
 * 创建时间：2017/1/12 12:18
 * 修改备注：
 */
public abstract class BaseFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    public Context mContext;
    public View view;
    private OnFragmentInteractionListener mListener;
    protected BaseActivity context;
    public PermissionChecker permissionChecker;
    public LinearLayoutManager layoutManager;
    public UserInfoDto userInfoDto;
    public PersonCenterDto personCenterDto;
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    //public BroadcastReceiver mReceiver;
    private boolean mNeedOnBus;
    private boolean isAttach=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (BaseActivity) getActivity();
        mContext=getActivity();

        //防止fragment出现重叠
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setNavigationBarColor(getResources().getColor(R.color.bg));
        }
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }
    protected void setNeedOnBus(boolean needOnBus) {
        mNeedOnBus = needOnBus;
    }
    public int dip2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 判断view是否可用
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view); // 移除重复的view
            }
        } else {
            // Inflate the layout for this fragment
            view = inflater.inflate(initContentView(), container, false);
            ButterKnife.bind(this, view);
            initView();
            initData();
        }
        if (mNeedOnBus) {
            EventBus.getDefault().register(this);
        }
       return view;
    }
    /**
     * 子类要实现该方法，return 要填充的布局
     *
     * @return
     */
    public abstract int initContentView();

    /**
     * 子类要实现该方法，以找到控件
     */
    public abstract void initView();

    /**
     * 获取数据
     * @param
     */
    public abstract  void initData();

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {

        Intent intent ;
        if(getActivity()==null){
            intent= new Intent(BaseApplication.getInstance(), clazz);
        }else {
            intent= new Intent(getActivity(), clazz);
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        startActivity(intent);
    }
    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {

        Intent intent ;
        if(getActivity()==null){
            intent = new Intent(BaseApplication.getInstance(), clazz);
        }else {
            intent = new Intent(getActivity(), clazz);
        }
        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {

        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        Intent intent ;
        if(getActivity()==null){
            intent= new Intent(BaseApplication.getInstance(), clazz);
        }else {
            intent= new Intent(getActivity(), clazz);
        }
        startActivityForResult(intent, requestCode);
    }
    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {

        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        if(clazz==null){
            return;
        }
        Intent intent;
        if(getActivity()==null){
            intent= new Intent(BaseApplication.getInstance(), clazz);
        }else {
            intent= new Intent(getActivity(), clazz);
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isAttach=true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) view.getParent()).removeView(view);
        if (mNeedOnBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 本地上传图片
     * **/
    public String tempImage;
    public final int REQUEST_CODE_CAMERA=1000;
    public void showCameraAction(Context cx) {
        if(context==null){
            return;
        }
        tempImage = SdCardUtil.getCacheTempImage(context);
        openCamera(tempImage, REQUEST_CODE_CAMERA);
    }

    public void openCamera(String path, int code) {
        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file:///" + path));
        startActivityForResult(intent, code);
    }
    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }



    public void showCameraAction() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(context==null){
            if (takePictureIntent.resolveActivity(BaseApplication.getInstance().getPackageManager())!=null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                if(takePictureIntent==null){
                    return;
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(BaseApplication.getInstance(),
                            "com.saimawzc.freight.updateFileProvider",
                            photoFile);
                    if(photoURI==null){
                        return;
                    }
                    if(!isAttach){
                        context.showMessage("not Attach");
                        return;
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                }
            }
        }else {

            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                if(takePictureIntent==null){
                    return;
                }
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(context,
                            "com.saimawzc.freight.updateFileProvider",
                            photoFile);
                    if(photoURI==null){
                        return;
                    }
                    if(!isAttach){
                        context.showMessage("not Attach");
                        return;
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                }
            }
        }


    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir ;

         if(context!=null){
             storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
         }else {
             storageDir = BaseApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
         }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        tempImage = image.getAbsolutePath();
        return image;
    }

    public void initpermissionChecker(){
        permissionChecker=new PermissionChecker(context);
        permissionChecker.setTitle(getString(R.string.check_info_title));
        permissionChecker.setMessage(getString(R.string.check_info_message));
    }
    public void  stopSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout){
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    protected void initCamera(String type){
        if(mContext==null){
            return;
        }
        if(!isAttach){
            context.showMessage("not Attach");
            return;
        }
        tempImage=System.currentTimeMillis()+"";
        Intent intent = new Intent(mContext, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(mContext,tempImage).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                type);
        startActivityForResult(intent, REQUEST_CODE_PIC);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public  UserInfoDto getUserInfoDto(UserInfoDto useDto){
        if(useDto==null|| TextUtils.isEmpty(useDto.getRoleId()) ){
            useDto= Hawk.get(PreferenceKey.USER_INFO);
           return  useDto;
        }else {
            return  useDto;
        }
    }
}
