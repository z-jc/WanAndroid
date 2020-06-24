package com.dq.util.http;

import android.content.Context;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.rxjava.rxlife.RxLife;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.entity.Progress;
import rxhttp.wrapper.param.RxHttp;

/**
 * FileName: RxhttpUtil
 * Author: admin
 * Date: 2020/5/14 15:27
 * Description:
 */
public class RxhttpUtil {

    @DefaultDomain
    public static String baseUrl = "https://www.wanandroid.com/";

    private static volatile RxhttpUtil instance = null;
    private static PersistentCookieJar mCookieJar = null;

    public static RxhttpUtil getInstance() {
        if (instance == null) {
            synchronized (RxhttpUtil.class) {
                if (instance == null) {
                    instance = new RxhttpUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化RxHttp
     *
     * @param debug false:非debug模式，true:debug模式
     * @param url   更换BaseUrl
     */
    public static void init(Context c, boolean debug, String url) {
        if (!TextUtils.isEmpty(url)) {
            baseUrl = url;
        }
        setOkhttp(c, debug);
    }

    /**
     * 初始化RxHttp
     *
     * @param debug false:非debug模式，true:debug模式
     */
    public static void init(Context c, boolean debug) {
        setOkhttp(c, debug);
    }

    private static void setOkhttp(Context c, boolean debug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        /**
         * 注入请求日志
         */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.cookieJar(getCookieJar(c));
        RxHttp.init(builder.build(), debug);
    }

    public static PersistentCookieJar getCookieJar(Context c) {
        if (mCookieJar == null) {
            mCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(c));
        }
        return mCookieJar;
    }

    /**
     * get 请求
     *
     * @param url          接口地址
     * @param a
     * @param httpCallBack
     */
    public void get(String url, FragmentActivity a, RxHttpCallBack httpCallBack) {
        RxHttp.get(url)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpCallBack.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        httpCallBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpCallBack.onFinish();
                    }
                });
    }

    /**
     * get 请求
     *
     * @param url          接口地址
     * @param map          请求参数
     * @param a
     * @param httpCallBack
     */
    public void get(String url, Map<String, String> map, FragmentActivity a, RxHttpCallBack httpCallBack) {
        RxHttp.get(url)
                .addAll(map)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpCallBack.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        httpCallBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpCallBack.onFinish();
                    }
                });
    }

    /**
     * get请求
     *
     * @param url            接口地址
     * @param map            请求参数
     * @param rxBaseResponse 请求返回的数据对象
     * @param a
     * @param httpResponse
     */
    public void get(String url, Map<String, String> map, RxBaseResponse rxBaseResponse, FragmentActivity a, RxHttpResponse httpResponse) {
        RxHttp.get(url)
                .addAll(map)
                .asResponse(rxBaseResponse.getClass())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<RxBaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpResponse.onStart();
                    }

                    @Override
                    public void onNext(RxBaseResponse rxBaseResponse) {
                        httpResponse.onSuccess(rxBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpResponse.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpResponse.onFinish();
                    }
                });
    }

    /**
     * post请求
     *
     * @param url          接口地址
     * @param map          请求参数
     * @param a
     * @param httpCallBack
     */
    public void post(String url, Map<String, String> map, FragmentActivity a, RxHttpCallBack httpCallBack) {
        RxHttp.postForm(url)
                .addAll(map)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpCallBack.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        httpCallBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpCallBack.onFinish();
                    }
                });
    }

    /**
     * post请求
     *
     * @param url          接口地址
     * @param map          请求参数
     * @param baseResponse 请求返回的数据对象
     * @param a
     * @param httpCallBack
     */
    public void post(String url, Map<String, String> map, RxBaseResponse baseResponse, FragmentActivity a, RxHttpResponse httpCallBack) {
        RxHttp.postForm(url)
                .addAll(map)
                .asObject(baseResponse.getClass())
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<RxBaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpCallBack.onStart();
                    }

                    @Override
                    public void onNext(RxBaseResponse s) {
                        httpCallBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpCallBack.onFinish();
                    }
                });
    }

    /**
     * post表单请求
     *
     * @param url          接口地址
     * @param map          请求参数
     * @param a
     * @param httpCallBack
     */
    public void postFrom(String url, Map<String, String> map, FragmentActivity a, RxHttpCallBack httpCallBack) {
        RxHttp.postForm(url)
                .addAll(map)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))     //界面销毁，自动关闭请求
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpCallBack.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        httpCallBack.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpCallBack.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpCallBack.onFinish();
                    }
                });
    }

    /**
     * 上传文件
     *
     * @param url        上传地址
     * @param key        文件key
     * @param filePath   本地文件地址
     * @param rxHttpFile 上传回调
     */
    public void uploadFile(String url, String key, String filePath, FragmentActivity a, RxHttpFile rxHttpFile) {
        RxHttp.postForm(url) //发送Form表单形式的Post请求
                .addFile(key, new File(filePath))
                .asUpload(new Consumer<Progress>() {
                    @Override
                    public void accept(Progress progress) throws Exception {
                        rxHttpFile.onProgress(progress.getProgress(), progress.getCurrentSize(), progress.getTotalSize());
                    }
                }, AndroidSchedulers.mainThread())
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        rxHttpFile.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        rxHttpFile.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rxHttpFile.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        rxHttpFile.onFinish();
                    }
                });
    }

    /**
     * 文件下载
     *
     * @param httpUrl  下载的文件网络位置
     * @param filePath 下载到本地的位置
     * @param a        activity
     * @param httpFile 下载回调
     */
    public void downloadFile(String httpUrl, String filePath, FragmentActivity a, RxHttpFile httpFile) {
        RxHttp.get(httpUrl)
                .asDownload(filePath, new Consumer<Progress>() {
                    @Override
                    public void accept(Progress progress) throws Exception {
                        httpFile.onProgress(progress.getProgress(), progress.getCurrentSize(), progress.getTotalSize());
                    }
                }, AndroidSchedulers.mainThread()) //指定主线程回调
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        httpFile.onStart();
                    }

                    @Override
                    public void onNext(String s) {
                        httpFile.onSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        httpFile.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        httpFile.onFinish();
                    }
                });
    }

    /**
     * 链式请求实例,可根据实际需求做调整
     *
     * @param a
     * @param url1 第一个接口地址
     * @param url2 第二个接口地址
     * @param map1 第一个接口请求的参数
     * @param map2 第二个接口请求的参数
     */
    public void post(FragmentActivity a, String url1, String url2, Map<String, String> map1, Map<String, String> map2, RxHttpCallBack callBack) {
        RxHttp.postForm(url1)
                .addAll(map1)
                .asString()
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        return RxHttp.postForm(url2)
                                .addAll(map2)
                                .subscribeOnCurrent()
                                .asString();
                    }
                })
                .as(RxLife.asOnMain(a))
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onStart();
                    }

                    @Override
                    public void onNext(Object o) {
                        callBack.onSuccess(o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        callBack.onFinish();
                    }
                });
    }

    public interface RxHttpCallBack {
        /**
         * 开始请求
         */
        void onStart();

        /**
         * 请求成功
         *
         * @param response
         */
        void onSuccess(String response);

        /**
         * 请求异常
         *
         * @param error
         */
        void onError(String error);

        /**
         * 请求结束
         */
        void onFinish();
    }

    public interface RxHttpResponse {
        /**
         * 开始请求
         */
        void onStart();

        /**
         * 请求成功
         *
         * @param response
         */
        void onSuccess(RxBaseResponse response);

        /**
         * 请求异常
         *
         * @param error
         */
        void onError(String error);

        /**
         * 请求结束
         */
        void onFinish();
    }

    public interface RxHttpFile {
        /**
         * 开始请求
         */
        void onStart();

        /**
         * 请求成功
         *
         * @param response
         */
        void onSuccess(String response);

        /**
         * 上传进度或下载进度
         *
         * @param progress    当前进度
         * @param currentSize 已上传或下载字节大小
         * @param totalSize   总字节大小
         */
        void onProgress(int progress, long currentSize, long totalSize);

        /**
         * 请求异常
         *
         * @param error
         */
        void onError(String error);

        /**
         * 请求结束
         */
        void onFinish();
    }
}