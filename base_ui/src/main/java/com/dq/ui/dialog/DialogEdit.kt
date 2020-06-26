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
import kotlinx.android.synthetic.main.dialog_custom.tvCancel
import kotlinx.android.synthetic.main.dialog_custom.tvEnter
import kotlinx.android.synthetic.main.dialog_edit.*

/**
 * FileName: DialogLoading
 * Author: admin
 * Date: 2020/5/21 14:28
 * Description:
 */
class DialogEdit(a: Activity) : Dialog(a, R.style.dialog_loading) {
    private var hint: String? = null
    private var clickLister: ActionLister? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        setContentView(R.layout.dialog_edit)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window!!.attributes
        lp.width = getScreenWidth(context) / 5 * 4
        window!!.attributes = lp
        if (!TextUtils.isEmpty(hint)) {
            edContent.hint = hint
        }
        tvCancel.text = "取消"
        tvEnter.text = "确定"
        tvCancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        tvEnter.setOnClickListener(View.OnClickListener {
            dismiss()
            if (clickLister != null) {
                clickLister!!.onSuccess(this@DialogEdit, edContent.text.toString())
            }
        })
    }

    fun setHint(hint: String): DialogEdit {
        this.hint = hint;
        return this
    }

    fun setActionLister(lister: ActionLister?): DialogEdit {
        clickLister = lister
        return this
    }

    interface ActionLister {
        fun onSuccess(dialog: Dialog?, value: String)
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