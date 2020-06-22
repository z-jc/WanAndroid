package com.android.wan.ui.fragment

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.ui.adapter.HomeAdapter
import com.android.wan.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    var mAdapter : HomeAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
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

    companion object {
        fun createFragment(): HomeFragment {
            return HomeFragment()
        }
    }
}