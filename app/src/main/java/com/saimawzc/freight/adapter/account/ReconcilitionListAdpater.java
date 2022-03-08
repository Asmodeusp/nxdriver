package com.saimawzc.freight.adapter.account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.account.ReconclitionDto;
import com.saimawzc.freight.dto.order.CarModelDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 汽车类型
 */

public class ReconcilitionListAdpater extends BaseAdapter {

    private List<ReconclitionDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public ReconcilitionListAdpater(List<ReconclitionDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<ReconclitionDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<ReconclitionDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<ReconclitionDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_reconcilition, parent,
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
            ReconclitionDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvId.setText(dto.getDispatchCarNo());
            ((ViewHolder) holder).tvconpanyName.setText(dto.getCompanyName());
            ((ViewHolder) holder).tvcarInfo.setText(dto.getSjName()+"|"+dto.getCarNo());
            ((ViewHolder) holder).tvgoodsinfo.setText(dto.getMaterialsName()+"|"+dto.getTotalWeight());
            ((ViewHolder) holder).tvsigninfo.setText("过磅量："+dto.getWeighing()+"|司机签收："+dto.getSjDefaultWeight()+"|货主签收："+dto.getHzSignInWeight());




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
        @BindView(R.id.tvId) TextView tvId;
        @BindView(R.id.tvconpanyName) TextView tvconpanyName;
        @BindView(R.id.tvcarInfo) TextView tvcarInfo;
        @BindView(R.id.tvgoodsinfo) TextView tvgoodsinfo;
        @BindView(R.id.tvsigninfo) TextView tvsigninfo;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
