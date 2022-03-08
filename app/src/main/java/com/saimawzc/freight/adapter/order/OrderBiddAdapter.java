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
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.bill.WayBillDto;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 */

public class OrderBiddAdapter extends BaseAdapter {

    private List<WayBillDto.OrderBillData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public OrderBiddAdapter(List<WayBillDto.OrderBillData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<WayBillDto.OrderBillData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<WayBillDto.OrderBillData> getData() {
        return mDatas;
    }
    public void addMoreData(List<WayBillDto.OrderBillData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_orderbidd, parent,
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
            WayBillDto.OrderBillData dto=mDatas.get(position);

            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvWayBillNo.setText(dto.getWayBillNo());
            ((ViewHolder) holder).tvWeight.setText(dto.getTotalWeight()+"");
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
             ((ViewHolder) holder).tvFhCompany.setText(dto.getFromName());
             ((ViewHolder) holder).tvShCompany.setText(dto.getToName());

            int sendType=dto.getSendType();
            if(sendType==0){//自己建的  不要申请停运
                ((ViewHolder) holder).viewtab1.setText("删除");
                if(dto.getTranType()==1){
                    ((ViewHolder) holder).viewtab2.setText("派车");
                }else if(dto.getTranType()==2){
                    ((ViewHolder) holder).viewtab2.setText("派船");
                }
                ((ViewHolder) holder).viewtab3.setText("清单");
                ((ViewHolder) holder).viewtab4.setVisibility(View.GONE);
            }else {//指派或者竞价
                ((ViewHolder) holder).viewtab1.setText("转包");
                if(dto.getTranType()==1){
                    ((ViewHolder) holder).viewtab2.setText("派车");
                }else if(dto.getTranType()==2){
                    ((ViewHolder) holder).viewtab2.setText("派船");
                }
                ((ViewHolder) holder).viewtab3.setText("清单");
                ((ViewHolder) holder).viewtab4.setVisibility(View.VISIBLE);
            }

            if(onTabClickListener!=null){
                ((ViewHolder) holder).viewtab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab1", position);
                    }
                });
                ((ViewHolder) holder).viewtab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab2", position);
                    }
                });
                ((ViewHolder) holder).viewtab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab3", position);
                    }
                });
                ((ViewHolder) holder).viewtab4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab4", position);
                    }
                });
            }
            if(dto.getBindSmartLock()==1){
                ((ViewHolder) holder).tvIsBindLock.setText("是（请确认司机已下发电子锁）");
                ((ViewHolder) holder).tvIsBindLock.setTextColor(mContext.getResources().getColor(R.color.red));
            }else {
                ((ViewHolder) holder).tvIsBindLock.setText("否");
                ((ViewHolder) holder).tvIsBindLock.setTextColor(mContext.getResources().getColor(R.color.gray999));
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
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.tvwaybillno)TextView tvWayBillNo;
        @BindView(R.id.tvweight)TextView tvWeight;
        @BindView(R.id.from_company)TextView tvFhCompany;
        @BindView(R.id.to_company)TextView tvShCompany;
        @BindView(R.id.tvISbindlock)TextView tvIsBindLock;
        @BindView(R.id.viewtab1)TextView viewtab1;
        @BindView(R.id.viewtab2)TextView viewtab2;
        @BindView(R.id.viewtab3)TextView viewtab3;
        @BindView(R.id.viewtab4)TextView viewtab4;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
