package com.android.wan.app

import android.app.Application
import android.content.Context
import com.android.wan.config.AppConfig
import com.dq.util.SPUtil
import com.dq.util.http.RxhttpUtil
import com.tencent.bugly.crashreport.CrashReport
import org.litepal.LitePal

/**
 * FileName: App
 * Author: admin
 * Date: 2020/6/17 19:30
 * Description:
 */
class App : Application() {

    var context: Context? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(this, AppConfig.buglyAppId, false);
        LitePal.initialize(this);//初始化litepal数据库
        context = this
        SPUtil.getInstance(context);
        RxhttpUtil.init(true);
    }
}