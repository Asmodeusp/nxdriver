package com.saimawzc.freight.adapter.sendcar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.saimawzc.freight.dto.sendcar.TrantProcessDto;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 运输流程
 */

public class TrantsportProcessAdpater extends BaseAdapter {

    private List<TrantProcessDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private ShowImageAdpater imgAdapter;

    public TrantsportProcessAdpater(List<TrantProcessDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }
    public void setData(List<TrantProcessDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<TrantProcessDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<TrantProcessDto> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_trantprocess, parent,
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
            TrantProcessDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvContect.setText(dto.getName());
            if(dto.isPictureFlag()){
                ((ViewHolder) holder).rv.setVisibility(View.VISIBLE);
                final ArrayList<String>datas=new ArrayList<>();

                 if(!TextUtils.isEmpty(dto.getPicture())){
                     if(!dto.getPicture().contains(",")){
                         datas.add(dto.getPicture());
                     }else {
                         String[] node = dto.getPicture().split(",");
                         for(int i=0;i<node.length;i++){
                             datas.add(node[i]);
                         }
                     }
                 }
                imgAdapter=new ShowImageAdpater(datas,mContext);
                GridLayoutManager layoutManager=new GridLayoutManager(mContext,3);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                ((ViewHolder) holder).rv.setLayoutManager(layoutManager);
                ((ViewHolder) holder).rv.setAdapter(imgAdapter);

                imgAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(mContext, PlusImageActivity.class);
                        intent.putStringArrayListExtra("imglist", datas);
                        intent.putExtra("currentpos", position);
                        intent.putExtra("from", "delation");
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                //ImageLoadUtil.displayImage(mContext,dto.getPicture(),((ViewHolder) holder).imgPic);
            }else {
                ((ViewHolder) holder).rv.setVisibility(View.GONE);
            }
            if(dto.isNextclockInFlag()){//待处理
                ((ViewHolder) holder).tvOrder.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).tvOrder.setVisibility(View.GONE);
            }
            if(dto.isFlag()){//已经处理
                ((ViewHolder) holder).imgisDeal.setBackgroundResource(R.drawable.ico_process_black);
                ((ViewHolder) holder).tvContect.setTextColor(mContext.getResources().getColor(R.color.color_black));
                ((ViewHolder) holder).tvline.setBackgroundResource(R.color.blue);
                ((ViewHolder) holder).rv.setVisibility(View.VISIBLE);
            }else {//尚未处理
                ((ViewHolder) holder).imgisDeal.setBackgroundResource(R.drawable.ico_process_glay);
                ((ViewHolder) holder).tvContect.setTextColor(mContext.getResources().getColor(R.color.text_gray));
                ((ViewHolder) holder).tvline.setBackgroundResource(R.color.bg_18);
                 ((ViewHolder) holder).rv.setVisibility(View.GONE);
                 if(dto.getName().equals("到货确认")){
                     ((ViewHolder) holder).tvline.setVisibility(View.GONE);
                 }else {
                     ((ViewHolder) holder).tvline.setVisibility(View.VISIBLE);
                 }
            }

            if(onTabClickListener!=null){
                ((ViewHolder) holder).tvOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick(mDatas.get(position).getCode()+"", position);
                    }
                });
            }
            if(position==mDatas.size()-1){

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
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.imgisDeal) ImageView imgisDeal;
        @BindView(R.id.tvContect) TextView tvContect;
        @BindView(R.id.rv) RecyclerView rv;
        @BindView(R.id.tvline) TextView tvline;
        @BindView(R.id.lldpwn) RelativeLayout lldpwn;
        @BindView(R.id.tvOrder)TextView tvOrder;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
