package com.android.demo.ui.activity

import android.app.Dialog
import android.view.MenuItem
import androidx.annotation.NonNull
import com.android.demo.R
import com.android.demo.ui.fragment.FoundFragment
import com.android.demo.ui.fragment.HomeFragment
import com.android.demo.ui.fragment.MeFragment
import com.android.demo.ui.fragment.OnLineFragment
import com.dq.mine.base.BaseActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.ui.dialog.DialogCustom.ActionLister
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportFragment

/**
 * FileName: MainActivity
 * Author: admin
 * Date: 2020/6/17 19:35
 * Description:
 */
class MainActivity : BaseActivity() {

    private val fragments = arrayOfNulls<SupportFragment>(4)
    
    override fun initView() {
        super.initView()
        fragments[0] = HomeFragment.createFragment()
        fragments[1] = OnLineFragment.createFragment()
        fragments[2] = FoundFragment.createFragment()
        fragments[3] = MeFragment.createFramgemt()
        loadMultipleRootFragment(
            R.id.main_fragment,
            0,
            fragments[0],
            fragments[1],
            fragments[2],
            fragments[3]
        )
        navigation.selectedItemId = R.id.main_1
        navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
                when (menuItem.getItemId()) {
                    R.id.main_1 -> {
                        selectFragment(0)
                        return true
                    }
                    R.id.main_2 -> {
                        selectFragment(1)
                        return true
                    }
                    R.id.main_3 -> {
                        selectFragment(2)
                        return true
                    }
                    R.id.main_4 -> {
                        selectFragment(3)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun getContentView(): Int? {
        return R.layout.activity_main
    }

    private var nowPosition = 0

    private fun selectFragment(index: Int) {
        if (nowPosition != index) {
            val prePosition = nowPosition
            nowPosition = index
            showHideFragment(fragments[nowPosition], fragments[prePosition])
        }
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