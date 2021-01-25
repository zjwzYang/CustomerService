package com.qkd.customerservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.qkd.customerservice.bean.MsgOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created on 12/9/20 13:42
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class NetUtil {
    private static NetUtil netUtil;
    private final OkHttpClient okHttpClient;

    public static NetUtil get() {
        if (netUtil == null) {
            netUtil = new NetUtil();
        }
        return netUtil;
    }

    public NetUtil() {
        okHttpClient = new OkHttpClient();
    }


    /**
     * @param url      下载连接
     * @param listener 下载监听
     */

    public void download(Context context, final String url, final OnDownloadListener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        File savePath = context.getCacheDir();
        // file:///data/user/0/com.qkd.customerservice/cache/1608866179196temp.mp3
        final String destFileDir = savePath.getAbsolutePath();
        // http://47.114.100.72/files/media/1608791322730c4ec714d-f334-4278-8227-4fcc6f3f4cbc.mp3
        int lastIndex = url.lastIndexOf("/");
        final String destFileName = url.substring(lastIndex + 1);

        File file = new File(savePath, destFileName);
        Log.i("NetUtil", "download: 文件存在" + file.exists() + file.getAbsolutePath());
        if (file.exists()) {
            listener.onDownloadSuccess(file);
            return;
        }


        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                //储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);

                try {

                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中更新进度条
                        listener.onDownloading(progress);
                        Log.i("NetUtil", "onResponse: 下载进度" + progress);
                    }
                    fos.flush();
                    //下载完成
                    Log.i("NetUtil", "onResponse: 下载完成" + file.getAbsolutePath());
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                } finally {

                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }

                }


            }
        });
    }


    public static void upLoadFile(final Map<String, Object> map, File file, final OnMsgCallBack callback) {
        String url = "http://47.114.100.72:8081//im/forwardMessage";
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", filename, body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("12345678", "onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                MsgOutput output = gson.fromJson(string, MsgOutput.class);
                if (output.getCode() == 505) {
                    callback.onSendFail(output.getMessage());
                } else {
                    callback.onSendSuccess();
                }
            }
        });

    }

    public interface OnMsgCallBack {
        void onSendSuccess();

        void onSendFail(String msg);
    }

    public static void upLoadVideoFile(File file, Callback callback) {
        String url = Constant.BASE_URL_CORE + "common/uploadMedia";
        OkHttpClient client = new OkHttpClient();

        SharedPreferences userSp = MyApp.getInstance().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        final String token = userSp.getString(Constant.USER_TOKEN, "");
        final String coreToken = userSp.getString(Constant.USER_CORE_TOKEN, "");
        final String identifier = userSp.getString(Constant.USER_IDENTIFIER, "");

        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("file", filename, body);
        }
        Request request = new Request.Builder().url(url)
                .addHeader("login-token", token)
                .addHeader("token", coreToken)
                .addHeader("identifier", identifier).post(requestBody.build()).build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(callback);

    }


    public interface OnDownloadListener {

        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */

        void onDownloadFailed(Exception e);
    }
}