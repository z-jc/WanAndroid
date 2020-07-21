package com.android.setting.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.android.setting.R
import com.bumptech.glide.Glide
import com.dq.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * Author:ZJC
 * Date:2020/2/7  15:55
 * Description:AboutActivity
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AboutActivity : BaseActivity() {

    private var verName = ""
    private var appName = ""
    private var icon = 0

    @SuppressLint("SetTextI18n")
    override fun initData() {
        verName = intent.getStringExtra(VER_NAME)
        appName = intent.getStringExtra(APP_NAME)
        icon = intent.getIntExtra(ICON, -1)
        tvTitle.text = "关于"
        Glide.with(this).load(icon).into(imgIcon)
        tvVersion.text = "$appName\tV$verName"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_about
    }

    companion object {
        const val VER_NAME = "ver_name"
        const val ICON = "icon"
        const val APP_NAME = "app_name"

        /**
         * @param a
         * @param verName 版本号
         * @param icon    app图标
         */
        fun startAct(a: Activity, verName: String?, appName: String?, icon: Int) {
            val intent = Intent(a, AboutActivity::class.java)
            intent.putExtra(VER_NAME, verName)
            intent.putExtra(APP_NAME, appName)
            intent.putExtra(ICON, icon)
            a.startActivity(intent)
        }
    }
}