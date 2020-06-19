package com.android.demo.ui.fragment

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.demo.R
import com.android.demo.ui.adapter.HomeAdapter
import com.android.demo.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.title_bar_base.*

class HomeFragment : BaseFragment() {

    var mAdapter : HomeAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        imgBack.visibility = View.GONE
        tvTitle.text = "首页"
        mAdapter = HomeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = mAdapter
        mAdapter!!.setList(getList())
    }

    fun getList(): ArrayList<String> {
        var list = arrayListOf<String>()
        var index: Int = 0
        while (index < 200) {
            index++
            list.add("第" + index.toString() + "行")
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        setTitleBackground(BG_WHITE)
    }

    companion object {
        fun createFragment(): HomeFragment {
            return HomeFragment()
        }
    }
}