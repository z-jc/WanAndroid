package com.android.wan.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class BaseViewPager : ViewPager {
    constructor(context: Context) : super(context) {}
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    override fun canScroll(
        v: View,
        checkV: Boolean,
        dx: Int,
        x: Int,
        y: Int
    ): Boolean {
        if (v !== this && v is ViewPager) {
            val currentItem = v.currentItem
            val countItem = v.adapter!!.count
            return if (currentItem == countItem - 1 && dx < 0 || currentItem == 0 && dx > 0) {
                false
            } else true
        }
        return super.canScroll(v, checkV, dx, x, y)
    }
}