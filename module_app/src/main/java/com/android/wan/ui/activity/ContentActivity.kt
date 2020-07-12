package com.android.wan.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.webkit.*
import com.android.wan.R
import com.android.wan.ui.view.SatelliteMenuView
import com.android.wan.util.BrowserUtil
import com.dq.ui.base.BaseActivity
import com.dq.util.ILog
import com.dq.util.ShareUtil
import kotlinx.android.synthetic.main.activity_content.*

/**
 * Author:ZJC
 * Date:2020/2/7  16:01
 * Description:ContentActivity
 */
class ContentActivity : BaseActivity() {

    override fun initView() {
        webUrl = intent.getStringExtra(URL)
        webTitle = intent.getStringExtra(TITLE)

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

        webView.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                view!!.loadUrl(webUrl)
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request != null) {
                        view!!.loadUrl(webUrl)
                        return true
                    }
                }
                return false
            }
        }
        ILog.e("加载的地址:$webUrl")
        webView.run { webView!!.loadUrl(webUrl) }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.visibility = View.INVISIBLE
                } else {
                    if (View.INVISIBLE === progressBar.getVisibility()) {
                        progressBar.visibility = View.VISIBLE
                    }
                    progressBar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }

        srcMenu.setOnMenuItemClickListener(object : SatelliteMenuView.OnMenuItemClickListener {
            override fun onClick(view: View?, position: Int) { //子菜单点击事件
                btnPlus.setImageResource(R.drawable.icon_content_edit)
                fadeOut(rlShan)
                if (position == 1) {
                    //收藏
                }
                if (position == 2) {
                    BrowserUtil.startLocal(this@ContentActivity, webUrl)
                }
                if (position == 3) {
                    ShareUtil.shareTxt(this@ContentActivity, javaClass.simpleName, webTitle, webUrl)
                }
            }
        })

        srcMenu.setMainOnClickLister(object : SatelliteMenuView.OnMainClickListener {
            override fun onClick() { //主菜单点击事件
                if (srcMenu.isOpen) { //打开
                    btnPlus.setImageResource(R.drawable.icon_content_edit)
                    fadeOut(rlShan)
                }
            }

            override fun onFinish() {
                finish()
            }

            override fun onLongClick() {
                if (!srcMenu.isOpen) {
                    btnPlus.setImageResource(R.drawable.icon_content_close)
                    fadeIn(rlShan)
                }
            }
        })
    }

    fun fadeIn(
        view: View,
        startAlpha: Float,
        endAlpha: Float,
        duration: Long
    ) {
        if (view.visibility == View.VISIBLE) return
        view.visibility = View.VISIBLE
        val animation: Animation = AlphaAnimation(startAlpha, endAlpha)
        animation.setDuration(duration)
        view.startAnimation(animation)
    }

    /**
     * 隐藏到显示渐变
     *
     * @param view
     */
    fun fadeIn(view: View) {
        fadeIn(view, 0f, 1f, 500)
        view.isEnabled = true
    }

    /**
     * 显示到隐藏渐变
     *
     * @param view
     */
    fun fadeOut(view: View) {
        if (view.visibility != View.VISIBLE) return
        view.isEnabled = false
        val animation: Animation =
            AlphaAnimation(1f, 0f)
        animation.duration = 500
        view.startAnimation(animation)
        view.visibility = View.GONE
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_BLACK)
        return R.layout.activity_content
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
}