package com.saimawzc.freight.weight.utils.preview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.sendcar.HackyViewPager;
import com.saimawzc.freight.base.BaseActivity;

import java.util.ArrayList;

public class PlusImageActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private HackyViewPager viewPager; //展示图片的ViewPager
    private TextView positionTv; //图片的位置，第几张图片
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private ViewPagerAdapter mAdapter;
    private String type="";
    ImageView tvDelete;



    @Override
    protected int getViewId() {
        return R.layout.activity_plus_image;
    }

    @Override
    protected void init() {
        imgList = getIntent().getStringArrayListExtra("imglist");
        mPosition = getIntent().getIntExtra("currentpos", 0);
        initView();
        try {
            type=getIntent().getStringExtra("delation");
        }catch (Exception e){

        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }

    private void initView() {
        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        positionTv = (TextView) findViewById(R.id.position_tv);
        findViewById(R.id.back_iv).setOnClickListener(this);
        findViewById(R.id.delete_iv).setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        tvDelete=findViewById(R.id.delete_iv);
        if(!TextUtils.isEmpty(type)){
            tvDelete.setVisibility(View.GONE);
        }
        if(imgList!=null&&imgList.size()>0){
            mAdapter = new ViewPagerAdapter(this, imgList);
            viewPager.setAdapter(mAdapter);
            positionTv.setText(mPosition + 1 + "/" + imgList.size());
            viewPager.setCurrentItem(mPosition);
        }
    }

    //删除图片
    private void deletePic() {
        if(imgList.size()>0){
            imgList.remove(mPosition); //从数据源移除删除的图片
            setPosition();
        }else {
            finish();
        }

    }

    //设置当前位置
    private void setPosition() {
        if(imgList.size()<=0){
            finish();
        }
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    //返回上一个页面
    private void back() {
        if(!TextUtils.isEmpty(type)){
            finish();
        }else {
            Intent intent = getIntent();
            intent.putStringArrayListExtra("imglist", imgList);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(imgList.size()<=position){
            return;
        }
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                //返回
                back();
                break;
            case R.id.delete_iv:
                //删除图片
                deletePic();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
