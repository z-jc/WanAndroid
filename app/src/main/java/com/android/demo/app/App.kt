package com.android.demo.app

import android.app.Application
import android.content.Context
import com.dq.util.SPUtil
import com.dq.util.http.RxhttpUtil
import org.litepal.LitePal

/**
 * FileName: App
 * Author: admin
 * Date: 2020/6/17 19:30
 * Description:
 */
class App : Application() {

    var context : Context? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this);//初始化litepal数据库
        context = getApplicationContext();
        SPUtil.getInstance(context);
        RxhttpUtil.init(true);
    }
}