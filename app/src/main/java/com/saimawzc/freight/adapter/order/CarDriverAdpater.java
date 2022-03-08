package com.saimawzc.freight.adapter.order;

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
import com.saimawzc.freight.adapter.sendcar.ScDriverAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.CarDriverDto;
import com.saimawzc.freight.dto.order.CarInfolDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 汽车类型
 */

public class CarDriverAdpater extends BaseAdapter {

    private List<CarDriverDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public CarDriverAdpater(List<CarDriverDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<CarDriverDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<CarDriverDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<CarDriverDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_cardriver, parent,
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
            CarDriverDto dto=mDatas.get(position);
            if (dto.getStatus()!=null&&dto.getIfDisable()!=null) {
                if (dto.getStatus()==1||dto.getIfDisable()==2) {
                    ((ViewHolder) holder).tvName.setTextColor(mContext.getResources().getColor(R.color.gray888));
                    ((ViewHolder) holder).tvPhoe.setTextColor(mContext.getResources().getColor(R.color.gray888));
                }
            }else {
                if (dto.getIfDisable()!=null) {
                    if (dto.getIfDisable()==2) {
                        ((ViewHolder) holder).tvName.setTextColor(mContext.getResources().getColor(R.color.gray888));
                        ((ViewHolder) holder).tvPhoe.setTextColor(mContext.getResources().getColor(R.color.gray888));
                    }
                }

            }

            ((ViewHolder) holder).tvName.setText(dto.getSjName());
            ((ViewHolder) holder).tvPhoe.setText(dto.getUserAccount());
            if (position == getmPosition()) {
                ((ViewHolder) holder).imgchoose.setVisibility(View.VISIBLE);
            }else{
                //  否则的话就全白色初始化背景
                ((ViewHolder) holder).imgchoose.setVisibility(View.GONE);
            }
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
        @BindView(R.id.tvPhoe) TextView tvPhoe;
        @BindView(R.id.imgchoose) ImageView imgchoose;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
    private  int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
        notifyDataSetChanged();
    }

}
