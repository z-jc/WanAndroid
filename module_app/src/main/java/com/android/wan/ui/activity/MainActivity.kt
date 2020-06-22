package com.android.wan.ui.activity

import android.app.Dialog
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.drawerlayout.widget.DrawerLayout
import com.android.wan.R
import com.android.wan.ui.fragment.*
import com.dq.mine.base.BaseActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.ui.dialog.DialogCustom.ActionLister
import com.dq.util.DisplayUtil
import com.dq.util.ToastUtil
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

    private val fragments = arrayOfNulls<SupportFragment>(5)

    override fun initView() {
        super.initView()
        imgBack.visibility = View.VISIBLE
        imgBack.setImageResource(R.drawable.icon_mian_title_left)
        fragments[0] = HomeFragment.createFragment()
        fragments[1] = SquareFragment.createFragment()
        fragments[2] = PublicFragment.createFragment()
        fragments[3] = SystemFragment.createFragment()
        fragments[4] = ProjectFragment.createFramgemt()
        loadMultipleRootFragment(
            R.id.main_fragment,
            0,
            fragments[0],
            fragments[1],
            fragments[2],
            fragments[3],
            fragments[4]
        )
        navigation.selectedItemId = R.id.main_1
        tvTitle.text = resources.getText(R.string.text_main_bottom_home)
        tvTitle.textSize = 16F
        imgBack.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        imgMenuTitle.setOnClickListener {
            ToastUtil.showShortToast(this, "积分排行榜")
        }
        imgMenuHeader.setOnClickListener {
            ToastUtil.showShortToast(this, "去登陆")
        }

        var lp: DrawerLayout.LayoutParams = layoutMenu.layoutParams as DrawerLayout.LayoutParams
        lp.width = DisplayUtil.getScreenWidth(this) / 20 * 13
        layoutMenu.requestLayout()

        navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
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
                    R.id.main_5 -> {
                        selectFragment(4)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_main
    }

    private var nowPosition = 0

    private fun selectFragment(index: Int) {
        when (index) {
            0 -> tvTitle.text = resources.getText(R.string.text_main_bottom_home)
            1 -> tvTitle.text = resources.getText(R.string.text_main_bottom_square)
            2 -> tvTitle.text = resources.getText(R.string.text_main_bottom_the_public)
            3 -> tvTitle.text = resources.getText(R.string.text_main_bottom_the_system)
            4 -> tvTitle.text = resources.getText(R.string.text_main_bottom_the_project)
        }
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