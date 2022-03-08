package com.saimawzc.freight.adapter.sendcar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.sendcar.ArriverOrderDto;
import com.saimawzc.freight.dto.sendcar.SendCarDelatiodto;
import com.saimawzc.freight.weight.LengthFilter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 到货确认
 */
public class ArriveOrderAdpater extends BaseAdapter {

    private List<ArriverOrderDto.materialsDto>mDatas ;
    private Context mContext;
    private LayoutInflater mInflater;
    private String trantType;
    private String dealType="-1";

    public ArriveOrderAdpater(List<ArriverOrderDto.materialsDto> mDatas,
                              Context mContext,String trantType,String delaType){
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.trantType=trantType;
        this.dealType=delaType;
    }

    public void setData(List<ArriverOrderDto.materialsDto>mDatas  ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_arriverorder, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(holder instanceof ViewHolder){
            ArriverOrderDto.materialsDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvGoodName.setText(dto.getMaterialsName());
            if(dealType.equals("7")){
                if(!TextUtils.isEmpty(dto.getSignWeight())){
                    if(Double.parseDouble(dto.getSignWeight())>0){
                        ((ViewHolder) holder).edNum.setText(dto.getSignWeight()+"");
                        ((ViewHolder) holder).edGuoBang.setText(dto.getSignWeight()+"");
                    }else {
                        ((ViewHolder) holder).edGuoBang.setText(0+"");
                    }
                }
            }else {
                if(dto.getWeighing()>0){
                    ((ViewHolder) holder).edNum.setText(dto.getWeighing()+"");
                    ((ViewHolder) holder).edGuoBang.setText(dto.getWeighing()+"");
                }else {
                    ((ViewHolder) holder).edGuoBang.setText(0+"");
                }
            }
            ((ViewHolder) holder).edNum.setFilters(new InputFilter[] {new LengthFilter(3)});
            ((ViewHolder) holder).edNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if(!TextUtils.isEmpty(s.toString())){
                            if(!s.toString().equals(".")){
                                if(trantType.equals("1")){
                                    if(Double.parseDouble(s.toString())>200){
                                        activity.showMessage("最大输入不能超过200");
                                        ((ViewHolder) holder).edNum.setText("");
                                    }
                                }
                                if(dealType.equals("7")){
                                    mDatas.get(position).setSignWeight(s.toString());
                                }else {
                                    mDatas.get(position).setWeighing(Double.parseDouble(s.toString()));
                                }
                            }
                        }else {
                            mDatas.get(position).setSignWeight("");
                        }
                    }catch (Exception e){
                        activity.showMessage("数据输入有误");
                        Log.e("msg",e.getMessage());
                    }
                }
            });
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
        return mDatas.size()== 0 ? 0 : mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.tvGoodName) TextView tvGoodName;
        @BindView(R.id.tvnum) EditText edNum;
        @BindView(R.id.edguobang)EditText edGuoBang;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }
}
