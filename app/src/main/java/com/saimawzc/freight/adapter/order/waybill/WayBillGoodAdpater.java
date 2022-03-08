package com.saimawzc.freight.adapter.order.waybill;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.waybill.AddWayBillGoodsDto;
import com.saimawzc.freight.weight.utils.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 *
 */

public class WayBillGoodAdpater extends BaseAdapter {

    private List<AddWayBillGoodsDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;

    public WayBillGoodAdpater(List<AddWayBillGoodsDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public WayBillGoodAdpater(List<AddWayBillGoodsDto> mDatas, Context mContext,int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }
    public void setData(List<AddWayBillGoodsDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }
    public List<AddWayBillGoodsDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<AddWayBillGoodsDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_addwaybill_good, parent,
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
            holder.setIsRecyclable(false);
            final AddWayBillGoodsDto dto=mDatas.get(position);
            if(dto!=null){
                if(dto.getGoodsCompanyDto()!=null){
                    ((ViewHolder) holder).tvGoodName.setText(dto.getGoodsCompanyDto().getName());
                }
            }
            if(dto.getGoodNum()>0){
                ((ViewHolder) holder).edTrantNum.setText(dto.getGoodNum()+"");
            }

            if(dto.getGoodPrice()>0){
                ((ViewHolder) holder).edprice.setText(dto.getGoodPrice()+"元");
            }else {
                ((ViewHolder) holder).edprice.setHint("");
            }
            if(dto.getGoodprice_two()>0){
                ((ViewHolder) holder).edGoodPrice.setText(dto.getGoodprice_two()+"元");
            }else {
                ((ViewHolder) holder).edGoodPrice.setHint("");
            }
            ((ViewHolder) holder).tvWeightUtil.setText(dto.getUnitName());
            if(type==1){
                ((ViewHolder) holder).imgRight.setVisibility(View.INVISIBLE);
                ((ViewHolder) holder).rl_chooseUtil.setBackground(null);
                ((ViewHolder) holder).imgDown.setVisibility(View.INVISIBLE);
            }
            if(dto.getBussType()==2){
                ((ViewHolder) holder).rlPrice.setVisibility(View.GONE);
                 ((ViewHolder) holder).rlGoodPrice.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).rlPrice.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).rlGoodPrice.setVisibility(View.VISIBLE);
            }
            ((ViewHolder) holder).edTrantNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(!TextUtils.isEmpty(s.toString())){
                        try{
                            if(s.toString().equals(".")){
                                dto.setGoodNum(0);
                            }else {
                                dto.setGoodNum(Double.parseDouble(s.toString()));
                            }
                        }catch (Exception e){
                            activity.showMessage("输入有误");
                        }

                    }
                }
            });
            ((ViewHolder) holder).edprice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(!TextUtils.isEmpty(s.toString())){

                        try{
                            if(s.toString().equals(".")){
                                dto.setGoodPrice(0);
                            }else {
                                dto.setGoodPrice(Double.parseDouble(s.toString()));
                            }
                        }catch (Exception e){
                            activity.showMessage("输入有误");
                        }

                    }
                }
            });

            if(onTabClickListener!=null){
                ((ViewHolder) holder).rl_goodsname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("goodnames", position);
                    }
                });
                ((ViewHolder) holder).rl_chooseUtil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("goodutil", position);
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
        @BindView(R.id.rl_goodsname) RelativeLayout rl_goodsname;
        @BindView(R.id.tvGoodName)TextView tvGoodName;
        @BindView(R.id.edTrantNum) EditText edTrantNum;
        @BindView(R.id.edprice)EditText edprice;
        @BindView(R.id.tvWeightUtil)TextView tvWeightUtil;
        @BindView(R.id.rl_chooseUtil)
        LinearLayout rl_chooseUtil;
        @BindView(R.id.llprice)RelativeLayout rlPrice;
        @BindView(R.id.imgRight) ImageView imgRight;
        @BindView(R.id.imgdown)ImageView imgDown;
        @BindView(R.id.edgoodprice)EditText edGoodPrice;
        @BindView(R.id.rlgoodprice)RelativeLayout rlGoodPrice;


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
    }
}
