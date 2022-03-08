package com.saimawzc.freight.adapter.my.carleader;

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
import com.saimawzc.freight.dto.my.queue.ChooseQueDto;
import com.saimawzc.freight.weight.ImageViewCheckable;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2020/8/4.
 *
 */

public class QueueChooseAdapter extends BaseAdapter {

    private List<ChooseQueDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    public QueueChooseAdapter(List<ChooseQueDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;

    }

    private String chooseId="-1";
    private String chooseName;
    private String choosePhone;

    public void setData(List<ChooseQueDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ChooseQueDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<ChooseQueDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_chooseque, parent,
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
            final ChooseQueDto queueDto=mDatas.get(position);
            ((ViewHolder) holder).tvName.setText(queueDto.getName());
            ((ViewHolder) holder).tvPhone.setText(queueDto.getPhone());
            if(queueDto.isCheck()){
                ((ViewHolder) holder).imageView.setImageResource(R.drawable.ico_radio_choose);
            }else {
                ((ViewHolder) holder).imageView.setImageResource(R.drawable.ico_radui_unchoose);
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
        @BindView(R.id.tvUserName)TextView tvName;
        @BindView(R.id.imgview)
        ImageViewCheckable imageView;
        @BindView(R.id.phone)TextView tvPhone;
    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    public void clearAll(String chooseId){
        this.chooseId=chooseId;
        if(mDatas.size()>0){
            int count=0;
            for(int i=0;i<mDatas.size();i++){
                count++;
                mDatas.get(i).setCheck(false);
                if(count>=mDatas.size()-1){
                    for (ChooseQueDto dto:mDatas) {
                        if(dto.getId().equals(chooseId)){
                            dto.setCheck(true);
                            chooseName=dto.getName();
                            choosePhone=dto.getPhone();
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        }
    }

    public String getChooseId(){
        return chooseId;
    }

    public String getChooseName(){
        return chooseName;
    }
    public String getChoosePhone(){
        return choosePhone;
    }

}
