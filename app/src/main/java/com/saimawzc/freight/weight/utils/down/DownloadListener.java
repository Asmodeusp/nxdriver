package com.saimawzc.freight.weight.utils.down;

/**
 * Created by Administrator on 2018-08-03.
 */

public interface DownloadListener {
    void onStart();//下载开始

    void onProgress(int progress);//下载进度

    void onFinish(String path);//下载完成

    void onFail(String errorInfo);//下载失败
}
