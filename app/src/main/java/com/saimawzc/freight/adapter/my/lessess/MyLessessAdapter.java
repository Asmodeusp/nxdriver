package com.saimawzc.freight.adapter.my.lessess;

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
import com.saimawzc.freight.dto.my.driver.MyDriverDto;
import com.saimawzc.freight.dto.my.lessess.MyLessessDto;
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
 * 我的承租人
 */

public class MyLessessAdapter extends BaseAdapter {
    private List<MyLessessDto> mDatas=new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private NormalDialog dialog;
    private int type=0;
    public MyLessessAdapter(List<MyLessessDto> mDatas, Context mContext,int type) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity=(BaseActivity) mContext;
        this.type=type;
    }

    public void setData(List<MyLessessDto> mDatas ) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<MyLessessDto> getData() {
        return mDatas;
    }
    public void addMoreData(List<MyLessessDto> newDatas) {
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
            View view = mInflater.inflate(R.layout.item_mylessess, parent,
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
            final MyLessessDto lessessDto=mDatas.get(position);
            if(type==1){
                ((ViewHolder) holder).imgUnbind.setVisibility(View.VISIBLE);
            }else {
                ((ViewHolder) holder).imgUnbind.setVisibility(View.GONE);
            }
            String role=lessessDto.getRoleType();
            ((ViewHolder) holder).tvName.setText("承租人:"+lessessDto.getCarName());
            ((ViewHolder) holder).tvPhone.setText("联系方式:"+lessessDto.getPhone());

            if(lessessDto.getType()==1){
                ((ViewHolder) holder).tvCarNum.setText("车号:"+lessessDto.getCarNo());
                ((ViewHolder) holder).tvWeight.setText("载重:"+lessessDto.getCarLoad());
                ((ViewHolder) holder).tvShape.setText("车型:"+lessessDto.getCarTypeName());
            }else if(lessessDto.getType()==2){
                ((ViewHolder) holder).tvCarNum.setText("船号:"+lessessDto.getCarNo());
                ((ViewHolder) holder).tvWeight.setText("容积:"+lessessDto.getCarLoad());
                ((ViewHolder) holder).tvShape.setText("船型:"+lessessDto.getCarTypeName());
            }

            if(role.equals("1")){
                ((ViewHolder) holder).tvRole.setText("角色：货主");
            }else if(role.equals("2")){
                ((ViewHolder) holder).tvRole.setText("角色：承运商");
            }else if(role.equals("3")){
                ((ViewHolder) holder).tvRole.setText("角色:司机");
            }else if(role.equals("4")){
                ((ViewHolder) holder).tvRole.setText("角色:收货人");
            }
            ((ViewHolder) holder).imgUnbind.setOnClickListener(new View.OnClickListener() {
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
                                    unbindCarRelation(lessessDto.getId(),lessessDto.getType());

                                }
                            });
                    dialog.show();
                }
            });

            ImageLoadUtil.displayImage(mContext,lessessDto.getUserPicture(),((ViewHolder) holder).imageView);
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
        @BindView(R.id.tvname)TextView tvName;
        @BindView(R.id.imgView) CircleImageView imageView;
        @BindView(R.id.tvphone)TextView tvPhone;
        @BindView(R.id.tvcarnum)TextView tvCarNum;
        @BindView(R.id.tvweight)TextView tvWeight;
        @BindView(R.id.tvcarshape)TextView tvShape;
        @BindView(R.id.tvRole)TextView tvRole;
        @BindView(R.id.imgunBind)ImageView imgUnbind;


    }
    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
    public MineApi mineApi= Http.http.createApi(MineApi.class);
    private void unbindCarRelation(String carId,int type){
        activity.showLoadingDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",carId);
            jsonObject.put("type",type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,jsonObject.toString());
        mineApi.unbindLessessRelation(body).enqueue(new CallBack<EmptyDto>() {
            @Override
            public void success(EmptyDto response) {
                activity.dismissLoadingDialog();
                activity.showMessage("解绑成功");
                EventBus.getDefault().post(Constants.reshLessess);
            }

            @Override
            public void fail(String code, String message) {
                activity.dismissLoadingDialog();
                activity.showMessage(message);
            }
        });

    }
}
