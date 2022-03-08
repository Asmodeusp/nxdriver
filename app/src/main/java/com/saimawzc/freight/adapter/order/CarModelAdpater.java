package com.saimawzc.freight.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.CarModelDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Administrator on 2020/8/6.
 * 汽车类型
 */

public class CarModelAdpater extends BaseAdapter {

    private List<CarModelDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public CarModelAdpater(List<CarModelDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<CarModelDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<CarModelDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<CarModelDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_car_model, parent,
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
            CarModelDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvCarmodel.setText(dto.getCarTypeName());


            if(getmPosition()==10000){
                ((ViewHolder) holder).llCar.setBackgroundResource(R.drawable.shape_list_btn);
                ((ViewHolder) holder).tvCarmodel.setTextColor(mContext.getResources().getColor(R.color.gray333));
            }else {
                if (position == getmPosition()) {
                    ((ViewHolder) holder).llCar.setBackgroundResource(R.drawable.shape_list_btn_blue);
                    ((ViewHolder) holder).tvCarmodel.setTextColor(mContext.getResources().getColor(R.color.white));
                }else{
                    //  否则的话就全白色初始化背景
                    ((ViewHolder) holder).llCar.setBackgroundResource(R.drawable.shape_list_btn);
                    ((ViewHolder) holder).tvCarmodel.setTextColor(mContext.getResources().getColor(R.color.gray333));
                }
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
        @BindView(R.id.tvCarmodel) TextView tvCarmodel;
        @BindView(R.id.llCar)
        LinearLayout llCar;
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
