package com.android.wan.ui.fragment.system

import android.os.Handler
import androidx.fragment.app.Fragment
import com.android.wan.R
import com.android.wan.ui.activity.MainActivity
import com.android.wan.ui.adapter.BaseViewPagerAdapter
import com.android.wan.ui.view.ViewPagerUtil
import com.dq.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_public.tabLayout
import kotlinx.android.synthetic.main.fragment_system.*

class SystemFragment : BaseFragment() {

    private val mFragments: MutableList<Fragment> = mutableListOf()
    var titleList: MutableList<String> = mutableListOf()

    override fun getContentView(): Int? {
        return R.layout.fragment_system
    }

    override fun initView() {
        super.initView()
        mFragments.add(SystemSysFragment.createFragment())
        mFragments.add(SystemNavFragment.createFragment())
        titleList.add("体系")
        titleList.add("导航")
        val mAdapter = BaseViewPagerAdapter(
            (activity as MainActivity).supportFragmentManager,
            mFragments,
            titleList
        )
        viewPagerSystem.adapter = mAdapter
        viewPagerSystem.offscreenPageLimit = mFragments.size
        tabLayout.setViewPager(viewPagerSystem)
        tabLayout.currentTab = 0
        ViewPagerUtil().setAnim(viewPagerSystem)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        if (isIntercept) {
            Handler().postDelayed({
                if (tabLayout != null) {
                    tabLayout.currentTab = 0
                    viewPagerSystem.currentItem = 0
                }
            }, 300)
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        isIntercept = true
    }

    companion object {
        var isIntercept = true
        fun createFragment(): SystemFragment {
            return SystemFragment()
        }
    }
}