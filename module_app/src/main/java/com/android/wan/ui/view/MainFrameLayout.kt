package com.android.wan.ui.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

class MainFrameLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context!!, attrs, defStyleAttr) {
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        val childCount = childCount
        for (index in 0 until childCount) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            getChildAt(index).dispatchApplyWindowInsets(insets)
        }
        return insets
    }

    init {
        setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(
                parent: View,
                child: View
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    requestApplyInsets()
                }
            }

            override fun onChildViewRemoved(
                parent: View,
                child: View
            ) {
            }
        })
    }
}