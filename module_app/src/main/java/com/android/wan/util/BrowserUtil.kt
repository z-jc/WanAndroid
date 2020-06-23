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
    fun startBrowser(a: Activity, url: String?) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val content_url = Uri.parse(url)
        intent.data = content_url
        a.startActivity(intent)
    }
}