package com.saimawzc.freight.adapter.sendcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.adapter.order.MyplanOrderAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.CancelOrderDto;
import com.saimawzc.freight.dto.sendcar.CompleteExecuteDto;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 审核撤销
 */
public class CancelOrderListAdpater extends BaseAdapter {

    private List<CancelOrderDto>mDatas ;
    private Context mContext;
    private LayoutInflater mInflater;

    public CancelOrderListAdpater(List<CancelOrderDto> mDatas, Context mContext){
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<CancelOrderDto>mDatas  ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<CancelOrderDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<CancelOrderDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_cancelorder, parent,
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
            CancelOrderDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvId.setText(dto.getWaybillNo());
            ((ViewHolder) holder).tvName.setText(dto.getSjName());
            ((ViewHolder) holder).tvCarNo.setText(dto.getCarNo());
            ((ViewHolder) holder).tvReason.setText(dto.getCancelOrderReason());

            if (onTabClickListener != null) {
                ((ViewHolder) holder).viewrefuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("viewrefuse", position);
                    }
                });
                ((ViewHolder) holder).viewagreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("viewagreen", position);
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
        return mDatas.size()== 0 ? 0 : mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.tvId) TextView tvId;
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvCarNo)TextView tvCarNo;
        @BindView(R.id.tvrerson)TextView tvReason;
        @BindView(R.id.viewrefuse)TextView viewrefuse;
        @BindView(R.id.viewagreen)TextView viewagreen;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
