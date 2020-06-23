package com.android.wan.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.android.wan.R
import com.android.wan.ui.view.LoadingUtil
import com.dq.mine.base.BaseActivity
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * Author:ZJC
 * Date:2020/2/7  16:01
 * Description:UserServiceAgreementActivity
 */
class ContentActivity : BaseActivity(),
    View.OnClickListener {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        LoadingUtil.showLoading(this,"加载中...")
        webUrl = intent.getStringExtra(URL)
        webTitle = intent.getStringExtra(TITLE)
        imgBack.setOnClickListener(this)
        val settings = webView.settings
        // 设置缩放
        settings.builtInZoomControls = false
        // 使页面适应用户屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        // 开启JavaScript
        settings.javaScriptEnabled = true
        webView.isVerticalScrollBarEnabled = false //隐藏垂直滚动条

        tvTitle.text = webTitle
        imgTitle.setImageResource(R.drawable.icon_detail)
        webView.loadUrl(webUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LoadingUtil.dismissLoading()
            }
        }
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_content
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgBack) {
            finish()
        }
    }

    companion object {
        var webUrl: String? = null
        var webTitle: String? = null

        const val URL: String = "url"
        const val TITLE: String = "title"

        fun startAct(activity: Activity, url: String, title: String) {
            val intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra(URL, url)
            intent.putExtra(TITLE, title)
            activity.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingUtil.dismissLoading()
    }
}