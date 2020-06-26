package com.android.setting.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import android.webkit.WebSettings
import com.android.setting.R
import com.dq.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_service_agreement.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * Author:ZJC
 * Date:2020/2/7  16:01
 * Description:UserServiceAgreementActivity
 */
class UserServiceAgreementActivity : BaseActivity(),
    View.OnClickListener {

    override fun initView() {
        val type = intent.getIntExtra(TYPE, 0)
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
        if (type == 0) { //用户协议
            tvTitle.text = "用户协议"
            webView.loadUrl(getAssetsUser)
        } else if (type == 1) { //隐私协议
            tvTitle.text = "隐私政策"
            webView.loadUrl(getAssetsPrivacy)
        }
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_user_service_agreement
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgBack) {
            finish()
        }
    }

    companion object {
        const val TYPE_USER = 0
        const val TYPE_PRIVACY = 1
        const val TYPE = "type"

        /**
         * 用户隐私政策协议
         */
        var getAssetsPrivacy = "file:///android_asset/index_policy.html" //隐私协议
        var getAssetsUser = "file:///android_asset/index_user.html"
        fun startAct(activity: Activity, type: Int) {
            val intent = Intent(activity, UserServiceAgreementActivity::class.java)
            intent.putExtra(TYPE, type)
            activity.startActivity(intent)
        }
    }
}