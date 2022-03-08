package com.saimawzc.freight.adapter.order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.adapter.sendcar.ShowImageAdpater;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.error.MyErrDto;
import com.saimawzc.freight.weight.utils.preview.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 汽车类型
 */

public class MyErrorListAdpater extends BaseAdapter {

    private List<MyErrDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    private ShowImageAdpater imgAdapter;
    public MyErrorListAdpater(List<MyErrDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<MyErrDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MyErrDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MyErrDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_my_error, parent,
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
            MyErrDto dto=mDatas.get(position);
            ((ViewHolder) holder).tvErrorType.setText(dto.getTypeName());
            ((ViewHolder) holder).tvMiaoShu.setText(dto.getExceptionName());
            if(dto.getExceStatus()==1){
                ((ViewHolder) holder).tvStatus.setText("已处理");
                ((ViewHolder) holder).tvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
            }else {
                ((ViewHolder) holder).tvStatus.setText("待处理");
                ((ViewHolder) holder).tvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            if(!TextUtils.isEmpty(dto.getExceptionImage())){
                ((ViewHolder) holder).rv.setVisibility(View.VISIBLE);
                final ArrayList<String>datas=new ArrayList<>();
                if(!TextUtils.isEmpty(dto.getExceptionImage())){
                    if(!dto.getExceptionImage().contains(",")){
                        datas.add(dto.getExceptionImage());
                    }else {
                        String[] node = dto.getExceptionImage().split(",");
                        for(int i=0;i<node.length;i++){
                            datas.add(node[i]);
                        }
                    }
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
                        mContext.startActivity(intent);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }else {
                ((ViewHolder) holder).rv.setVisibility(View.GONE);
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
        @BindView(R.id.tvErrortype) TextView tvErrorType;
        @BindView(R.id.tvmiaoshu) TextView tvMiaoShu;
        @BindView(R.id.tvStatus) TextView tvStatus;
        @BindView(R.id.cvimg)RecyclerView rv;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
