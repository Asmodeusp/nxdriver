package com.saimawzc.freight.adapter.sendcar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.saimawzc.freight.dto.account.ReconclitionDto;
import com.saimawzc.freight.dto.sendcar.WaitExecuteDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2020/8/6.
 * 司机派车等待
 */

public class WaitExecuteAdpater extends BaseAdapter {

    private List<WaitExecuteDto.WaitExecuteData> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public WaitExecuteAdpater(List<WaitExecuteDto.WaitExecuteData> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        activity = (BaseActivity) mContext;
    }

    public void setData(List<WaitExecuteDto.WaitExecuteData> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<WaitExecuteDto.WaitExecuteData> getData() {
        return mDatas;
    }

    public void addMoreData(List<WaitExecuteDto.WaitExecuteData> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.item_waitexcute, parent,
                    false);
            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterHolder(mInflater.inflate(R.layout.layout_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            WaitExecuteDto.WaitExecuteData dto = mDatas.get(position);
            if (dto.getLcResult() != null && dto.getLcbh() != null) {
                if (dto.getLcbh().length() > 0 && Integer.parseInt(dto.getLcResult()) < 2) {
                    ((ViewHolder) holder).scanCodeButton.setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolder) holder).scanCodeButton.setVisibility(View.GONE);
                }
            }
            if (TextUtils.isEmpty(dto.getResTxt2())) {
                 ((ViewHolder) holder).resTxt2Linear.setVisibility(View.GONE);
            }else {
                ((ViewHolder) holder).resTxt2Linear.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).resTxt2Text.setText(dto.getResTxt2());
            }
            ((ViewHolder) holder).tvAdress.setText(dto.getFromUserAddress());
            ((ViewHolder) holder).tvAdressTo.setText(dto.getToUserAddress());
            ((ViewHolder) holder).tvFromCompany.setText(dto.getFromName());
            ((ViewHolder) holder).tvToCompany.setText(dto.getToName());
            ((ViewHolder) holder).tvCarriName.setText(dto.getCysName());
            ((ViewHolder) holder).tvCarNum.setText(dto.getBillNum() + "车");
            if (dto.getTranType() == 1) {
                ((ViewHolder) holder).tvTrantType.setText("车号");
            } else {
                ((ViewHolder) holder).tvTrantType.setText("船号");
            }
            ((ViewHolder) holder).tvCarNo.setText(dto.getCarNo());
            ((ViewHolder) holder).tvGoodName.setText(dto.getMaterialsNames());
            ((ViewHolder) holder).tvWeight.setText(dto.getTotalWeight() + dto.getUnitName());
            ((ViewHolder) holder).tvCreatTime.setText(dto.getTaskStartTime() + "-" + dto.getTaskEndTime());
            if (dto.getStatus() == 1) {//待执行
                ((ViewHolder) holder).tvStatus.setText("待执行");
                ((ViewHolder) holder).viewTab4.setText("开启任务");
            } else if (dto.getStatus() == 2) {//运输中
                ((ViewHolder) holder).tvStatus.setText("运输中");
                ((ViewHolder) holder).viewTab4.setText("查看任务");
            } else if (dto.getStatus() == 3) {//已完成
                ((ViewHolder) holder).tvStatus.setText("已完成");
                ((ViewHolder) holder).viewTab4.setText("开启任务");
            }


            if (onTabClickListener != null) {
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
                ((ViewHolder) holder).imgPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("callphone", position);
                    }
                });
                ((ViewHolder) holder).scanCodeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onTabClickListener.onItemClick("scanCodeButton", position);
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
        }
        if (holder instanceof FooterHolder) {

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
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @BindView(R.id.tvAdress)
        TextView tvAdress;
        @BindView(R.id.tvAdressTo)
        TextView tvAdressTo;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tvCarriName)
        TextView tvCarriName;
        @BindView(R.id.tvCarNo)
        TextView tvCarNo;
        @BindView(R.id.tvGoodName)
        TextView tvGoodName;
        @BindView(R.id.tvWeight)
        TextView tvWeight;
        @BindView(R.id.tvCreatTime)
        TextView tvCreatTime;
        @BindView(R.id.imgPhone)
        ImageView imgPhone;
        @BindView(R.id.tvtrantType)
        TextView tvTrantType;
        @BindView(R.id.viewtab1)
        TextView viewTab1;
        @BindView(R.id.viewtab2)
        TextView viewTab2;
        @BindView(R.id.viewtab3)
        TextView viewTab3;
        @BindView(R.id.viewtab4)
        TextView viewTab4;
        @BindView(R.id.from_company)
        TextView tvFromCompany;
        @BindView(R.id.to_company)
        TextView tvToCompany;
        @BindView(R.id.tvcarnum)
        TextView tvCarNum;
        @BindView(R.id.resTxt2Text)
        TextView resTxt2Text;
        @BindView(R.id.scanCodeButton)
        LinearLayout scanCodeButton;
        @BindView(R.id.resTxt2Linear)
        LinearLayout resTxt2Linear;


    }

    @Override
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


}
