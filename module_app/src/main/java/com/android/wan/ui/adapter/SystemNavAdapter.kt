package com.android.wan.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.android.wan.R
import com.android.wan.model.entity.SystemNavEntity
import com.android.wan.ui.activity.ContentActivity
import com.android.wan.ui.fragment.system.SystemFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener

/**
 * FileName: HomeAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class SystemNavAdapter :
    BaseQuickAdapter<SystemNavEntity.DataBean, BaseViewHolder>(
        R.layout.item_system_nav,
        null
    ) {

    override fun convert(helper: BaseViewHolder, item: SystemNavEntity.DataBean) {
        helper.setText(R.id.tvItemTitle, item.name)
        var flowlayout: TagFlowLayout = helper.getView(R.id.flowLayoutNav)

        var list: MutableList<String> = mutableListOf()
        for (dataBean: SystemNavEntity.DataBean.ArticlesBean in item!!.articles!!) {
            list.add(dataBean!!.title!!)
        }

        flowlayout.adapter = object : TagAdapter<String?>(list as List<String>) {
            override fun getView(parent: FlowLayout?, position: Int, s: String?): View? {
                val tvItem: TextView = LayoutInflater.from(helper.itemView.context).inflate(
                    R.layout.item_flowlayout,
                    flowlayout, false
                ) as TextView
                tvItem.text = s
                return tvItem
            }
        }

        flowlayout.setOnTagClickListener(OnTagClickListener { _, position, _ ->
            SystemFragment.isIntercept = false
            ContentActivity.startAct(helper.itemView.context as Activity,
                item.articles!![position]!!.link!!, item.articles!![position]!!.title!!)
            false
        })
    }
}