package com.saimawzc.freight.presenter;

import android.content.Context;

import com.saimawzc.freight.modle.main.MainModel;
import com.saimawzc.freight.modle.main.MainModelImple;
import com.saimawzc.freight.modle.mine.MineModel;
import com.saimawzc.freight.modle.mine.MineModelImple;
import com.saimawzc.freight.view.DriverMainView;
import com.saimawzc.freight.view.mine.MineView;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2020/8/8.
 */

public class MainPersonter {

    private Context mContext;
    MainModel model;
    DriverMainView view;

    public MainPersonter(DriverMainView view, Context context) {
        this.view = view;
        this.mContext = context;
        model=new MainModelImple();
    }


    public void getCarriveList(){
        model.getCarrierList(view);
    }
    public void getLessess(){
        model.getLessessList(view);
    }
    public void getFram(){
        model.getDlilog(view);
    }

    public void gWFence(String waybillId,String highEnclosureId,String warnType,String location, List<String>alreadyList){
        model.uploadGwWl(view,waybillId,highEnclosureId,warnType,location, alreadyList);
    }

    public void getYdInfo(){
        model.getYdInfo(view);
    }

    public void deleteFile(File file) {
        if(!file.exists()){
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if(files==null||files.length==0){
                return;
            }
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if(f.getAbsolutePath().contains("aqcheck")||f.getAbsolutePath().contains("siji.apk")){
                    deleteFile(f);
                }
            }
            //file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }


}
