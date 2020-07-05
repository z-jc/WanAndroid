package com.android.wan.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.android.wan.R
import com.android.wan.model.entity.SystemSysEntity
import com.android.wan.ui.adapter.BaseViewPagerAdapter
import com.android.wan.ui.fragment.system.SystemChildFragment
import com.android.wan.ui.view.ViewPagerUtil
import com.dq.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_system.*
import kotlinx.android.synthetic.main.title_bar_base.*


class SystemActivity : BaseActivity() {

    var entity: SystemSysEntity.DataBean? = null
    var position: Int = 0
    val mFragments: MutableList<Fragment> = mutableListOf()
    var titleList: MutableList<String> = mutableListOf()

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_system
    }

    override fun initView() {
        super.initView()
        entity = intent.getParcelableExtra(sysEntity)
        position = intent.getIntExtra(mPosition, 0)
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
        tvTitle.text = entity!!.name
        ViewPagerUtil().setAnim(viewPagerSystem)
        if (entity != null) {
            if(entity!!.children!=null){
                for (item: SystemSysEntity.DataBean.ChildrenBean? in entity!!.children!!) {
                    titleList.add(item!!.name!!)
                    mFragments.add(SystemChildFragment.createFragment(item!!.id))
                }
            }
        }
        var mAdapter = BaseViewPagerAdapter(
            supportFragmentManager,
            mFragments,
            titleList
        )
        viewPagerSystem.adapter = mAdapter
        viewPagerSystem.offscreenPageLimit = mFragments.size
        tabLayout.setViewPager(viewPagerSystem)
        tabLayout.currentTab = position
    }

    companion object {

        var sysEntity = "SYS_ENTITY"
        var mPosition = "POSITION"
        fun startAct(act: Activity, entity: SystemSysEntity.DataBean, pos: Int) {
            var i = Intent(act, SystemActivity::class.java)
            i.putExtra(sysEntity, entity as Parcelable)
            i.putExtra(mPosition, pos)
            act.startActivity(i)
        }
    }
}