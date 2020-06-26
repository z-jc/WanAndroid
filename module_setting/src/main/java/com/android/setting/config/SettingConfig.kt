package com.android.setting.config

import com.dq.util.SPUtil

object SettingConfig {

    /**
     * 获取上拉加载动画文字
     */
    /**
     * 保存上拉加载动画文字
     *
     * @param value
     */
    var footView: String?
        get() = SPUtil.getString("foot_view")
        set(value) {
            SPUtil.setString("foot_view", value)
        }
}