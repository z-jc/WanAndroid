package com.android.wan.config

import com.dq.util.SPUtil

/**
 * Author : Z-JC
 * Date : 2020/3/5
 * Description :
 */
class AppConfig {

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