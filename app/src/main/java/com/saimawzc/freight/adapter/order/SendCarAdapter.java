package com.saimawzc.freight.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.SendCarDto;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 派车
 */

public class SendCarAdapter extends BaseAdapter {

    private List<SendCarDto.SendCarData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private String type=1+"";
    public HashMap<Integer,Boolean> map=null;
    public OnTabClickListener onTabClickListener;
    private List<String>chooseIdList;
    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public OnItemCheckListener onItemChckListener;
    public void setOnItemCheckListener(OnItemCheckListener onItemClickListener) {
        this.onItemChckListener = onItemClickListener;
    }
    public interface OnItemCheckListener {
        void onItemClick(View view, int position, boolean isselect);
    }



    private boolean isShowCheck=false;
    public SendCarAdapter(List<SendCarDto.SendCarData> mDatas, Context mContext,String type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
        map = new HashMap<>();
        init();
    }

    public void setData(List<SendCarDto.SendCarData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    private void init() {
        if (null == mDatas||mDatas.size()<=0) {
            return;
        }else{
            for(int i=0;i<mDatas.size();i++){
                map.put(i, false);
            }
        }
    }
    public List<SendCarDto.SendCarData> getData() {
        return mDatas;
    }
    public void addMoreData(List<SendCarDto.SendCarData> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }
    public void setIsShowCheck(boolean ischeck){
        this.isShowCheck=ischeck;
        notifyDataSetChanged();

    }
    public boolean getShowCheck(){
        return isShowCheck;
    }
    public List<String>getList(){
        if(chooseIdList==null){
            chooseIdList=new ArrayList<>();
        }
        return chooseIdList;
    }
    public void setList(List<String>chooseIdList){
        this.chooseIdList=chooseIdList;
        notifyDataSetChanged();
    }

    public HashMap<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Boolean> map) {
        this.map = map;
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
            View view = mInflater.inflate(R.layout.item_sendcar, parent,
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
            SendCarDto.SendCarData dto=mDatas.get(position);
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
             ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
             ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
             ((ViewHolder) holder).fhUtil.setText(dto.getFromName());
             ((ViewHolder) holder).shUtil.setText(dto.getToName());

             ((ViewHolder) holder).tvTime.setText(dto.getCreateTime());
            ((ViewHolder) holder).tvDanhao.setText(dto.getDispatchCarNo());
            ((ViewHolder) holder).tvSiji.setText(dto.getSjName()+"|"+dto.getCarNo());
            ((ViewHolder) holder).tvtrant.setText(dto.getMaterialsName());
            if(isShowCheck){
                ((ViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).checkBox.setVisibility(View.GONE);
            }

            if(dto.getTranType()==1){
                 ((ViewHolder) holder).tvYunshuType.setText("汽运");
            }else {
                ((ViewHolder) holder).tvYunshuType.setText("船运");
            }

            if(type.equals("1")){//已派车
                ((ViewHolder) holder).viewTab1.setText("当前位置");
                if(dto.getTranType()==1){
                    ((ViewHolder) holder).viewTab2.setText("关闭派车单");
                }else {
                    ((ViewHolder) holder).viewTab2.setText("关闭派船单");
                }
                ((ViewHolder) holder).viewtab3.setVisibility(View.GONE);
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                setBtnStyle(mContext,((ViewHolder) holder).viewTab1,R.drawable.shape_list_btn,R.color.gray333);
                setBtnStyle(mContext,((ViewHolder) holder).viewTab2,R.drawable.shape_list_btn_red,R.color.red);
            }else if(type.equals("2")){//运输中

                ((ViewHolder) holder).viewTab1.setText("物流信息");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setText("当前位置");

                ((ViewHolder) holder).viewtab3.setText("同意撤单");
                if(dto.getIsCancel()==1){
                    ((ViewHolder) holder).viewtab3.setVisibility(View.VISIBLE);
                }else {
                    ((ViewHolder) holder).viewtab3.setVisibility(View.GONE);
                }
                setBtnStyle(mContext,((ViewHolder) holder).viewTab1,R.drawable.shape_list_btn,R.color.gray333);
               // setBtnStyle(mContext,((ViewHolder) holder).viewTab2,R.drawable.shape_list_btn_blue,R.color.white);
            }else if(type.equals("3")){//已经完成
                ((ViewHolder) holder).llSign.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvSignNum.setText(dto.getHzSignInWeight()+"");
                ((ViewHolder) holder).viewTab1.setText("地图轨迹");
                ((ViewHolder) holder).viewTab2.setText("客商信息");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewtab3.setVisibility(View.VISIBLE);
                setBtnStyle(mContext,((ViewHolder) holder).viewTab1,R.drawable.shape_list_btn,R.color.gray333);
                setBtnStyle(mContext,((ViewHolder) holder).viewTab2,R.drawable.shape_list_btn,R.color.gray333);
            }else if(type.equals("4")){//已关闭
                ((ViewHolder) holder).viewTab1.setText("停运详情");
                ((ViewHolder) holder).viewTab1.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).viewTab2.setVisibility(View.GONE);
                ((ViewHolder) holder).viewtab3.setVisibility(View.GONE);
                setBtnStyle(mContext,((ViewHolder) holder).viewTab1,R.drawable.shape_list_btn,R.color.gray333);

            }

            if(onTabClickListener!=null){
                ((ViewHolder) holder).viewTab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab1", position);
                    }
                });
                ((ViewHolder) holder).viewTab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab2", position);
                    }
                });
                ((ViewHolder) holder).viewtab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab3", position);
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
            if(chooseIdList!=null){
                if(chooseIdList.contains(dto.getId())){
                    ((ViewHolder) holder).checkBox.setChecked(true);
                }else {
                    ((ViewHolder) holder).checkBox.setChecked(false);
                }
            }else {
                ((ViewHolder) holder).checkBox.setChecked(false);
            }

            if(onItemChckListener!=null){
                ((ViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean tag;
                        if(((ViewHolder) holder).checkBox.isChecked()){
                            map.put(position,true);
                            tag=true;
                        }else {
                            map.put(position,false);
                            tag=false;

                        }
                        onItemChckListener.onItemClick(holder.itemView, position,tag);
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
        @BindView(R.id.tvAdress)TextView tvAdress;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.tvtime)TextView tvTime;
        @BindView(R.id.tvDanhao)TextView tvDanhao;
        @BindView(R.id.tvTrantType)TextView tvTrantType;
        @BindView(R.id.tvYunshuType)TextView tvYunshuType;
        @BindView(R.id.tvSignNum)TextView tvSignNum;//签收量
        @BindView(R.id.llSign) LinearLayout llSign;
        @BindView(R.id.from_company)TextView fhUtil;
        @BindView(R.id.to_company)TextView shUtil;
        @BindView(R.id.checkBox) CheckBox checkBox;



        @BindView(R.id.tvSiji)TextView tvSiji;
        @BindView(R.id.tvtrant)TextView tvtrant;
        @BindView(R.id.viewtab1)TextView viewTab1;
        @BindView(R.id.viewtab2)TextView viewTab2;
        @BindView(R.id.viewtab3)TextView viewtab3;

    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
