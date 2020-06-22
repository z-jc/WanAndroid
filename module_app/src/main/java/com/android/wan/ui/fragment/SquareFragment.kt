package com.android.wan.ui.fragment

import com.android.wan.R
import com.android.wan.ui.base.BaseFragment

class SquareFragment : BaseFragment() {

    override fun getContentView(): Int? {
        return R.layout.fragment_square
    }

    companion object {
        fun createFragment(): SquareFragment {
            return SquareFragment()
        }
    }
}