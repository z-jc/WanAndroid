package com.android.setting.config

import com.dq.util.SPUtil

object SettingConfig {


    /**
     * 获取上拉加载动画文字
     */
    fun getFootView(): String {
        return SPUtil.getString("foot_view")
    }

    /**
     * 保存上拉加载动画文字
     */
    fun setFootView(value: String) {
        SPUtil.setString("foot_view", value)
    }

    /**
     * 保存列表动画类型
     */
    fun setAdapterAnim(value: Int) {
        SPUtil.setInteger("adapter_list", value)
    }

    /**
     * 获取动画类型
     */
    fun getAdapterAnim(): Int {
        return SPUtil.getInteger("adapter_list")
    }
}