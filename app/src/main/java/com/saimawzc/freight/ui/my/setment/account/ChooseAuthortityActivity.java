package com.saimawzc.freight.ui.my.setment.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.saimawzc.freight.R;
import com.saimawzc.freight.adapter.BaseAdapter;
import com.saimawzc.freight.adapter.account.AuthorityAdapter;
import com.saimawzc.freight.base.BaseActivity;
import com.saimawzc.freight.dto.account.AuthorityDto;
import com.saimawzc.freight.dto.account.AuthorityDtoSerializ;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2020/8/6.
 * 选择组织机构
 */
public class ChooseAuthortityActivity extends BaseActivity {

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private AuthorityAdapter authorityAdapter;
    private List<AuthorityDto>mDatas=new ArrayList<>();
    int tag=0;

    @Override
    protected int getViewId() {
        return R.layout.activity_chooseauthortity;
    }

    @OnClick({R.id.tvuplever,R.id.tvdownlever,R.id.tvOrder})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvdownlever:
                if(tag>=mDatas.size()){
                    return;
                }
                fresh(mDatas.get(tag).getJsonObject(),mDatas.get(tag).getCurremtleve());
                break;
            case R.id.tvuplever:
                if(tag>=mDatas.size()){
                    return;
                }
                if((mDatas.get(tag).getCurremtleve()<0)){
                    return;
                }
                upfresh(mDatas.get(tag).getCurremtleve());
                break;
            case R.id.tvOrder:
                Intent intent=new Intent();
                AuthorityDtoSerializ dtoSerializ=new AuthorityDtoSerializ();
                dtoSerializ.setCompanyName(mDatas.get(tag).getCompanyName());
                dtoSerializ.setId(mDatas.get(tag).getId());
                dtoSerializ.setParentId(mDatas.get(tag).getParentId());
                intent.putExtra("data",dtoSerializ);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
    @Override
    protected void init() {
        setToolbar(toolbar,"组织机构");
        authorityAdapter=new AuthorityAdapter(mDatas,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(authorityAdapter);
        getData();
    }
    @Override
    protected void initListener() {
        authorityAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(mDatas.size()<=position){
                    return;
                }
                tag=position;
                authorityAdapter.setmPosition(position);
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }
    @Override
    protected void onGetBundle(Bundle bundle) {
    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).fitsLayoutOverlapEnable(true).
                statusBarDarkFont(true).init();
    }
    List<JSONArray>tt=new ArrayList<>();
    private void getData(){
        context.showLoadingDialog();
        orderApi.getAuthorityList().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                final String msg=response.body();
                context.dismissLoadingDialog();
                try {
                    if(TextUtils.isEmpty(msg)){
                        return;
                    }
                    JSONObject object=new JSONObject(msg);
                    JSONArray array =new JSONArray(object.getString("data"));
                    tt.add(array);
                    if(array.length()>0){
                            for(int i=0;i<array.length();i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                AuthorityDto dto=new AuthorityDto();
                                dto.setCompanyName(jsonObject.getString("companyName"));
                                dto.setId(jsonObject.getString("id"));
                                dto.setParentId(jsonObject.getString("parentId"));
                                dto.setJsonObject(jsonObject);
                                dto.setCurremtleve(0);
                                mDatas.add(dto);
                            }
                        authorityAdapter.notifyDataSetChanged();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                context.dismissLoadingDialog();
            }
        });
    }


    private void fresh(JSONObject object,int currentlevel){
        try {
            JSONArray array =new JSONArray(object.getString("children"));
            if(!tt.contains(array)){
                tt.add(array);
            }
            if(array.length()>0){
                mDatas.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    AuthorityDto dto=new AuthorityDto();
                    dto.setCompanyName(jsonObject.getString("companyName"));
                    dto.setId(jsonObject.getString("id"));
                    dto.setParentId(jsonObject.getString("parentId"));
                    dto.setJsonObject(jsonObject);
                    dto.setCurremtleve(currentlevel+1);
                    mDatas.add(dto);
                }
                authorityAdapter.setmPosition(0);
                tag=0;
                authorityAdapter.notifyDataSetChanged();
            }else {
                showMessage("没有了");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void upfresh(int currentlevel){
        JSONArray array=tt.get(currentlevel);
        try{
            if(array.length()>0){
                mDatas.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject = array.getJSONObject(i);
                    AuthorityDto dto=new AuthorityDto();
                    dto.setCompanyName(jsonObject.getString("companyName"));
                    dto.setId(jsonObject.getString("id"));
                    dto.setParentId(jsonObject.getString("parentId"));
                    dto.setJsonObject(jsonObject);
                    dto.setCurremtleve(currentlevel-1);
                    mDatas.add(dto);
                }
                authorityAdapter.setmPosition(0);
                tag=0;
                authorityAdapter.notifyDataSetChanged();
            }else {
                showMessage("没有了");
            }
        }catch (Exception E){

        }
    }
}
