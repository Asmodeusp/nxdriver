package com.saimawzc.freight.adapter.order.mainindex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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
import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2020/8/6.
 */
public class RobOrderAdapter extends BaseAdapter {

    private List<RobOrderDto.robOrderData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }
    public RobOrderAdapter(List<RobOrderDto.robOrderData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<RobOrderDto.robOrderData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<RobOrderDto.robOrderData> getData() {
        return mDatas;
    }
    public void addMoreData(List<RobOrderDto.robOrderData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_robbill, parent,
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
            RobOrderDto.robOrderData dto=mDatas.get(position);
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
           ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
           ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
           ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
           ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }
           if(dto.getTranType()==2){
               ((ViewHolder) holder).tvtrantType.setText("船运");
           }else {
               ((ViewHolder) holder).tvtrantType.setText("汽运");
           }
             if(dto.getWaybillType()==1){
                 ((ViewHolder) holder).tvUitl1.setText("货主名称：");
                 ((ViewHolder) holder).tvUitl2.setText("物料信息：");
                 ((ViewHolder) holder).tvhzName.setText(dto.getCompanyName());
                 ((ViewHolder) holder).tvGoodinfo.setText(dto.getMaterialsName()+"|"+dto.getPointWeight());
             }else if(dto.getWaybillType()==2){
                 ((ViewHolder) holder).tvUitl1.setText("预运单号：");
                 ((ViewHolder) holder).tvUitl2.setText("重量：");
                 ((ViewHolder) holder).tvhzName.setText(dto.getWaybillNo());
                 ((ViewHolder) holder).tvGoodinfo.setText(dto.getPointWeight());
             }else if(dto.getWaybillType()==3){
                 ((ViewHolder) holder).tvUitl1.setText("所需车型：");
                 ((ViewHolder) holder).tvUitl2.setText("重量：");
                 ((ViewHolder) holder).tvhzName.setText(dto.getCarTypeName());
                 ((ViewHolder) holder).tvGoodinfo.setText(dto.getPointWeight());
             }
            ((ViewHolder) holder).tvTime.setText(""+dto.getStartTime()+"~"+dto.getEndTime());
            ((ViewHolder) holder).tvNum.setText(dto.getBiddNum());
            ((ViewHolder) holder).tvTaskTime.setText(""+dto.getTaskStartTime()+"~"+dto.getTaskEndTime());

            if(dto.getWaybillType()==2){//预运单
                ((ViewHolder) holder).viewtab1.setText("清单");
                ((ViewHolder) holder).viewtab2.setText("参与竞价");
                ((ViewHolder) holder).viewtab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewtab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvStatus.setText("预运单");
                setBtnStyle(mContext,((ViewHolder) holder).viewtab1,R.drawable.shape_list_btn,R.color.gray333);
                setBtnStyle(mContext,((ViewHolder) holder).viewtab2,R.drawable.shape_list_btn_blue,R.color.white);


            }else {
                if(dto.getWaybillType()==1){
                    ((ViewHolder) holder).tvStatus.setText("计划订单");
                }else if(dto.getWaybillType()==3) {
                    ((ViewHolder) holder).tvStatus.setText("调度单");
                }
                ((ViewHolder) holder).viewtab1.setText("参与竞价");
                ((ViewHolder) holder).viewtab1.setVisibility(View.VISIBLE);
                setBtnStyle(mContext,((ViewHolder) holder).viewtab1,R.drawable.shape_list_btn_blue,R.color.white);
                setBtnStyle(mContext,((ViewHolder) holder).viewtab2,R.drawable.shape_list_btn,R.color.gray333);
                ((ViewHolder) holder).viewtab2.setVisibility(View.GONE);
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
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvhzName)TextView tvhzName;
        @BindView(R.id.tvgoodinfo)TextView tvGoodinfo;
        @BindView(R.id.tvtime)TextView tvTime;
        @BindView(R.id.tvnums)TextView tvNum;
        @BindView(R.id.tvtimetask)TextView tvTaskTime;
        @BindView(R.id.tvtrantType)TextView tvtrantType;
        @BindView(R.id.viewtab1)TextView viewtab1;
        @BindView(R.id.viewtab2)TextView viewtab2;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.tvUitl2)TextView tvUitl2;
        @BindView(R.id.tvUitl1)TextView tvUitl1;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.to_company)TextView tvToCompany;
        @BindView(R.id.from_company)TextView tvFromCompany;
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
