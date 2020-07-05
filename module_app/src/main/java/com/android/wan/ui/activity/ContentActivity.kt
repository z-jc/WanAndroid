package com.android.wan.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.android.wan.R
import com.android.wan.ui.dialog.DialogTitle
import com.android.wan.ui.view.SatelliteMenuView
import com.android.wan.util.BrowserUtil
import com.dq.ui.base.BaseActivity
import com.dq.util.ILog
import com.dq.util.ShareUtil
import com.dq.util.ToastUtil
import kotlinx.android.synthetic.main.activity_content.*

/**
 * Author:ZJC
 * Date:2020/2/7  16:01
 * Description:ContentActivity
 */
class ContentActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
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
        webView.loadUrl(webUrl)

        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE)
                } else {
                    if (View.INVISIBLE === progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE)
                    }
                    progressBar.setProgress(newProgress)
                }
                super.onProgressChanged(view, newProgress)
            }
        })

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