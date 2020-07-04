package com.android.wan.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.android.wan.R
import com.android.wan.model.entity.SystemSysEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.ToastUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener

/**
 * FileName: SystemSysAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class SystemSysAdapter :
    BaseQuickAdapter<SystemSysEntity.DataBean, BaseViewHolder>(
        R.layout.item_system_sys,
        null
    ) {

    override fun convert(helper: BaseViewHolder, item: SystemSysEntity.DataBean) {
        helper.setText(R.id.tvItemTitle, item.name)
        var flowlayout: TagFlowLayout = helper.getView(R.id.flowLayoutSys)

        var list: MutableList<String> = mutableListOf()
        for (dataBean: SystemSysEntity.DataBean.ChildrenBean in item!!.children!!) {
            list.add(dataBean!!.name!!)
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
            ToastUtil.showShortToast(helper.itemView.context, list[position])
            false
        })
    }
}