package com.saimawzc.freight.adapter.sendcar.route;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.sendcar.LogistoicAdpater;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.sendcar.LogistoicDto;
import com.saimawzc.freight.ui.order.OrderMainActivity;
import com.saimawzc.freight.weight.CircleImageView;
import com.saimawzc.freight.weight.WrapContentLinearLayoutManager;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * creator :  tlp
 * time    :  2016/5/4.
 * 物流规划
 * content :
 */
public class LogistoicViewPageAdatper extends PagerAdapter {

    private Context mcontext;

    private List<LogistoicDto> murls;
    private BaseActivity activity;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public LogistoicViewPageAdatper(Context context, List<LogistoicDto> urls) {
        mcontext = context;
        murls = urls;
        activity=(BaseActivity) mcontext;
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
                inflate(R.layout.item_logistica_viewpage, null);
        RecyclerView cv=mitemView.findViewById(R.id.cv);
        TextView tvDanHao = (TextView) mitemView.findViewById(R.id.tvDanHao);
        TextView tvName = (TextView) mitemView.findViewById(R.id.tvName);
        TextView tvPhone = (TextView) mitemView.findViewById(R.id.tvPhone);
        TextView carNo = (TextView) mitemView.findViewById(R.id.carNo);
        CircleImageView imgHead=(CircleImageView)mitemView.findViewById(R.id.imgHead);
        TextView tvCy=mitemView.findViewById(R.id.tvcy);
        final LogistoicDto dto=murls.get(position);
        if(dto!=null){
            tvDanHao.setText(dto.getWayBillNo());
            tvName.setText(dto.getSjName());
            carNo.setText(dto.getCarNo());
            WrapContentLinearLayoutManager layoutManager=new WrapContentLinearLayoutManager(mcontext, LinearLayoutManager.VERTICAL, false);
            LogistoicAdpater adpater=new LogistoicAdpater(dto.getTransportLogList(),mcontext);;
            adpater.setId(dto.getId());
            adpater.setWeighingDoubt(dto.getWeighingDoubt());
            cv.setLayoutManager(layoutManager);
            cv.setAdapter(adpater);
            adpater.setPointMark(dto.getPoundReMark());
            if(dto.getWeighingDoubt()!=2){
                cv.scrollToPosition(dto.getTransportLogList().size() - 1);
                tvCy.setVisibility(View.VISIBLE);
            }else {
                tvCy.setVisibility(View.GONE);
            }
            adpater.setOnTabClickListener(new BaseAdapter.OnTabClickListener() {
                @Override
                public void onItemClick(String type, int position) {
                        Bundle bundle=new Bundle();
                        bundle.putString("id",dto.getId());
                        bundle.putString("tag",type);
                        bundle.putString("from","rearriveorder");
                        activity.readyGo(OrderMainActivity.class,bundle);
                }
            });
        }
        container.addView(mitemView);
        return mitemView;

    }


    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
