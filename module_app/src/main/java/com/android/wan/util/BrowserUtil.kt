package com.android.wan.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.dq.util.ILog

/**
 * FileName: BrowserUtil
 * Author: admin
 * Date: 2020/6/23 17:25
 * Description:
 */
object BrowserUtil {

    /**
     * 手机自带浏览器打开
     */
    fun startLocal(a: Activity, url: String?) {
        ILog.e("跳转到浏览器:${a.javaClass.simpleName}")
        val uri = Uri.parse(url)
        val intent =
            Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        a.startActivity(intent)
    }
}