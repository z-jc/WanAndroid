package com.android.demo.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.demo.R
import com.android.demo.model.entity.MeBean
import com.android.demo.ui.adapter.MeAdapter
import com.android.demo.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.title_bar_base.*
import java.util.*

class MeFragment : BaseFragment() {
    
    private var meAdapter: MeAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_me
    }

    override fun initView() {
        tvTitle.text = "我的"
        imgBack.visibility = View.GONE
        imgTitle.visibility = View.GONE
        meAdapter = MeAdapter()
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.layout_me_header, null)
        meAdapter!!.addHeaderView(view)
        val layoutManager: LinearLayoutManager = object : LinearLayoutManager(activity) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = meAdapter
        meAdapter!!.setList(meList)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        setTitleBackground(BG_WHITE)
    }

    companion object {
        fun createFramgemt(): MeFragment {
            return MeFragment()
        }

        /**
         * 我的界面数据源
         *
         * @return
         */
        val meList: List<MeBean>
            get() {
                val list: MutableList<MeBean> = ArrayList<MeBean>()
                list.add(MeBean(R.drawable.icon_main_tab_me_true, "清理缓存", "0.00k"))
                list.add(MeBean(R.drawable.icon_main_tab_me_true, "隐私政策", null.toString()))
                list.add(MeBean(R.drawable.icon_main_tab_me_true, "用户协议", null.toString()))
                list.add(MeBean(R.drawable.icon_main_tab_me_true, "版本信息", "V3.0"))
                list.add(MeBean(R.drawable.icon_main_tab_me_true, "关于我们", null.toString()))
                list.add(MeBean(R.drawable.icon_main_tab_me_true, "用户反馈", null.toString()))
                return list
            }
    }
}