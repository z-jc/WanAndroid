package com.dq.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.dq.ui.R
import kotlinx.android.synthetic.main.dialog_custom.*

/**
 * FileName: DialogLoading
 * Author: admin
 * Date: 2020/5/21 14:28
 * Description:
 */
class DialogCustom(a: Activity) : Dialog(a, R.style.dialog_loading) {
    private var title: String? = null
    private var msg: String? = null
    private var left: String? = null
    private var right: String? = null
    private var clickLister: ActionLister? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContentView(R.layout.dialog_custom)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window!!.attributes
        lp.width = getScreenWidth(context) / 5 * 4
        window!!.attributes = lp
        if (TextUtils.isEmpty(title)) {
            tvTitle.visibility = View.GONE
        } else {
            tvTitle.text = title
        }
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.text = msg
        }
        if (TextUtils.isEmpty(left)) {
            tvCancel.text = "取消"
        } else {
            tvCancel.text = left
        }
        if (TextUtils.isEmpty(right)) {
            tvEnter.text = "确定"
        } else {
            tvEnter.setText(right)
        }
        tvCancel.setOnClickListener(View.OnClickListener {
            dismiss()
            if (clickLister != null) {
                clickLister!!.onLeftClick(this@DialogCustom)
            }
        })
        tvEnter.setOnClickListener(View.OnClickListener {
            dismiss()
            if (clickLister != null) {
                clickLister!!.onRightClick(this@DialogCustom)
            }
        })
    }

    fun setTitle(t: String?): DialogCustom {
        title = t
        return this
    }

    fun setMsg(m: String?): DialogCustom {
        msg = m
        return this
    }

    fun setLeft(l: String?): DialogCustom {
        left = l
        return this
    }

    fun setRight(r: String?): DialogCustom {
        right = r
        return this
    }

    fun setActionLister(lister: ActionLister?): DialogCustom {
        clickLister = lister
        return this
    }

    interface ActionLister {
        fun onLeftClick(dialog: Dialog?)
        fun onRightClick(dialog: Dialog?)
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