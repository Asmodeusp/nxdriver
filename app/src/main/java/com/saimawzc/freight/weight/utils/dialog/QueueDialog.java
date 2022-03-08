package com.saimawzc.freight.weight.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.idl.face.platform.utils.DensityUtils;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.my.carleader.QueueChooseAdapter;
import com.saimawzc.freight.dto.my.queue.ChooseQueDto;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 车队长选择Dialog
 * Created by v_liujialu01 on 2020/4/7.
 */

public class QueueDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnTimeoutDialogClickListener mOnTimeoutDialogClickListener;
    protected List<ChooseQueDto>mDatas;
     QueueChooseAdapter adapter;
    public QueueDialog(@NonNull Context context,List<ChooseQueDto>mDatas) {
        super(context, R.style.DefaultDialog);
        mContext = context;
        this.mDatas=mDatas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_queue, null);
        setContentView(view);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        int widthPx = DensityUtils.getDisplayWidth(getContext());
        int dp = DensityUtils.px2dip(getContext(), widthPx) - 40;
        lp.width = DensityUtils.dip2px(getContext(), dp);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

        if(mDatas!=null){
            RecyclerView rv=view.findViewById(R.id.cy);
            adapter=new QueueChooseAdapter(mDatas,mContext);
            LinearLayoutManager layoutManager=new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if(mDatas.size()<=position){
                        return;
                    }
                    if(!adapter.getChooseId().equals(mDatas.get(position).getId())){
                        adapter.clearAll(mDatas.get(position).getId());
                    }
                }
                @Override
                public void onItemLongClick(View view, int position) {
                }
            });

            view.findViewById(R.id.tvcancel).setOnClickListener(this);
            view.findViewById(R.id.tvorder).setOnClickListener(this);
        }

    }

    public void setDialogListener(OnTimeoutDialogClickListener listener) {
        mOnTimeoutDialogClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvcancel:
                if (mOnTimeoutDialogClickListener != null) {
                    dismiss();
                    mOnTimeoutDialogClickListener.onRecollect();
                }
                break;

            case R.id.tvorder:
                if(adapter==null||adapter.getChooseId().equals("-1")){
                    Toast.makeText(mContext,"未选择车队长",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mOnTimeoutDialogClickListener != null) {
                    mOnTimeoutDialogClickListener.onReturn(adapter.getChooseId(),adapter.getChooseName(),adapter.getChoosePhone());
                }
                break;

            default:
                break;
        }
    }

    public interface OnTimeoutDialogClickListener {
        void onRecollect();

        void onReturn(String id,String name,String phone);
    }
}
