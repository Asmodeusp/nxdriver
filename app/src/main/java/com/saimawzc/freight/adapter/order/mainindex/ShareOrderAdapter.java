package com.saimawzc.freight.adapter.order.mainindex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.mainindex.WaitOrderDto;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 */

public class ShareOrderAdapter extends BaseAdapter {

    private List<WaitOrderDto.waitOrderData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }
    public ShareOrderAdapter(List<WaitOrderDto.waitOrderData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<WaitOrderDto.waitOrderData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<WaitOrderDto.waitOrderData> getData() {
        return mDatas;
    }
    public void addMoreData(List<WaitOrderDto.waitOrderData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_share_order, parent,
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
            WaitOrderDto.waitOrderData dto=mDatas.get(position);
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).image);
            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).fromCompany.setText(dto.getFromName());
            ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }
            String util;
            if(dto.getWeightUnit()==1){
                util="吨";
            }else {
                util="方";
            }
            if(dto.getTranType()==2){
                ((ViewHolder) holder).tvtrantType.setText("船运");
            }else {
                ((ViewHolder) holder).tvtrantType.setText("汽运");
            }
            if(dto.getWaybillType()==1){
                ((ViewHolder) holder).tvUitl1.setText("货主名称：");
                ((ViewHolder) holder).tvUitl2.setText("货物名称：");
                ((ViewHolder) holder).tvUitl3.setText("重量：");
                ((ViewHolder) holder).tvHzName.setText(dto.getCompanyName());
                ((ViewHolder) holder).tvGoodInfo.setText(dto.getMaterialsName());
                ((ViewHolder) holder).tvNum.setText(dto.getPointWeight()+util);
                ((ViewHolder) holder).tvGoodInfo.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvUitl2.setVisibility(View.VISIBLE);
            }else if(dto.getWaybillType()==2){
                ((ViewHolder) holder).tvUitl1.setText("预运单号：");
                ((ViewHolder) holder).tvUitl3.setText("重量：");
                ((ViewHolder) holder).tvHzName.setText(dto.getWaybillNo());
                ((ViewHolder) holder).tvNum.setText(dto.getPointWeight()+util);
                ((ViewHolder) holder).tvGoodInfo.setVisibility(View.GONE);
                ((ViewHolder) holder).tvUitl2.setVisibility(View.GONE);
            }else if(dto.getWaybillType()==3){
                ((ViewHolder) holder).tvUitl1.setText("创建时间：");
                ((ViewHolder) holder).tvUitl2.setText("所需车型：");
                ((ViewHolder) holder).tvUitl3.setText("重量：");
                ((ViewHolder) holder).tvHzName.setText(dto.getCreateTime());
                ((ViewHolder) holder).tvGoodInfo.setText(dto.getCarTypeName());
                ((ViewHolder) holder).tvNum.setText(dto.getPointWeight()+util);
                ((ViewHolder) holder).tvGoodInfo.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvUitl2.setVisibility(View.VISIBLE);
            }
            if(dto.getWaybillType()==2){//1计划订单 2 预运单 3调度单
                ((ViewHolder) holder).viewtab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewtab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewtab1.setText("清单");
                ((ViewHolder) holder).viewtab2.setText("确认指派");
                ((ViewHolder) holder).tvStatus.setText("预运单");
                ((ViewHolder) holder).viewtab2.setBackgroundResource(R.drawable.shape_list_btn_blue);
                ((ViewHolder) holder).viewtab2.setTextColor(mContext.getResources().getColor(R.color.white));

                ((ViewHolder) holder).viewtab1.setBackgroundResource(R.drawable.shape_list_btn);
                ((ViewHolder) holder).viewtab1.setTextColor(mContext.getResources().getColor(R.color.gray333));
            }else {
                if(dto.getWaybillType()==1){
                    ((ViewHolder) holder).tvStatus.setText("计划订单");
                }else if(dto.getWaybillType()==3){
                    ((ViewHolder) holder).tvStatus.setText("调度单");
                }
                ((ViewHolder) holder).viewtab1.setBackgroundResource(R.drawable.shape_list_btn_blue);
                ((ViewHolder) holder).viewtab1.setTextColor(mContext.getResources().getColor(R.color.white));

                ((ViewHolder) holder).viewtab2.setBackgroundResource(R.drawable.shape_list_btn);
                ((ViewHolder) holder).viewtab2.setTextColor(mContext.getResources().getColor(R.color.gray333));
                ((ViewHolder) holder).viewtab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewtab2.setVisibility(View.GONE);
                ((ViewHolder) holder).viewtab1.setText("确认指派");
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
        @BindView(R.id.image)ImageView image;
        @BindView(R.id.tvhzName)TextView tvHzName;
        @BindView(R.id.tvgoodinfo)TextView tvGoodInfo;
        @BindView(R.id.tvnums)TextView tvNum;
        @BindView(R.id.tvtrantType)TextView tvtrantType;
        @BindView(R.id.tvwaybill)TextView tvStatus;
        @BindView(R.id.viewtab1)TextView viewtab1;
        @BindView(R.id.viewtab2)TextView viewtab2;
        @BindView(R.id.tvUitl3)TextView tvUitl3;
        @BindView(R.id.tvUitl2)TextView tvUitl2;
        @BindView(R.id.tvUitl1)TextView tvUitl1;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.from_company)TextView fromCompany;
        @BindView(R.id.resTxt2Text)TextView resTxt2Text;
        @BindView(R.id.resTxt2Linear)
        LinearLayout resTxt2Linear;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

}
