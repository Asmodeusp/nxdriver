package com.saimawzc.freight.adapter.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.order.bill.MyPlanOrderDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 */

public class MyplanOrderAdapter extends BaseAdapter {

    private List<MyPlanOrderDto.planOrderData> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onItemTabClickListener) {
        this.onTabClickListener = onItemTabClickListener;
    }
    public interface OnTabClickListener {
        void onItemClick(String type, int position);
    }

    public MyplanOrderAdapter(List<MyPlanOrderDto.planOrderData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }

    public void setData(List<MyPlanOrderDto.planOrderData> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MyPlanOrderDto.planOrderData> getData() {
        return mDatas;
    }
    public void addMoreData(List<MyPlanOrderDto.planOrderData> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_planorder, parent,
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
            MyPlanOrderDto.planOrderData dto=mDatas.get(position);
            ImageLoadUtil.displayImage(mContext,dto.getCompanyLogo(),((ViewHolder) holder).imageView);
            String util=dto.getUnitName();
            if(dto.getTranType()==1){//派车
                ((ViewHolder) holder).viewTab1.setText("派车");
            }else if(dto.getTranType()==2){//派船
                ((ViewHolder) holder).viewTab1.setText("派船");
            }
            if(dto.getPlanStatus()==2){
                ((ViewHolder) holder).tvStatus.setText("已暂停");

            }else if(dto.getPlanStatus()==3){
                ((ViewHolder) holder).tvStatus.setText("已终止");
            }else {
                ((ViewHolder) holder).tvStatus.setText("");
            }
            ((ViewHolder) holder).viewTab3.setVisibility(View.GONE);
            if(dto.getIsSx()==1){
              ((ViewHolder) holder).viewTab4.setText("刷新");
              ((ViewHolder) holder).viewTab4.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).viewTab4.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                ((ViewHolder) holder).resTxt2TextLin.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).resTxt2TextLin.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }

            ((ViewHolder) holder).tvBigNo.setText(dto.getPlanWayBillNo());
            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());//发货单位
            ((ViewHolder) holder).tvToCopmpany.setText(dto.getToName());
            ((ViewHolder) holder).tvGoodesNmae.setText(dto.getMaterialsName());
            ((ViewHolder) holder).tvGoodsNum.setText(dto.getPointWeight()+util);
            ((ViewHolder) holder).tvOverNum.setText(dto.getOverWeight()+util);
            if(dto.getBindSmartLock()==1){
                ((ViewHolder) holder).tvIsBindLock.setText("是（请确认司机已下发电子锁）");
                ((ViewHolder) holder).tvIsBindLock.setTextColor(mContext.getResources().getColor(R.color.red));
            }else {
                ((ViewHolder) holder).tvIsBindLock.setText("否");
                ((ViewHolder) holder).tvIsBindLock.setTextColor(mContext.getResources().getColor(R.color.gray999));
            }
            ((ViewHolder) holder).tvQkNum.setText(dto.getTakeCardWeight()+util);
            ((ViewHolder) holder).tvZtSun.setText(dto.getUnderWay()+util);
            ((ViewHolder) holder).tvconsultsnum.setText(dto.getConsult()+util);
            if(dto.isPrice()==1){
                ((ViewHolder) holder).layPrice.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvPrice.setText(dto.getPrice());
            }else {
                ((ViewHolder) holder).layPrice.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).llMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupWindowUtil popupWindowUtil = new PopupWindowUtil.Builder()
                            .setContext(mContext) //设置 context
                            .setContentView(R.layout.dialog_sendcar) //设置布局文件
                            .setOutSideCancel(true) //设置点击外部取消
                            .setwidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setheight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setFouse(true)
                            .builder()
                            .showAsLaction(((ViewHolder) holder).llMore, Gravity.LEFT,0,0);

                    popupWindowUtil.setOnClickListener(new int[]{R.id.rlapplystop, R.id.rlzhuabao,R.id.creat_smallorder}, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle;
                            switch (v.getId()){
                                case R.id.rlapplystop://
                                    popupWindowUtil.dismiss();
                                    bundle=new Bundle();
                                    bundle.putString("from","applypause");
                                    bundle.putString("id",mDatas.get(position).getId());
                                    bundle.putString("type",1+"");
                                    activity.readyGo(OrderMainActivity.class,bundle);
                                    break;
                                case R.id.rlzhuabao://
                                     activity.showMessage("转包功能正在抓紧开发中。。。。");
                                    popupWindowUtil.dismiss();
                                    break;
                                case R.id.creat_smallorder:
                                    bundle=new Bundle();
                                    bundle.putString("from","addwaybill");
                                    bundle.putSerializable("data",mDatas.get(position));
                                    activity.readyGo(OrderMainActivity.class,bundle);
                                    break;
                            }
                        }
                    });
                }
            });



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
                ((ViewHolder) holder).viewTab3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab3", position);
                    }
                });
                ((ViewHolder) holder).viewTab4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("tab4", position);
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
        @BindView(R.id.image) ImageView imageView;

        @BindView(R.id.tvgoodsname)TextView tvGoodesNmae;
        @BindView(R.id.tvbigNo)TextView tvBigNo;
        @BindView(R.id.tvgoodsnum)TextView tvGoodsNum;
        @BindView(R.id.tvovernum)TextView tvOverNum;
        @BindView(R.id.viewtab1)TextView viewTab1;
        @BindView(R.id.viewtab2)TextView viewTab2;
        @BindView(R.id.viewtab3)TextView viewTab3;
        @BindView(R.id.viewtab4)TextView viewTab4;
        @BindView(R.id.tvAdressTo)TextView tvAdressTo;
        @BindView(R.id.tvstatus)TextView tvStatus;
        @BindView(R.id.ico_more) LinearLayout llMore;
        @BindView(R.id.from_company)TextView tvFromCompany;
        @BindView(R.id.to_company)TextView tvToCopmpany;
        @BindView(R.id.tvISbindlock)TextView tvIsBindLock;
        @BindView(R.id.tvqukasnum)TextView tvQkNum;
        @BindView(R.id.tvztsnum)TextView tvZtSun;
        @BindView(R.id.tvconsultsnum)TextView tvconsultsnum;

        @BindView(R.id.layprice)LinearLayout layPrice;
        @BindView(R.id.tvprice)TextView tvPrice;
        @BindView(R.id.resTxt2Text)TextView resTxt2Text;
        @BindView(R.id.resTxt2TextLin)LinearLayout resTxt2TextLin;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
