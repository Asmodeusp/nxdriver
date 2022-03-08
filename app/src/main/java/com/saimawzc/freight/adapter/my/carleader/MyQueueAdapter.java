package com.saimawzc.freight.adapter.my.carleader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.saimawzc.freight.constants.Constants;
import com.saimawzc.freight.dto.EmptyDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
import com.saimawzc.freight.dto.my.queue.CarQueueDto;
import com.saimawzc.freight.weight.CircleImageView;
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
 *
 */

public class MyQueueAdapter extends BaseAdapter {
    private List<CarQueueDto.data> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private NormalDialog dialog;
    private int type=0;
    public MyQueueAdapter(List<CarQueueDto.data> mDatas, Context mContext, int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }

    public void setData(List<CarQueueDto.data> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<CarQueueDto.data> getData() {
        return mDatas;
    }
    public void addMoreData(List<CarQueueDto.data> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_queue, parent,
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
            final CarQueueDto.data queueDto=mDatas.get(position);
            if(type==1){
                ((ViewHolder) holder).llOper.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).llOper.setVisibility(View.VISIBLE);
            }
            ((ViewHolder) holder).tvUserName.setText(queueDto.getUserName());
            ((ViewHolder) holder).tvcarNo.setText(queueDto.getCarNo());
            ((ViewHolder) holder).tvName.setText(queueDto.getClientName()+"    "+queueDto.getPhone());

            ((ViewHolder) holder).tvRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定拒绝吗?")
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
                                    unbindCarRelation(queueDto.getId(),2);

                                }
                            });
                    dialog.show();
                }
            });
            ((ViewHolder) holder).tvAgreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new NormalDialog(mContext).isTitleShow(false)
                            .content("确定同意该车队长邀请吗?")
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
                                    unbindCarRelation(queueDto.getId(),1);

                                }
                            });
                    dialog.show();
                }
            });

            ImageLoadUtil.displayImage(mContext,queueDto.getPicture(),((ViewHolder) holder).imageView);
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
        @BindView(R.id.tvcarNo)TextView tvcarNo;
        @BindView(R.id.imgView) CircleImageView imageView;
        @BindView(R.id.tvName)TextView tvName;
        @BindView(R.id.lloper)
        LinearLayout llOper;
        @BindView(R.id.tvrefuse)
        TextView tvRefuse;
        @BindView(R.id.tvagree)
        TextView tvAgreen;
        @BindView(R.id.tvUserName)
        TextView tvUserName;



    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    private void unbindCarRelation(String carId,int status){

        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",carId);
            jsonObject.put("status",status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.SJoperQueue(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                Log.e("msg","发送了");
                EventBus.getDefault().post(Constants.reshMyQueue);
            }
            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
                activity.showMessage(message);
            }
        });

    }
}
