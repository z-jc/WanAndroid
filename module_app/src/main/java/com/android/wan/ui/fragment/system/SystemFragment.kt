package com.android.wan.ui.fragment.system

import com.android.wan.R
import com.dq.ui.base.BaseFragment

class SystemFragment : BaseFragment() {

    override fun getContentView(): Int? {
        return R.layout.fragment_system
    }

    companion object {
        fun createFragment(): SystemFragment {
            return SystemFragment()
        }
    }
}