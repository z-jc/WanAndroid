package com.dq.login.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * FileName: LoginFragmentPagerAdapter
 * Author: admin
 * Date: 2020/6/24 10:55
 * Description:
 */
class LoginFragmentPagerAdapter(
    fm: FragmentManager?,
    fragmentList: List<Fragment>?
) :
    FragmentPagerAdapter(fm!!) {

    private val mfragmentList: List<Fragment> = fragmentList!!

    //获取集合中的某个项
    override fun getItem(position: Int): Fragment {
        return mfragmentList[position]
    }

    //返回绘制项的数目
    override fun getCount(): Int {
        return mfragmentList.size
    }

}