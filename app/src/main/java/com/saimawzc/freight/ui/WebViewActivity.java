package com.saimawzc.freight.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;


import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;


/**
 * Created by longbh on 16/6/14.
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final int TYPE_LOAD_URL = 1000;
    private static final int TYPE_LOAD_HTML = 2000;
    private static final int TYPE_LOAD_Word = 3000;
    private WebView mWebView;
    private String mTitle = "", mUrl = "", mHtmlContent = "";
    private int mType = TYPE_LOAD_URL;
    @BindView(R.id.view)RelativeLayout view;
    private  String contectType="";//不为空为下载，为空不传展示
    @BindView(R.id.right_btn)TextView tvRightBtn;

    public static void loadUrl(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putInt("type", TYPE_LOAD_URL);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void loadUrl(Context context, String title, String url,int i) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putInt("type", TYPE_LOAD_URL);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static void loadUrl(Context context, String title, String url,String type) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putInt("type", TYPE_LOAD_URL);
        bundle.putString("contectType", type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static void loadWord(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        bundle.putInt("type", TYPE_LOAD_Word);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static void loadHtml(Context context, String title, String htmlContent) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", htmlContent);
        bundle.putInt("type", TYPE_LOAD_HTML);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    String photoPath;
    @Override
    protected void onGetBundle(Bundle bundle) {
        try{
            mType = bundle.getInt("type", TYPE_LOAD_URL);
        }catch (Exception e){

        }
        try {
            mTitle = bundle.getString("title");
        }catch (Exception e){

        }
        try{
            contectType=bundle.getString("contectType");
        }catch (Exception e){

        }

        setToolbar(toolbar,mTitle);
        if(bundle!=null){
            if (mType == TYPE_LOAD_URL||mType==TYPE_LOAD_Word) {
                mUrl = bundle.getString("url");
            } else if (mType == TYPE_LOAD_HTML) {
                mHtmlContent = bundle.getString("content");
            }
        }
    }
    public Bitmap getScreenPhoto(RelativeLayout waterPhoto) {
        View view = waterPhoto;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap saveBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        bitmap = null;
        return saveBitmap;
    }



    @Override
    protected int getViewId() {
        return R.layout.activity_webview;
    }

    @Override
    public void init() {
        mWebView = (WebView) findViewById(R.id.web_view);
        WebSettings mSettings = mWebView.getSettings();// 支持获取手势焦点
        mWebView.requestFocusFromTouch();
        mWebView.setHorizontalFadingEdgeEnabled(true);
        mWebView.setVerticalFadingEdgeEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        ///////7.0部分手机滑动卡顿，不加VIVO手机有问题
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        // 支持JS
        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setDisplayZoomControls(true);
        mSettings.setLoadWithOverviewMode(true);
        // 支持插件
        mSettings.setPluginState(WebSettings.PluginState.ON);
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setSavePassword(true);
        mSettings.setSaveFormData(true);
        mSettings.setGeolocationEnabled(true);;
        // 支持缩放
        mSettings.setSupportZoom(true);//就是这个属性把我搞惨了，缩放设置true 可能会有一系列问题
        // 隐藏原声缩放控件
        mSettings.setDisplayZoomControls(false);
        // 支持内容重新布局
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.supportMultipleWindows();
        mSettings.setSupportMultipleWindows(true);
        // 设置缓存模式
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//不缓存
       // mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先缓存模式，
        mSettings.setAppCacheEnabled(true);
        mSettings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
        // 设置可访问文件
        mSettings.setAllowFileAccess(true);
        mSettings.setNeedInitialFocus(true);
        mWebView.requestDisallowInterceptTouchEvent(true);
        // 支持自定加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mSettings.setLoadsImagesAutomatically(true);
        } else {
            mSettings.setLoadsImagesAutomatically(false);
        }
        if(!mTitle.equals("后台设置")){
            mSettings.setTextSize(WebSettings.TextSize.NORMAL);
        }else {
            mSettings.setTextSize(WebSettings.TextSize.NORMAL);
        }
        mSettings.setNeedInitialFocus(true);
        // 设定编码格式
        mSettings.setDefaultTextEncodingName("UTF-8");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        if(!TextUtils.isEmpty(contectType)){

        }else {
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    try{
                        if (url.startsWith("weixin://wap/pay?")) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else {
                            if(!TextUtils.isEmpty(mUrl) && (!mUrl.startsWith("http"))){
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                                return true;
                            }
                        }

                    }catch (Exception e){
                        return false;
                    }
                    return false;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (isDestroy(WebViewActivity.this)) {
                        return;
                    }
                    mWebView.getSettings().setBlockNetworkImage(true);

                }
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                    handler.proceed();
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (isDestroy(WebViewActivity.this)) {
                        return;
                    }
                    mWebView.getSettings().setBlockNetworkImage(false);

                }
            });
        }
        //加载需要显示的网页
        if (mType == TYPE_LOAD_URL) {
            if (!TextUtils.isEmpty(mUrl) && (!mUrl.startsWith("http"))) {
                mUrl = "http://" + mUrl;
            }
            Log.e("msg","加载网址："+mUrl);

            if(mUrl.contains("H5PayPage")){
               // Map<String, String> extraHeaders = new HashMap<>();
               //extraHeaders.put("Referer", "http://wxpay.nyjdgl.com");
                mWebView.loadUrl(mUrl);
            }else {
                mWebView.loadUrl(mUrl);
            }

        } else if (mType == TYPE_LOAD_HTML) {
            mWebView.loadDataWithBaseURL(null, mHtmlContent, "text/html", "UTF-8", null);
        }else if(mType == TYPE_LOAD_Word){
            Log.e("msg","当前"+"file://"+mUrl);
            mWebView.loadUrl("file://"+mUrl);
        }
    }


    @Override
    protected void initListener() {

    }



    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(mWebView.canGoBack())
            {
                mWebView.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();
                //System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public String chageDate(String str){
        str=str.replace("/Date(","").replace(")/","");
        String time = str.substring(0,str.length()-5);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mWebView.pauseTimers();
    }


}