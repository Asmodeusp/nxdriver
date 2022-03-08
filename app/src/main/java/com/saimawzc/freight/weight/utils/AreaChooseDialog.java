package com.saimawzc.freight.weight.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.dto.login.AreaDto;
import com.saimawzc.freight.modle.BasEModeImple;
import com.saimawzc.freight.weight.utils.hawk.Hawk;
import com.saimawzc.freight.weight.utils.http.CallBack;
import com.saimawzc.freight.weight.utils.listen.AreaListener;
import com.saimawzc.freight.weight.utils.preference.PreferenceKey;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.saimawzc.freight.base.BaseApplication.options1Items;

/**
 * Created by Administrator on 2020/8/5.
 * 地区选择器
 */

public class AreaChooseDialog extends BasEModeImple{


    private ArrayList<ArrayList<AreaDto.city>> options2Items=new ArrayList<>() ;//市
    private ArrayList<ArrayList<ArrayList<AreaDto.country>>> options3Items =new ArrayList<>();
    Context context;

    public AreaChooseDialog(Context c){
        this.context=c;
        if(options1Items==null){
            options1Items=Hawk.get(PreferenceKey.CITY_INFO);
        }
    }

    public ArrayList<AreaDto>  getData(){
        return options1Items;
    }


    public void initData(){
        if(options1Items==null){
            getArea();
            return;
        }
                options2Items.clear();
                options3Items.clear();
                for(int i=0;i<options1Items.size();i++){
                    ArrayList<AreaDto.city> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    ArrayList<ArrayList<AreaDto.country>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）
                    for (int c = 0; c < options1Items.get(i).getChildren().size(); c++) {//遍历该省份的所有城市
                        CityList.add(options1Items.get(i).getChildren().get(c));//添加城市
                        ArrayList<AreaDto.country> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                        City_AreaList.addAll(options1Items.get(i).getChildren().get(c).getChildren());
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                    }
                    /**
                     * 添加城市数据
                     */
                    options2Items.add(CityList);
                    /**
                     * 添加地区数据
                     */
                    options3Items.add(Province_AreaList);
                }
             Log.e("msg","加载数据成功");


    }

    public void show(final AreaListener listener){

        if(options1Items==null||options2Items.size()<=0||options3Items.size()<=0){
            return;
        }
        if(options1Items.size()<=0){
            Toast.makeText(context,"地区信息尚未加载",Toast.LENGTH_SHORT).show();
            return;
        }

        final OptionsPickerView pvOptions = new  OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String area=options1Items.get(options1).getName()+options2Items.get(options1).get(option2).getName()
                        +options3Items.get(options1).get(option2).get(options3).getName();

                String proviceID=options1Items.get(options1).getId();
                String proviceName=options1Items.get(options1).getName();
                String cityID=options2Items.get(options1).get(option2).getId();
                String cityName=options2Items.get(options1).get(option2).getName();;
                String countryID=options3Items.get(options1).get(option2).get(options3).getId();
                String countryName=options3Items.get(options1).get(option2).get(options3).getName();

                listener.getArea(area,proviceName,cityName,countryName,proviceID,cityID,countryID);


            }
        }).build();
        pvOptions.setPicker(options1Items,options2Items,options3Items);
        pvOptions.show();

    }

    private void getArea(){
        mineApi.getArea().enqueue(new CallBack<List<AreaDto>>() {
            @Override
            public void success(List<AreaDto> response) {
                Hawk.put(PreferenceKey.CITY_INFO,response);
                options1Items= (ArrayList<AreaDto>) response;
                options2Items.clear();
                options3Items.clear();
                for(int i=0;i<response.size();i++) {
                    ArrayList<AreaDto.city> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    ArrayList<ArrayList<AreaDto.country>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）
                    for (int c = 0; c < response.get(i).getChildren().size(); c++) {//遍历该省份的所有城市
                        CityList.add(response.get(i).getChildren().get(c));//添加城市
                        ArrayList<AreaDto.country> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                        City_AreaList.addAll(response.get(i).getChildren().get(c).getChildren());
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                    }
                    /**
                     * 添加城市数据
                     */
                    options2Items.add(CityList);
                    /**
                     * 添加地区数据
                     */
                    options3Items.add(Province_AreaList);
                }
            }
            @Override
            public void fail(String code, String message) {
            }
        });
    }

}
