package com.android.wan.ui.fragment

import com.android.wan.R
import com.dq.ui.base.BaseFragment

class ProjectFragment : BaseFragment() {

    override fun getContentView(): Int? {
        return R.layout.fragment_project
    }

    companion object {
        fun createFramgemt(): ProjectFragment {
            return ProjectFragment()
        }
    }
}