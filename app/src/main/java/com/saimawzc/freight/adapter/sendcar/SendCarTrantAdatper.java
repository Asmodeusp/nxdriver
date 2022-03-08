package com.saimawzc.freight.adapter.sendcar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.order.MyplanOrderAdapter;
import com.saimawzc.freight.dto.order.OrderManageRoleDto;
import com.saimawzc.freight.dto.sendcar.DriverTransDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.utils.dialog.PopupWindowUtil;

import java.util.List;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * 广告
 * content :
 */
public class SendCarTrantAdatper extends PagerAdapter {

    private Context mcontext;
    private List<DriverTransDto> murls;

    public SendCarTrantAdatper(Context context, List<DriverTransDto> urls) {
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
        final TextView tvMore=(TextView) mitemView.findViewById(R.id.tv_more);
        TextView tvSendAdress = (TextView) mitemView.findViewById(R.id.tvSendAdress);
        TextView tvReceiveAdress = (TextView) mitemView.findViewById(R.id.tvReceiveAdress);
        TextView tvCardId= (TextView) mitemView.findViewById(R.id.tvCardId);
        TextView tvPass= (TextView) mitemView.findViewById(R.id.tvPass);
        LinearLayout llCard=mitemView.findViewById(R.id.llCard);
        LinearLayout llPass=mitemView.findViewById(R.id.llPass);
        LinearLayout llcancel=mitemView.findViewById(R.id.llcancel);
        TextView tvCancelstatus=mitemView.findViewById(R.id.tvCancelstatus);

        TextView tvNoticeMsg=mitemView.findViewById(R.id.tvMessage);
        final TextView btnResh=mitemView.findViewById(R.id.btnResh);
        LinearLayout layNotice=mitemView.findViewById(R.id.llnotice);

        DriverTransDto dto=murls.get(position);
        tvNoticeMsg.setText("温馨提示：当前物料限制车辆"+dto.getLimitCar()+"位，您当前排位在第"+
                dto.getNowPosition()+"位，请稍等！");
        if(dto.isLineUp()){
           layNotice.setVisibility(View.VISIBLE);
        }else {
            layNotice.setVisibility(View.GONE);
        }
        tvName.setText(dto.getMaterialsNames());
        tvId.setText(dto.getWayBillNo());
        tvSendAdress.setText(dto.getFromUserAddress());
        tvReceiveAdress.setText(dto.getToUserAddress());
        if(dto.getBindSmartLock()==1){
            tvMore.setVisibility(View.VISIBLE);
            tvRole.setVisibility(View.GONE);
        }else {
            tvMore.setVisibility(View.VISIBLE);
            tvRole.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(dto.getCardId())){
            tvCardId.setText("固定卡号："+dto.getCardId());
        }else {
            tvCardId.setText("固定卡号：无");
        }
        if(!TextUtils.isEmpty(dto.getTakeCardPwd())){
            tvPass.setText("取卡密码："+dto.getTakeCardPwd());
            llPass.setVisibility(View.VISIBLE);
        }else {
            tvPass.setText("取卡密码：无");
        }
        if(dto.getCancelOrder()==1){
            llcancel.setVisibility(View.VISIBLE);
            tvCancelstatus.setText("该订单正在申请撤销中");
        }else {
            llcancel.setVisibility(View.GONE);
        }


          if(onItemClickListener!=null){
              tvRole.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      onItemClickListener.onItemClick("",tvRole,position);
                  }
              });
              tvMore.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      onItemClickListener.onItemClick("tvmore",tvMore,position);
                  }
              });
              btnResh.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      onItemClickListener.onItemClick("resh",btnResh,position);
                  }
              });
          }
        container.addView(mitemView);
        return mitemView;

    }


    public interface OnItemClickListener {
        void onItemClick(String str,View view, int position);
    }
    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
