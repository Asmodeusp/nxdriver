package com.saimawzc.freight.adapter.sendcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.dto.sendcar.TrantProcessDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 物流信息
 */

public class LogistoicAdpater extends BaseAdapter {

    private List<LogistoicDto .transportLog> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private ShowImageAdpater imgAdapter;
    private String id;
    private int weighingDoubt=-1;//
    private String pointMark;
    public LogistoicAdpater(List<LogistoicDto .transportLog> mDatas, Context mContext) {
        if(mDatas!=null){
            this.mDatas = mDatas;
        }else {
            this.mDatas=new ArrayList<>();
        }
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public void setData(List<LogistoicDto .transportLog> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<LogistoicDto .transportLog> getData() {
        return mDatas;
    }
    public void addMoreData(List<LogistoicDto .transportLog> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_logticas, parent,
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
            final LogistoicDto .transportLog dto=mDatas.get(position);
            ((ViewHolder) holder).tvContect.setText(dto.getName());
            ((ViewHolder) holder).tvtime.setText(dto.getCreateTime());
            RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) ((ViewHolder) holder).tvline.getLayoutParams(); //取控件textView当前的布局参数
            if(!TextUtils.isEmpty(dto.getPicture())){
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
                     linearParams.height = dp2px(mContext,70);// 控件的高强制设成20
                 }
                imgAdapter=new ShowImageAdpater(datas,mContext);
                GridLayoutManager layoutManager= new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false);
                ((ViewHolder) holder).rv.setLayoutManager(layoutManager);
                ((ViewHolder) holder).rv.setAdapter(imgAdapter);
                imgAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(mContext, PlusImageActivity.class);
                        intent.putStringArrayListExtra("imglist", datas);
                        intent.putExtra("currentpos", position);
                        intent.putExtra("from", "delation");
                        activity.startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
            }else {
                ((ViewHolder) holder).rv.setVisibility(View.GONE);
                linearParams.height = dp2px(mContext,30);
            }
            ((ViewHolder) holder).tvline.setLayoutParams(linearParams); //使设置好的布局参数应用到控件</pre>
            ((ViewHolder) holder).imgisDeal.setBackgroundResource(R.drawable.ico_process_black);
            ((ViewHolder) holder).tvContect.setTextColor(mContext.getResources().getColor(R.color.color_black));
            ((ViewHolder) holder).tvline.setBackgroundResource(R.color.blue);
            if(weighingDoubt!=2){
                if(weighingDoubt==1){
                    if(dto.getTransportType()==7){
                        ((ViewHolder) holder).tvUpload.setVisibility(View.VISIBLE);
                    }else {
                        ((ViewHolder) holder).tvUpload.setVisibility(View.GONE);
                    }
                }else if(weighingDoubt==3){
                    if(dto.getTransportType()==8){
                        ((ViewHolder) holder).tvUpload.setVisibility(View.VISIBLE);
                    }else {
                        ((ViewHolder) holder).tvUpload.setVisibility(View.GONE);
                    }
                }else if(weighingDoubt==4){
                    if(dto.getTransportType()==7||dto.getTransportType()==8){
                        ((ViewHolder) holder).tvUpload.setVisibility(View.VISIBLE);
                    }else {
                        ((ViewHolder) holder).tvUpload.setVisibility(View.GONE);
                    }
                }
                if(dto.getTransportType()==7){
                    ((ViewHolder) holder).layMark.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tvMark.setText(getPointMark());
                }else {
                    ((ViewHolder) holder).layMark.setVisibility(View.GONE);
                }
            }else {
                ((ViewHolder) holder).tvUpload.setVisibility(View.GONE);
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

            if(onTabClickListener!=null){
                ((ViewHolder) holder).tvUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick(dto.getTransportType()+"", position);
//                        Bundle bundle=new Bundle();
//                        bundle.putString("id",id);
//                        bundle.putString("tag",dto.getTransportType()+"");
//                        bundle.putString("from","rearriveorder");
//                        activity.readyGo(OrderMainActivity.class,bundle);
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
        @BindView(R.id.tvtime)TextView tvtime;
        @BindView(R.id.tvUpload)TextView tvUpload;
        @BindView(R.id.laymark)LinearLayout layMark;
        @BindView(R.id.edmark)TextView tvMark;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    public void  setId(String id){
        this.id=id;
    }
    public void setWeighingDoubt(int status){
        this.weighingDoubt=status;
    }

    /**
     * dp转换成px
     */
    private int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public String getPointMark() {
        return pointMark;
    }

    public void setPointMark(String pointMark) {
        this.pointMark = pointMark;
    }
}
