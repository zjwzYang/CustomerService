package com.qkd.customerservice.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.net.service.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2020/8/17 13:06
 * 网络请求.
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class BaseHttp {

    public static RetrofitService getRetrofitService(String baseUrl) {
        RetrofitService service = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build().create(RetrofitService.class);
        return service;
    }

    public static <T extends BaseOutput> void subscribe(Observable<T> observable, final HttpObserver<T> httpObserver) {
        if (!isNetworkConnected(MyApp.getInstance())) {
            Toast.makeText(MyApp.getInstance(), "请检查网络", Toast.LENGTH_SHORT).show();
            return;
        }
        observable.subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        httpObserver.onError();
                    }

                    @Override
                    public void onNext(T t) {
//                        if (t.isSuccess()) {
                        httpObserver.onSuccess(t);
//                        } else {
//                            Toast.makeText(MyApp.getInstance(), (String) t.getErrorMsg(), Toast.LENGTH_SHORT).show();
//                            httpObserver.onError();
//                        }
                    }
                });
    }

    public interface HttpObserver<T> {
        void onSuccess(T t);

        void onError();
    }

    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取OkHttpClient
     * 用于打印请求参数
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        // 日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        // 新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("Http请求参数：", message);
            }
        });
        loggingInterceptor.setLevel(level);
        // 定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);

        SharedPreferences userSp = MyApp.getInstance().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        final String token = userSp.getString(Constant.USER_TOKEN, "");
        final String coreToken = userSp.getString(Constant.USER_CORE_TOKEN, "");
        final String identifier = userSp.getString(Constant.USER_IDENTIFIER, "");
        if (!TextUtils.isEmpty(token)) {
            httpClientBuilder.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("login-token", token)
                            .header("token", coreToken)
                            .header("identifier", identifier)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient build = httpClientBuilder.build();
        return build;
    }

}
