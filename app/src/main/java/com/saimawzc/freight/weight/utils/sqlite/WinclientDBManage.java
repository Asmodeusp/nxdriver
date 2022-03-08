package com.saimawzc.freight.weight.utils.sqlite;


import com.saimawzc.freight.base.BaseApplication;

/**
 * Created by Administrator on 2018-04-02.
 * 本地数据库的管理
 */

public class WinclientDBManage {

    static private WinclientDBManage dbMgr = new WinclientDBManage();
    private WinClientOpenHelper dbHelper;

    private WinclientDBManage() {
        dbHelper = WinClientOpenHelper.getInstance(BaseApplication.getInstance().getApplicationContext());
    }

    public static synchronized WinclientDBManage getInstance() {
        if (dbMgr == null) {
            dbMgr = new WinclientDBManage();
        }
        return dbMgr;
    }

    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();
        }
        dbMgr = null;
    }



}
