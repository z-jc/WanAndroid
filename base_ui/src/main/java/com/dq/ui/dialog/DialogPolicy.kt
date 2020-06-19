package com.dq.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.dq.ui.R
import kotlinx.android.synthetic.main.dialog_policy.*

/**
 * Author : Z-JC
 * Date : 2020/1/10
 * Description :
 */
class DialogPolicy(private val mContext: Context, private val policyLister: OnPolicyListener?) :
    AlertDialog(mContext, R.style.dialog_loading) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_policy)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        init()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    private fun init() {
        val style = SpannableStringBuilder()
        //设置文字
        style.append("1.在浏览使用时，我们会收集、使用设备信息用户体验优化。\n")
        style.append("2.我们可能会申请未知权限，用于推荐。\n")
        style.append("3.您可以查看完整版《用户协议》和《隐私政策》以便了解我们收集、使用、共享、存储信息的情况，以及会信息的保护措施。\n\n\n")
        style.append("您可以选择点击“同意”按钮接受我们的服务")
        //设置部分文字点击事件
        val clickablePolicy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                policyLister?.onUser()
            }

            override fun updateDrawState(ds: TextPaint) { //重写该方法去掉下划线
                ds.isUnderlineText = false
            }
        }
        val clickableUser: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                policyLister?.onPolicy()
            }

            override fun updateDrawState(ds: TextPaint) { //重写该方法去掉下划线
                ds.isUnderlineText = false
            }
        }
        style.setSpan(clickablePolicy, 59, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(clickableUser, 66, 72, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSuper.text = style

        //设置部分文字颜色
        val foregroundPolicy =
            ForegroundColorSpan(Color.parseColor("#434FF2"))
        val foregroundUser =
            ForegroundColorSpan(Color.parseColor("#434FF2"))
        style.setSpan(foregroundPolicy, 59, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(foregroundUser, 66, 72, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //配置给TextView
        tvSuper.movementMethod = LinkMovementMethod.getInstance()
        tvSuper.text = style
        ivAgreed.setOnClickListener(View.OnClickListener { policyLister?.onAgreed(this@DialogPolicy) })
        ivCancel.setOnClickListener(View.OnClickListener { policyLister?.onCancel(this@DialogPolicy) })
    }

    interface OnPolicyListener {
        fun onPolicy() //隐私政策
        fun onUser() //用户协议
        fun onCancel(dialogPolicy: DialogPolicy?) //再看看
        fun onAgreed(dialogPolicy: DialogPolicy?) //同意
    }

}