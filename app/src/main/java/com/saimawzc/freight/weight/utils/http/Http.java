package com.saimawzc.freight.weight.utils.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import static com.saimawzc.freight.constants.Constants.Baseurl;
/**
 * Created by longbh on 16/5/24.
 */
public class Http {
    public static String user_session = "";
    public static Http http;
    private Retrofit mRetrofit;
    private Http(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                //.sslSocketFactory(createSSLSocketFactory())//信任证书
                //.hostnameVerifier(new TrustAllHostnameVerifier())//信任证书
                .retryOnConnectionFailure(true)//重连
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public static boolean isLogin() {
        return !"".equals(user_session);
    }

    public static void initHttp(Context context) {
        http = new Http(context);
    }
    public <T> T createApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public  boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
        }
        return false;
    }
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, null);

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
 }
}
