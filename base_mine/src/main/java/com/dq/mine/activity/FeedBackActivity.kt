package com.dq.mine.activity

import android.os.Build
import android.text.TextUtils
import android.view.View
import com.dq.base_mine.R
import com.dq.mine.base.BaseActivity
import com.dq.util.ToastUtil
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * CreateName : Z-JC
 * Date : 2019/10/18
 * Describe :
 */
class FeedBackActivity : BaseActivity(), View.OnClickListener {

    override fun initView() {
        tvTitle.text = getString(R.string.text_feedback)
        btnSubmit.setOnClickListener(this)
        imgBack.setOnClickListener{finish()}
    }

    override fun getContentView(): Int? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        return R.layout.activity_feedback
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.btnSubmit) {
            val content =
                edContent.text.toString().trim { it <= ' ' }
            val phone =
                edPhone.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showShortToast(this, "反馈内容不能为空")
                return
            }
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showShortToast(this, "联系方式不能为空")
                return
            }
            ToastUtil.showShortToast(this, "提交成功")
            finish()
        }
    }
}