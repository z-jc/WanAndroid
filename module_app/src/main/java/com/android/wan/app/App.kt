package com.android.wan.app

import android.app.Application
import android.content.Context
import com.android.wan.config.AppConfig
import com.android.wan.config.AppConstant
import com.android.wan.util.RvAnimUtils
import com.dq.util.DateUtil
import com.dq.util.ILog
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
        context = this
    }

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(this, AppConstant.buglyAppId, false)
        LitePal.initialize(this)
        SPUtil.getInstance(context)
        AppConfig().setAdapterAnim(RvAnimUtils.RvAnim.SCALEIN)
        RxhttpUtil.init(this, true)
    }
}