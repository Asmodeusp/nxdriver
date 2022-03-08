package com.saimawzc.freight.adapter.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.account.ReconclitionDto;
import com.saimawzc.freight.dto.account.WaitComfirmAccountDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 待确认订单列表
 */

public class WaitComfirmAdpater extends BaseAdapter {

    private List<WaitComfirmAccountDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public WaitComfirmAdpater(List<WaitComfirmAccountDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<WaitComfirmAccountDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<WaitComfirmAccountDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<WaitComfirmAccountDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
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
            View view = mInflater.inflate(R.layout.item_wait_comfirmaccount, parent,
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
            WaitComfirmAccountDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvName.setText(""+dto.getCompanyName());
            ((ViewHolder) holder).tvId.setText(""+dto.getWayBillNo());
            ((ViewHolder) holder).tvsendadress.setText(""+dto.getFromUserAddress());
            ((ViewHolder) holder).tvreceiveadress.setText(""+dto.getToUserAddress());
            ((ViewHolder) holder).tvallnum.setText(""+dto.getTotalWeight());
            ((ViewHolder) holder).signnum.setText(""+dto.getSignWeight());
            ((ViewHolder) holder).waitnum.setText(""+dto.getUnsettleWeight());
            ((ViewHolder) holder).waitmoney.setText(""+dto.getUnsettlePrice());
            ((ViewHolder) holder).goodInfo.setText(dto.getMaterialsName());
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
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
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvId) TextView tvId;
        @BindView(R.id.tvsendadress) TextView tvsendadress;
        @BindView(R.id.tvreceiveadress) TextView tvreceiveadress;
        @BindView(R.id.tvallnum) TextView tvallnum;
        @BindView(R.id.signnum) TextView signnum;
        @BindView(R.id.waitnum) TextView waitnum;
        @BindView(R.id.waitmoney) TextView waitmoney;
        @BindView(R.id.tvgoodInfo)TextView goodInfo;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
