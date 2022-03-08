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
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 司机派车等待
 */

public class ComeleteExecuteAdpater extends BaseAdapter {

    private List<CompleteExecuteDto.ComeletaExecuteData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public ComeleteExecuteAdpater(List<CompleteExecuteDto.ComeletaExecuteData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<CompleteExecuteDto.ComeletaExecuteData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<CompleteExecuteDto.ComeletaExecuteData> getData() {
        return mDatas;
    }
    public void addMoreData(List<CompleteExecuteDto.ComeletaExecuteData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_comelete_excute, parent,
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
            CompleteExecuteDto.ComeletaExecuteData dto=mDatas.get(position);
            ((ViewHolder) holder).tvAdress.setText("派车单号："+dto.getDispatchCarNo());
            ((ViewHolder) holder).tvgoodinfo.setText(dto.getMaterialsNames());
            ((ViewHolder) holder).tvweight.setText(dto.getTransportWeight());
            ((ViewHolder) holder).tvsignnum.setText(dto.getSignWeight());
            ((ViewHolder) holder).tvcarrive.setText(dto.getCysName());
            ((ViewHolder) holder).tvCreatTime.setText(dto.getTaskStartTime()+"-"+dto.getTaskEndTime());
            ((ViewHolder) holder).tvStatus.setText(dto.getCompleteStatusValue());
            if(dto.getWeighingDoubt()!=2){
                ((ViewHolder) holder).tvBdCy.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).tvBdCy.setVisibility(View.GONE);
            }
           if(onTabClickListener!=null){
               ((ViewHolder) holder).viewTab1.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       int position = holder.getLayoutPosition();
                       onTabClickListener.onItemClick("tab1",position);
                   }
               });
               ((ViewHolder) holder).viewTab2.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       int position = holder.getLayoutPosition();
                       onTabClickListener.onItemClick("tab2",position);
                   }
               });
               ((ViewHolder) holder).viewTab3.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       int position = holder.getLayoutPosition();
                       onTabClickListener.onItemClick("tab3",position);
                   }
               });

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
        @BindView(R.id.tvAdress) TextView tvAdress;
        //@BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.tvStatus) TextView tvStatus;
        @BindView(R.id.image) ImageView image;

        @BindView(R.id.tvgoodinfo) TextView tvgoodinfo;
        @BindView(R.id.tvweight) TextView tvweight;
        @BindView(R.id.tvsignnum) TextView tvsignnum;
        @BindView(R.id.tvcarrive) TextView tvcarrive;
        @BindView(R.id.tvCreatTime) TextView tvCreatTime;
        //@BindView(R.id.from_company)TextView tvFromCompany;
       // @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.viewtab1) TextView viewTab1;
        @BindView(R.id.viewtab2) TextView viewTab2;
        @BindView(R.id.viewtab3) TextView viewTab3;
        @BindView(R.id.tvcunyi)TextView tvBdCy;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
