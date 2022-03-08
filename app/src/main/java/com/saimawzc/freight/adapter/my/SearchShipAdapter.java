package com.saimawzc.freight.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.FooterHolder;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.car.ship.SearchShipDto;
import com.saimawzc.freight.weight.utils.RepeatClickUtil;
import com.saimawzc.freight.weight.utils.api.mine.MineApi;
import com.saimawzc.freight.weight.utils.dialog.BounceTopEnter;
import com.saimawzc.freight.weight.utils.dialog.NormalDialog;
import com.saimawzc.freight.weight.utils.dialog.OnBtnClickL;
import com.saimawzc.freight.weight.utils.dialog.SlideBottomExit;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.http.Http;
import com.saimawzc.freight.weight.utils.loadimg.ImageLoadUtil;

import org.greenrobot.eventbus.EventBus;
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
 * 搜索船舶适配器
 */

public class SearchShipAdapter extends BaseAdapter {
    private List<SearchShipDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private int type=0;
    private NormalDialog dialog;
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    public SearchShipAdapter(List<SearchShipDto> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
    }
    public SearchShipAdapter(List<SearchShipDto> mDatas, Context mContext, int type) {
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
            View view = mInflater.inflate(R.layout.item_searchship, parent,
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
            if(type==1||type==2||type==3){
                ((ViewHolder) holder).tvAdd.setVisibility(View.GONE);
                if(searchCarDto.getIfRegister()==1){
                    ((ViewHolder) holder).imgUndind.setVisibility(View.GONE);
                }else {
                    ((ViewHolder) holder).imgUndind.setVisibility(View.VISIBLE);
                }
            }else {
                ((ViewHolder) holder).imgUndind.setVisibility(View.GONE);
            }
            if(type==2){//待审核
                ((ViewHolder) holder).tvStatus.setVisibility(View.VISIBLE);
                if(searchCarDto.getIfRegister()==1){
                    ((ViewHolder) holder).tvStatus.setText("待平台审核");
                }else {
                    ((ViewHolder) holder).tvStatus.setText("待车主同意");
                }
            }else if(type==1){//已通过
                ((ViewHolder) holder).tvStatus.setVisibility(View.VISIBLE);
                if(searchCarDto.getIfRegister()==1){
                    ((ViewHolder) holder).tvStatus.setText("平台已审核");
                }else {
                    ((ViewHolder) holder).tvStatus.setText("车主已同意");
                }
            }else  if(type==3){//未通过
                ((ViewHolder) holder).tvStatus.setVisibility(View.VISIBLE);
                if(searchCarDto.getIfRegister()==1){
                    ((ViewHolder) holder).tvStatus.setText("平台未审核");
                }else {
                    ((ViewHolder) holder).tvStatus.setText("车主未同意");
                }
            }
            if(searchCarDto!=null){
                ((ViewHolder) holder).tvCardNum.setText(searchCarDto.getShipNumberId());
                ((ViewHolder) holder).tvCarShape.setText(searchCarDto.getShipTypeName());
                ((ViewHolder) holder).tvWeight.setText(searchCarDto.getShipVolume()+"");
                ((ViewHolder) holder).tvUser.setText(searchCarDto.getBusinessName());
                ImageLoadUtil.displayImage(mContext,searchCarDto.getSideView(),((ViewHolder) holder).imageView);
            }

            ((ViewHolder) holder).tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!RepeatClickUtil.isFastClick()){
                        activity.showMessage("您操作太频繁，请稍后再试");
                        return;
                    }
                    addCarRelation(searchCarDto.getId());
                }
            });

            ((ViewHolder) holder).imgUndind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("取消关联之后将从列表删除，是否取消关联?")
                            .contentGravity(Gravity.CENTER)
                            .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                            .btnNum(2).btnText("取消", "确定");
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if(!activity.isDestroy(activity)){
                                        dialog.dismiss();
                                    }
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    if(!activity.isDestroy(activity)){
                                        dialog.dismiss();
                                    }
                                    unbindCarRelation(searchCarDto.getId());

                                }
                            });
                    dialog.show();
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
        return mDatas.size() == 0 ? 0 : mDatas.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @BindView(R.id.cardnum)TextView tvCardNum;
        @BindView(R.id.tvadd)TextView tvAdd;
        @BindView(R.id.imgView)ImageView imageView;
        @BindView(R.id.tvcarshap)TextView tvCarShape;
        @BindView(R.id.tvweight)TextView tvWeight;
        @BindView(R.id.tvuser)TextView tvUser;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.imgunBind)ImageView imgUndind;
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
            jsonObject.put("shipId",carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.addShipRelation(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                activity.showMessage("申请成功");
                EventBus.getDefault().post(Constants.reshShip);
                activity.finish();

            }

            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
               activity.showMessage(message);
            }
        });

    }
    private void unbindCarRelation(String carId){
        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",carId);
            jsonObject.put("type",2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.unbindCarRelation(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                activity.showMessage("解绑成功");
                EventBus.getDefault().post(Constants.reshShip);
                //activity.finish();
            }

            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
                activity.showMessage(message);
            }
        });

    }
}
