package com.saimawzc.freight.adapter.my.lessess;

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
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2020/8/4.
 * 车辆变更
 */

public class ChangeShipAdapter extends BaseAdapter {
    private List<SearchShipDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    public ChangeShipAdapter(List<SearchShipDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public ChangeShipAdapter(List<SearchShipDto> mDatas, Context mContext, int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }
    public void setData(List<SearchShipDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<SearchShipDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<SearchShipDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_shipchange, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            final SearchShipDto searchCarDto=mDatas.get(position);
            if(searchCarDto!=null){
                ((ViewHolder) holder).tvCardNum.setText(searchCarDto.getShipNumberId());
                ((ViewHolder) holder).tvCarShape.setText(searchCarDto.getShipTypeName());
                ((ViewHolder) holder).tvWeight.setText(searchCarDto.getShipVolume()+"");
                ((ViewHolder) holder).tvUser.setText(searchCarDto.getBusinessName());
                if(searchCarDto.getCheckStatus()==2){//审核状态(1.已审核 2.审核中 3.未通过)
                    ((ViewHolder) holder).tvstatus.setText("审核中");

                }else if(searchCarDto.getCheckStatus()==3){
                    ((ViewHolder) holder).tvstatus.setText("未通过");
                }else if(searchCarDto.getCheckStatus()==1){
                    ((ViewHolder) holder).tvstatus.setText("审核通过");
                }
                ImageLoadUtil.displayImage(mContext,searchCarDto.getSideView(),((ViewHolder) holder).imageView);
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
        @BindView(R.id.cardnum)TextView tvCardNum;
        @BindView(R.id.tvstatus)TextView tvstatus;
        @BindView(R.id.imgView)ImageView imageView;
        @BindView(R.id.tvcarshap)TextView tvCarShape;
        @BindView(R.id.tvweight)TextView tvWeight;
        @BindView(R.id.tvuser)TextView tvUser;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    private void addCarRelation(String carId){
        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("carId",carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.addCarRelation(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                activity.showMessage("申请成功");
                activity.finish();

            }

            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
               activity.showMessage(message);
            }
        });

    }
}
