package com.android.wan.ui.fragment

import com.android.wan.R
import com.dq.ui.base.BaseFragment

class PublicFragment : BaseFragment() {

    override fun getContentView(): Int? {
        return R.layout.fragment_public
    }

    companion object {
        fun createFragment(): PublicFragment {
            return PublicFragment()
        }
    }
}