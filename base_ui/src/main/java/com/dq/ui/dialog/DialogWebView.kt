package com.dq.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.webkit.WebSettings
import com.dq.ui.R
import kotlinx.android.synthetic.main.dialog_webview.*

/**
 * FileName: DialogLoading
 * Author: admin
 * Date: 2020/5/21 14:28
 * Description:
 */
class DialogWebView(a: Activity) : Dialog(a, R.style.dialog_loading) {

    private var url: String? = null
    private var loadData: String? = null
    private var isShowContent = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContentView(R.layout.dialog_webview)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window!!.attributes
        lp.width = getScreenWidth(context)
        window!!.attributes = lp

        val settings = webView.settings
        // 设置缩放
        settings.builtInZoomControls = false
        // 使页面适应用户屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        // 开启JavaScript
        settings.javaScriptEnabled = true
        webView.isHorizontalScrollBarEnabled = false//水平不显示
        webView.isVerticalScrollBarEnabled = false //隐藏垂直滚动条

        if (isShowContent) {
            webView.loadUrl(url)
        } else {
            webView.loadDataWithBaseURL(
                null, loadData,
                "text/html", "utf-8", null
            );
        }
        imgClose.setOnClickListener { dismiss() }
    }

    fun setUrl(r: String?): DialogWebView {
        this.url = r
        return this
    }

    fun isShowContent(b: Boolean?): DialogWebView {
        this.isShowContent = b!!
        return this
    }

    fun setLoadData(r: String?): DialogWebView {
        this.loadData = r
        return this
    }

    override fun dismiss() {
        super.dismiss()
    }

    companion object {
        /**
         * 获取屏幕的宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val wm =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        /**
         * 获取屏幕的高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

    }
}