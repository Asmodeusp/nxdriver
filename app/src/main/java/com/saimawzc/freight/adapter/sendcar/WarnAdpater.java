package com.saimawzc.freight.adapter.sendcar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.dto.sendcar.WarnInfoDto;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 物流信息
 */

public class WarnAdpater extends BaseAdapter {

    private List<WarnInfoDto.worninfotLog> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public WarnAdpater(List<WarnInfoDto.worninfotLog> mDatas, Context mContext) {
        if(mDatas!=null){
            this.mDatas = mDatas;
        }else {
            this.mDatas=new ArrayList<>();
        }

        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<WarnInfoDto.worninfotLog> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<WarnInfoDto.worninfotLog> getData() {
        return mDatas;
    }
    public void addMoreData(List<WarnInfoDto.worninfotLog> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_warn, parent,
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
            WarnInfoDto.worninfotLog dto=mDatas.get(position);
            if(dto.getWarnType()==1){
                ((ViewHolder) holder).tvContect.setText("偏离预警");
            }else  if(dto.getWarnType()==2){
                ((ViewHolder) holder).tvContect.setText("离线预警");
            }else  if(dto.getWarnType()==3){
                ((ViewHolder) holder).tvContect.setText("停留预警");
            }else  if(dto.getWarnType()==4){
                ((ViewHolder) holder).tvContect.setText("高危围栏预警");
            }

            ((ViewHolder) holder).tvtime.setText(dto.getCreateTime());
            ((ViewHolder) holder).imgisDeal.setBackgroundResource(R.drawable.ico_process_red);
            ((ViewHolder) holder).tvContect.setTextColor(mContext.getResources().getColor(R.color.color_black));
            ((ViewHolder) holder).tvline.setBackgroundResource(R.color.red);
            ((ViewHolder) holder).tvNeiron.setText(dto.getContent());

            if(onTabClickListener!=null){

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
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.imgisDeal) ImageView imgisDeal;
        @BindView(R.id.tvContect) TextView tvContect;
        @BindView(R.id.tvline) TextView tvline;
        @BindView(R.id.lldpwn) RelativeLayout lldpwn;
        @BindView(R.id.tvtime)TextView tvtime;
        @BindView(R.id.tvneirong)TextView tvNeiron;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
