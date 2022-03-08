package com.saimawzc.freight.weight.utils;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.CameraListener;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;

/**
 * Created by Administrator on 2020/8/1.
 * 选择拍照、本地图册
 */

public class CameraDialogUtil {

    public final int REQUEST_CODE_GALLERY = 1001;
    public BottomSheetDialog dialog;
    private RelativeLayout rlIdCard;
    private RelativeLayout rlPhone;
    private RelativeLayout rlCannel;
    Context context;

    public  CameraDialogUtil(Context context){
        this.context=context;
    }

    public void showDialog(final CameraListener listener){
        dialog=new BottomSheetDialog(context);
        View box_view= LayoutInflater.from(context).inflate(R.layout.dialog_choose_photo,null);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //←重点在这里，来，都记下笔记
        dialog.setContentView(box_view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //解决平板完全显示问题
        View parent = (View) box_view.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        box_view.measure(0, 0);
        behavior.setPeekHeight(box_view.getMeasuredHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);
        dialog.show();
        rlIdCard= (RelativeLayout) box_view.findViewById(R.id.takeemeri);//拍照
        rlPhone=(RelativeLayout)box_view.findViewById(R.id.takeByphoto);//相册选择
        rlCannel=(RelativeLayout)box_view.findViewById(R.id.rl_Cannle);
        rlIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.takePic();
                }
            }
        });
        rlPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.chooseLocal();
                }
            }
        });
        rlCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.cancel();
                }
            }
        });


    }






}
