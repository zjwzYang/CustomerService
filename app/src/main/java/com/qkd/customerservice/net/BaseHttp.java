package com.qkd.customerservice.net;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qkd.customerservice.BuildConfig;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.activity.IndexActivity;
import com.qkd.customerservice.bean.ErrorBody;
import com.qkd.customerservice.net.service.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
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
 * @org 趣看点
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
                        //Log.i("Http请求参数", "onError: " + e.getMessage());
                        httpObserver.onError();
                        if (e instanceof HttpException) {
                            try {
                                ResponseBody body = ((HttpException) e).response().errorBody();
                                String bodyStr = body.string();
                                Log.i("Http请求参数", "onError111: " + bodyStr);
                                Gson gson = new Gson();
                                ErrorBody errorBody = gson.fromJson(bodyStr, ErrorBody.class);
                                if ("401".equals(errorBody.getCode())) {
                                    Intent intent = new Intent(MyApp.getInstance(), IndexActivity.class);
                                    MyApp.getInstance().startActivity(intent);
                                }
                                Toast.makeText(MyApp.getInstance(), errorBody.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException IOe) {
                                IOe.printStackTrace();
                            }
                        }
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
                if (BuildConfig.DEBUG) {
                    Log.i("Http请求参数：", message);
                }
            }
        });
        loggingInterceptor.setLevel(level);
        // 定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES);

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
                            .header("identifier", URLEncoder.encode(identifier, "utf-8"))
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
