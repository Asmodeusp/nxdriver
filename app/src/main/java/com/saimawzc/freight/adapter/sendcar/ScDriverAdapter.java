package com.saimawzc.freight.adapter.sendcar;

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
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.dto.sendcar.ScSearchDriverDto;
import com.saimawzc.freight.weight.CircleImageView;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/4.
 * 我的司机
 */

public class ScDriverAdapter extends BaseAdapter {
    private List<ScSearchDriverDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public ScDriverAdapter(List<ScSearchDriverDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public ScDriverAdapter(List<ScSearchDriverDto> mDatas, Context mContext, int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }
    public void setData(List<ScSearchDriverDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ScSearchDriverDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<ScSearchDriverDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_mydriver, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ScSearchDriverDto myDriverDto=mDatas.get(position);
            if (myDriverDto.getIsBlackList()!=null&&myDriverDto.getIfDisable()!=null) {
                if (myDriverDto.getIsBlackList()==1||myDriverDto.getIfDisable()==2) {
                    ((ViewHolder) holder).tvName.setTextColor(mContext.getResources().getColor(R.color.gray888));
                    ((ViewHolder) holder).tvPhone.setTextColor(mContext.getResources().getColor(R.color.gray888));
                }
            }else {
                if (myDriverDto.getIfDisable()!=null) {
                    if (myDriverDto.getIfDisable()==2) {
                        ((ViewHolder) holder).tvName.setTextColor(mContext.getResources().getColor(R.color.gray888));
                        ((ViewHolder) holder).tvPhone.setTextColor(mContext.getResources().getColor(R.color.gray888));
                    }
                }

            }
            ((ViewHolder) holder).tvName.setText(myDriverDto.getSjName());
            ((ViewHolder) holder).tvPhone.setText(myDriverDto.getPhone());
            ImageLoadUtil.displayImage(mContext,myDriverDto.getPicture(),((ViewHolder) holder).imageView);
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
        @BindView(R.id.tvname)TextView tvName;

        @BindView(R.id.imgView)
        CircleImageView imageView;
        @BindView(R.id.tvphone)TextView tvPhone;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
