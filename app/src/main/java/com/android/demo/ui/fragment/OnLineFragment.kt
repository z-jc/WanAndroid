package com.android.demo.ui.fragment

import android.view.View
import com.android.demo.R
import com.android.demo.ui.base.BaseFragment
import kotlinx.android.synthetic.main.title_bar_base.*

class OnLineFragment : BaseFragment() {

    override fun onSupportVisible() {
        super.onSupportVisible()
        setTitleBackground(BG_WHITE)
    }

    override fun getContentView(): Int? {
        return R.layout.fragment_online
    }

    override fun initView() {
        imgBack.visibility = View.GONE
        tvTitle.text = "问答"
    }

    companion object {
        fun createFragment(): OnLineFragment {
            return OnLineFragment()
        }
    }
}