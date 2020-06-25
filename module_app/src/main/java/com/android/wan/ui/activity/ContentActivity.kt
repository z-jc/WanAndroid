package com.android.wan.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.android.wan.R
import com.android.wan.ui.dialog.DialogTitle
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.BrowserUtil
import com.dq.ui.base.BaseActivity
import com.dq.util.ShareUtil
import com.dq.util.ToastUtil
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * Author:ZJC
 * Date:2020/2/7  16:01
 * Description:UserServiceAgreementActivity
 */
class ContentActivity : BaseActivity() {

    private var dialogTitle: DialogTitle? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        LoadingUtil.showLoading(this, "加载中...")
        webUrl = intent.getStringExtra(URL)
        webTitle = intent.getStringExtra(TITLE)
        imgBack.setOnClickListener { finish() }
        imgTitle.setOnClickListener { showTitlePop() }
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
        tvTitle.isSelected = true
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

    fun showTitlePop() {
        dialogTitle = DialogTitle(this, object : DialogTitle.OnActionLister {
            override fun onShare() {
                dialogTitle!!.dismiss()
                ShareUtil.shareTxt(this@ContentActivity, javaClass.simpleName, webTitle, webUrl)
            }

            override fun onCollection() {
                dialogTitle!!.dismiss()
                ToastUtil.showShortToast(this@ContentActivity, "已添加收藏")
            }

            override fun onBrowser() {
                dialogTitle!!.dismiss()
                BrowserUtil.startBrowser(this@ContentActivity, webUrl)
            }
        })
        dialogTitle!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingUtil.dismissLoading()
        if (dialogTitle != null) {
            dialogTitle!!.dismiss()
            dialogTitle = null
        }
    }
}