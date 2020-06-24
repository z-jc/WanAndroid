package com.dq.login.fragment

import com.dq.login.R
import com.dq.ui.base.BaseFragment

/**
 * FileName: LoginFragment
 * Author: admin
 * Date: 2020/6/24 10:51
 * Description:
 */
class LoginFragment : BaseFragment() {
    override fun getContentView(): Int? {
        return R.layout.fragment_login
    }

    companion object {
        fun createFragment(): LoginFragment {
            return LoginFragment()
        }
    }
}