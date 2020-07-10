package com.android.wan.ui.view

import android.view.View
import androidx.viewpager.widget.ViewPager

class ViewPagerUtil {
    fun setAnim(vp: ViewPager) {
        vp.setPageTransformer(false, object : ViewPager.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                var normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setAlpha(normalizedposition);//渐变透明
                //渐变大小
                //page.setScaleX(normalizedposition / 2 + 0.5f);
                //page.setScaleY(normalizedposition / 2 + 0.5f);
                //page.setRotationY(position * -30);//渐入渐出
            }
        })
    }
}