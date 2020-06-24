package com.android.wan.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.android.wan.R
import kotlinx.android.synthetic.main.pop_title_share.*

/**
 * FileName: MeSelectPopWindow
 * Author: admin
 * Date: 2020/5/27 17:28
 * Description:
 */
class DialogTitle(private val activity: Activity, private val lister: OnActionLister) :
    Dialog(activity, R.style.dialog_loading) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_title_share)
        setViewLocation()
        setCanceledOnTouchOutside(true) //外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private fun setViewLocation() {
        tv_share.setOnClickListener { lister.onShare() }
        tv_collection.setOnClickListener { lister.onCollection() }
        tv_browser.setOnClickListener { lister.onBrowser() }
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val window = this.window
        val lp = window!!.attributes
        lp.x = 0
        lp.y = height
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置显示位置
        onWindowAttributesChanged(lp)
    }

    interface OnActionLister {
        fun onShare()
        fun onCollection()
        fun onBrowser()
    }

}