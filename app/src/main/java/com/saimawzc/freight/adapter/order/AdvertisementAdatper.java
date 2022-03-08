package com.saimawzc.freight.adapter.order;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.saimawzc.freight.R;
import com.saimawzc.freight.dto.order.OrderManageRoleDto;

import java.util.List;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * 广告
 * content :
 */
public class AdvertisementAdatper extends PagerAdapter {

    private Context mcontext;

    private List<OrderManageRoleDto.mapData> murls;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public AdvertisementAdatper(Context context, List<OrderManageRoleDto.mapData> urls) {
        mcontext = context;
        murls = urls;

    }


    @Override
    public int getCount() {
        if (murls == null) return 0;
        return murls.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LinearLayout mitemView = (LinearLayout) LayoutInflater.from(mcontext).
                inflate(R.layout.item_role, null);
        TextView tvName = (TextView) mitemView.findViewById(R.id.tvName);
        TextView tvId = (TextView) mitemView.findViewById(R.id.tvId);
        final TextView tvRole = (TextView) mitemView.findViewById(R.id.tvRole);
        TextView tvSendAdress = (TextView) mitemView.findViewById(R.id.tvSendAdress);
        TextView tvReceiveAdress = (TextView) mitemView.findViewById(R.id.tvReceiveAdress);
        LinearLayout llCard=mitemView.findViewById(R.id.llCard);
        LinearLayout llPass=mitemView.findViewById(R.id.llPass);
        final TextView tvMore=(TextView) mitemView.findViewById(R.id.tv_more);
        tvMore.setVisibility(View.GONE);
        tvRole.setVisibility(View.VISIBLE);
        OrderManageRoleDto.mapData dto=murls.get(position);
        tvName.setText(dto.getMaterialsName());
        tvId.setText(dto.getWayBillNo());
        tvSendAdress.setText(dto.getFromUserAddress());
        tvReceiveAdress.setText(dto.getToUserAddress());
        llCard.setVisibility(View.GONE);
        llPass.setVisibility(View.GONE);
        container.addView(mitemView);
        if (onItemClickListener != null) {
            tvRole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(tvRole, position);
                }
            });


        }
        return mitemView;

    }


    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
