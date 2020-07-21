package com.android.wan.ui.activity

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.android.wan.R
import com.android.wan.ui.adapter.BaseViewPagerAdapter
import com.android.wan.ui.fragment.main.MainFragment
import com.android.wan.ui.fragment.main.MenuFragment
import com.dq.ui.base.BaseActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.ui.dialog.DialogCustom.ActionLister
import kotlinx.android.synthetic.main.activity_main.*

/**
 * FileName: MainActivity
 * Author: admin
 * Date: 2020/6/17 19:35
 * Description:
 */
class MainActivity : BaseActivity() {

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()

    override fun initView() {
        super.initView()
        fragments.add(MenuFragment.createFragment())
        fragments.add(MainFragment.createFragment())
        titles.add("menu")
        titles.add("main")
        val adapter = BaseViewPagerAdapter(supportFragmentManager, fragments, titles)
        viewPagerMain.setAdapter(adapter)
        viewPagerMain.currentItem = 1
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_main
    }

    override fun onBackPressedSupport() {
        DialogCustom(this)
            .setMsg("确认退出吗？")
            .setActionLister(object : ActionLister {
                override fun onLeftClick(dialog: Dialog?) {
                    dialog!!.dismiss()
                }

                override fun onRightClick(dialog: Dialog?) {
                    dialog!!.dismiss()
                    finish()
                }
            }).show()
    }
}