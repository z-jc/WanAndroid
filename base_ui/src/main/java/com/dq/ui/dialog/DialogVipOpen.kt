package com.dq.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.dq.ui.R
import kotlinx.android.synthetic.main.dialog_vip_open.*

/**
 * Created by Administrator on 2018\2\25 0025.
 */
class DialogVipOpen(
    private val mContext: Context,
    private val clickLister: OnClickLister?,
    private val resourcesId: Int,
    private val btnText: String
) : AlertDialog(mContext, R.style.dialog_loading) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_vip_open)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        init()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    private fun init() {
        imgVipopen.setImageResource(resourcesId)
        btnStart.text = btnText
        btnStart.setOnClickListener(View.OnClickListener { clickLister?.onStart(this@DialogVipOpen) })
        imgClose.setOnClickListener(View.OnClickListener { clickLister?.onClose(this@DialogVipOpen) })
    }

    interface OnClickLister {
        fun onStart(dialog: Dialog?)
        fun onClose(dialog: Dialog?)
    }

}