package com.saimawzc.freight.adapter.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.account.WaitComfirmAccountDto;
import com.saimawzc.freight.dto.account.WaitDispatchDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 待结算派车单
 */

public class WaitDispatchAdpater extends BaseAdapter {
    public HashMap<Integer,Boolean> map=null;
    private List<WaitDispatchDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public WaitDispatchAdpater(List<WaitDispatchDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        map = new HashMap<>();
        init();
    }
    public void setData(List<WaitDispatchDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<WaitDispatchDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<WaitDispatchDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    private void init() {
        if (null == mDatas||mDatas.size()<=0) {
            return;
        }else{
            for(int i=0;i<mDatas.size();i++){
                map.put(i, false);
                Log.d("msg",""+map.get(i));
            }
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_wait_dispatch, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            WaitDispatchDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvId.setText(""+dto.getWayBillNo());
            ((ViewHolder) holder).tvcarnum.setText(""+dto.getCarNo());
            ((ViewHolder) holder).tvzanjie.setText(""+dto.getUnsettlePrice());
            ((ViewHolder) holder).tvyunjia.setText(""+dto.getPointPrice());
            ((ViewHolder) holder).tvsignnum.setText(""+dto.getSignWeight());
            ((ViewHolder) holder).tvguobangnum.setText(""+dto.getWeighing());
            ((ViewHolder) holder).tvsigntype.setText(""+dto.getSignTypeName());
            ((ViewHolder) holder).tvtime.setText(""+dto.getSignTime());


            if (map!= null) {
                Log.d("msg","滑动位置"+map.get(position)+"===="+position);
                if(map.get(position)!=null){
                    ((ViewHolder) holder).checkBox.setChecked(map.get(position));
                }else {
                    ((ViewHolder) holder).checkBox.setChecked(false);
                }
            } else {
                ((ViewHolder) holder).checkBox.setChecked(false);
            }


            if (onItemClickListener != null) {
                ((ViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("msg","点击"+position);
                        boolean tag;
                        if(((ViewHolder) holder).checkBox.isChecked()){
                            map.put(position,true);
                            tag=true;
                        }else {
                            map.put(position,false);
                            tag=false;
                        }
                        onItemClickListener.onItemClick(holder.itemView, position,tag);

                    }
                });
            }
        }if (holder instanceof FooterHolder) {

            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("正在加载数据...");
                    break;
                case LOADING_FINISH:
                    ((FooterHolder) holder).tvFooter.setVisibility(View.VISIBLE);
                    ((FooterHolder) holder).tvFooter.setText("没有更多了~");
                    break;
                default:
                    //  ((FooterHolder) holder).tvFooter.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.tvId) TextView tvId;
        @BindView(R.id.tvcarnum) TextView tvcarnum;
        @BindView(R.id.tvzanjie) TextView tvzanjie;
        @BindView(R.id.tvyunjia) TextView tvyunjia;
        @BindView(R.id.tvsignnum) TextView tvsignnum;
        @BindView(R.id.tvguobangnum) TextView tvguobangnum;
        @BindView(R.id.tvsigntype) TextView tvsigntype;
        @BindView(R.id.tvtime) TextView tvtime;
        @BindView(R.id.checkbox)
        CheckBox checkBox;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    public interface OnItemCheckListener {
        void onItemClick(View view, int position,boolean isselect);
        void onItemLongClick(View view, int position);
    }
    public OnItemCheckListener onItemClickListener;
    public void setOnItemClickListener(OnItemCheckListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

}
