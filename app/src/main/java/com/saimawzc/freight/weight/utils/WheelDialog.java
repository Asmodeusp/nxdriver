package com.saimawzc.freight.weight.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import com.saimawzc.freight.dto.account.AccountType;
import com.bigkoo.pickerview.OptionsPickerView;
import com.saimawzc.freight.dto.my.CarBrandDto;
import com.saimawzc.freight.dto.my.CarTypeDo;
import com.saimawzc.freight.dto.my.ShipTypeDo;
import com.saimawzc.freight.dto.order.error.ErrorReportDto;
import com.saimawzc.freight.dto.wallet.MsBankDto;
import com.saimawzc.freight.weight.utils.listen.WheelListener;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2020/8/7.
 */
public class WheelDialog<T> {

    Context context;

    public List<T>listDatas;

    private List<String>stringLists;

    public WheelDialog(Context c,List<T>datas,List<String>strings){
        this.context=c;
        this.listDatas=datas;
        this.stringLists=strings;
    }
    public WheelDialog(Context c){
        this.context=c;
    }
    public void Show(final WheelListener listener) {// 弹出条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context,
                new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                   if(listDatas.size()<=0){
                       return;
                   }
                    if(listDatas.get(options1)instanceof CarTypeDo ){
                        String name = ((CarTypeDo) listDatas.get(options1)).getCarTypeName();
                         String id = ((CarTypeDo) listDatas.get(options1)).getId();
                        listener.callback(name,id);
                    }else if(listDatas.get(options1)instanceof CarBrandDto){
                        String name = ((CarBrandDto) listDatas.get(options1)).getBrandName();
                        String id = ((CarBrandDto) listDatas.get(options1)).getId();
                        listener.callback(name,id);
                    }else if(listDatas.get(options1)instanceof ShipTypeDo){
                        String name = ((ShipTypeDo) listDatas.get(options1)).getShipTypeName();
                        String id = ((ShipTypeDo) listDatas.get(options1)).getShipType();
                        listener.callback(name,id);
                    }else if(listDatas.get(options1)instanceof AccountType){
                        String name = ((AccountType) listDatas.get(options1)).getRecordStatusName();
                        String id = ((AccountType) listDatas.get(options1)).getRecordStatus();
                        listener.callback(name,id);
                    }else if(listDatas.get(options1)instanceof MsBankDto){
                        String name = ((MsBankDto) listDatas.get(options1)).getOpenBranch();
                        String id = ((MsBankDto) listDatas.get(options1)).getBankName();
                        listener.callback(name,id);
                    }else if(listDatas.get(options1)instanceof ErrorReportDto){
                        String name = ((ErrorReportDto) listDatas.get(options1)).getTypeName();
                        String id = ((ErrorReportDto) listDatas.get(options1)).getId();
                        listener.callback(name,id);

                    }

            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(stringLists);//条件选择器

        pvOptions.show();
    }

    public void Show(final WheelListener listener, final List<String> stringLists){
        if(stringLists==null||stringLists.size()<=0){
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(context,
                new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                      listener.callback(stringLists.get(options1),"");
                    }
                })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)//设置文字大小
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(stringLists);//条件选择器

        pvOptions.show();
    }



}
