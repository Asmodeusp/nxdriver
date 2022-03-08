package com.saimawzc.freight.adapter.wallet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.wallet.TradeDelationDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 *
 */

public class TradeDelationAdapter extends BaseAdapter {

    private List<TradeDelationDto.data> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String fundAcc="";
    public TradeDelationAdapter(List<TradeDelationDto.data> mDatas, Context mContext,String fundAcc) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.fundAcc=fundAcc;

    }
    public void setData(List<TradeDelationDto.data> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<TradeDelationDto.data> getData() {
        return mDatas;
    }
    public void addMoreData(List<TradeDelationDto.data> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_tradechoose, parent,
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
            TradeDelationDto.data dto=mDatas.get(position);
            ((ViewHolder) holder).tvData.setText(BaseActivity.transTime(dto.getOutputTime()+"","MM月dd日"));
            ((ViewHolder) holder).tvWeek.setText(BaseActivity.getWeekOfDate(dto.getOutputTime()+""));
            if(dto.getTransType()==1){
                ((ViewHolder) holder).tvUserType.setText("充值");
                ((ViewHolder) holder).tvUseWay.setText(dto.getPayFundAccName());//显示收款方
                ((ViewHolder) holder).tvrademoney.setText("+"+dto.getMoney());
                ((ViewHolder) holder).tvrademoney.setTextColor(mContext.getResources().getColor(R.color.red));
            }else if(dto.getTransType()==2){
                ((ViewHolder) holder).tvUserType.setText("收支");
                if(fundAcc.equals(dto.getPayFundAcc())){//支出
                    ((ViewHolder) holder).tvUseWay.setText(dto.getColFundAccName());
                    ((ViewHolder) holder).tvrademoney.setText("-"+dto.getMoney());
                    ((ViewHolder) holder).tvrademoney.setTextColor(mContext.getResources().getColor(R.color.green));
                }else {//收入
                    ((ViewHolder) holder).tvrademoney.setText("+"+dto.getMoney());
                    ((ViewHolder) holder).tvrademoney.setTextColor(mContext.getResources().getColor(R.color.red));
                    ((ViewHolder) holder).tvUseWay.setText(dto.getPayFundAccName());
                }
            }else if(dto.getTransType()==3){
                ((ViewHolder) holder).tvUserType.setText("提现");
                ((ViewHolder) holder).tvUseWay.setText(dto.getColFundAccName());//显示提现
                ((ViewHolder) holder).tvrademoney.setText("-"+dto.getMoney());
                ((ViewHolder) holder).tvrademoney.setTextColor(mContext.getResources().getColor(R.color.green));
            }

            ((ViewHolder) holder).tvallmoney.setText(dto.getBalance());
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
        }else if (holder instanceof FooterHolder) {
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
        @BindView(R.id.tvData) TextView tvData;
        @BindView(R.id.tvWeek) TextView tvWeek;
        @BindView(R.id.tvusertype)TextView tvUserType;
        @BindView(R.id.tvUseWay)TextView tvUseWay;
        @BindView(R.id.trademoney)TextView tvrademoney;
        @BindView(R.id.tvallmoney)TextView tvallmoney;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

}
