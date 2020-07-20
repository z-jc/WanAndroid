package com.android.setting.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.android.setting.R
import com.android.setting.adapter.SettingAdapter
import com.android.setting.entity.SettingEntity
import com.dq.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * FileName: SettingActivity
 * Author: admin
 * Date: 2020/7/20 16:33
 * Description:
 */
class SettingActivity : BaseActivity() {

    var settingAdapter: SettingAdapter? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_setting
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "系统设置"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
        settingAdapter = SettingAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = settingAdapter
        settingAdapter!!.setList(getList())
    }

    private fun getList(): MutableList<SettingEntity> {
        var list: MutableList<SettingEntity> = mutableListOf()
        list.add(SettingEntity("列表动画", ""))
        list.add(SettingEntity("上拉加载loading文字", ""))
        list.add(SettingEntity("清除缓存", ""))
        list.add(SettingEntity("版本更新", ""))
        list.add(SettingEntity("意见反馈", ""))
        list.add(SettingEntity("关于我们", ""))
        list.add(SettingEntity("隐私政策", ""))
        list.add(SettingEntity("用户协议", ""))
        return list
    }

}