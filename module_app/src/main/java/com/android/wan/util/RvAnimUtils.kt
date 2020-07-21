package com.android.wan.util

import com.android.setting.config.SettingConfig
import com.chad.library.adapter.base.BaseQuickAdapter

object RvAnimUtils {
    fun getName(anim: Int): String {
        return when (anim) {
            RvAnim.NONE -> "无"
            RvAnim.ALPHAIN -> "渐显"
            RvAnim.SCALEIN -> "缩放"
            RvAnim.SLIDEIN_BOTTOM -> "底部滑入"
            RvAnim.SLIDEIN_LEFT -> "左侧滑入"
            RvAnim.SLIDEIN_RIGHT -> "右侧滑入"
            else -> {
                "无"
            }
        }
    }

    fun setAnim(adapter: BaseQuickAdapter<*, *>) {
        when (SettingConfig.getAdapterAnim()) {
            RvAnim.NONE -> adapter.animationEnable = false
            RvAnim.ALPHAIN -> adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn)
            RvAnim.SCALEIN -> adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
            RvAnim.SLIDEIN_BOTTOM -> adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
            RvAnim.SLIDEIN_LEFT -> adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft)
            RvAnim.SLIDEIN_RIGHT -> adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight)
            else -> {
                adapter.animationEnable = false
            }
        }
    }

    object RvAnim {
        const val NONE = 0
        const val ALPHAIN = 1
        const val SCALEIN = 2
        const val SLIDEIN_BOTTOM = 3
        const val SLIDEIN_LEFT = 4
        const val SLIDEIN_RIGHT = 5
    }
}