package com.android.demo.ui.fragment

import com.android.demo.R
import com.android.demo.ui.base.BaseFragment

class FoundFragment : BaseFragment() {

    override fun getContentView(): Int? {
        return R.layout.fragment_found
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        setTitleBackground(BG_BLACK)
    }

    companion object {
        fun createFragment(): FoundFragment {
            return FoundFragment()
        }
    }
}