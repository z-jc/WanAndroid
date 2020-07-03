package com.android.wan.util

import android.app.Activity
import android.content.Intent
import android.net.Uri

/**
 * FileName: BrowserUtil
 * Author: admin
 * Date: 2020/6/23 17:25
 * Description:
 */
object BrowserUtil {

    /**
     * 浏览器打开
     */
    fun startBrowser(a: Activity, url: String?) {
        val intent = Intent()
        intent.action = "android.intent.category.VIEW"
        val content_url = Uri.parse(url)
        intent.data = content_url
        a.startActivity(intent)
    }

    /**
     * 网页打开
     */
    fun startLocal(a: Activity, url: String?) {
        val uri = Uri.parse(url)
        val intent =
            Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        a.startActivity(intent)
    }
}