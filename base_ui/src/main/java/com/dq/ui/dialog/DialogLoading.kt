package com.dq.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.dq.ui.R
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * FileName: DialogLoading
 * Author: admin
 * Date: 2020/5/21 14:28
 * Description:
 */
class DialogLoading(private val activity: Activity) :
    Dialog(activity, R.style.dialog_loading) {
    private var content: String? = null
    private var duration = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window!!.attributes
        lp.width = getScreenWidth(activity) / 4
        lp.height = getScreenWidth(activity) / 4
        window!!.attributes = lp
        if (TextUtils.isEmpty(content)) {
            tvContent.text = "加载中..."
        } else {
            tvContent.text = content
        }
        val rotateAnimation =
            AnimationUtils.loadAnimation(activity, R.anim.anim_loading)
        val lin = LinearInterpolator()
        rotateAnimation.interpolator = lin
        imgIcon.startAnimation(rotateAnimation)
        if (duration != 0) {
            Handler().postDelayed({ dismiss() }, duration.toLong())
        }
    }

    fun setContent(c: String?): DialogLoading {
        content = c
        return this
    }

    fun setDuration(d: Int): DialogLoading {
        duration = d
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
    }
}