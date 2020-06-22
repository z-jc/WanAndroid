package com.android.wan.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author Administrator
 * @particulars
 * @time 2019\10\24 0024 9:56
 * @class describe
 */
abstract class BaseFragment : SupportFragment() {
    var activity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity()
    }

    /**
     * Activity跳转
     *
     * @param startAct
     * @param endAct
     * @return
     */
    fun startAct(startAct: Activity?, endAct: Activity) {
        startActivity(Intent(startAct, endAct.javaClass))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return this.getContentView()?.let { inflater.inflate(it, null) }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
        initData()
    }

    protected abstract fun getContentView(): Int?

    open fun setTitleBackground(back: Int?) {
        if (back == 1) {//深色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        if (back == 0) {//浅色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }

    companion object {
        const val BG_WHITE = 0
        const val BG_BLACK = 1
    }

    open fun initData() {}

    open fun initView() {}
}