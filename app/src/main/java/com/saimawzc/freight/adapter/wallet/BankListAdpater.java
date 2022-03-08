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
import com.saimawzc.freight.dto.wallet.SonAccountDto;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 银行列表
 */

public class BankListAdpater extends BaseAdapter {
    private List<SonAccountDto.bankList> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public BankListAdpater(List<SonAccountDto.bankList> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<SonAccountDto.bankList> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<SonAccountDto.bankList> getData() {
        return mDatas;
    }
    public void addMoreData(List<SonAccountDto.bankList> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_banklist, parent,
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
            SonAccountDto.bankList dto=mDatas.get(position);
            ((ViewHolder) holder).tvBankName.setText(dto.getBankName());
            ((ViewHolder) holder).tvCardNum.setText(dto.getCardNo());

            if(dto.getDefaultFlag() == 1) {
                ((ViewHolder) holder).tvdefault.setText("默认卡");
            }
            else {
                ((ViewHolder) holder).tvdefault.setText("设为默认卡");
            }

            if(this.onTabClickListener != null) {
                ((ViewHolder) holder).tvdefault.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    onTabClickListener.onItemClick("default", holder.getLayoutPosition());
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
                    ((FooterHolder) holder).tvFooter.setVisibility(View.GONE);
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
        if(mDatas==null){
            return 0;
        }else {
            return mDatas.size() == 0 ? 0 : mDatas.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.tvBankName) TextView tvBankName;
        @BindView(R.id.tvCardNum) TextView tvCardNum;
        @BindView(R.id.tvdefault)TextView tvdefault;

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
