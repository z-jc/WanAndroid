package com.android.wan.ui.adapter

import android.app.Activity
import com.android.wan.R
import com.android.wan.model.entity.ToolEntity
import com.android.wan.ui.activity.ContentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * FileName: ToolAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class ToolAdapter : BaseQuickAdapter<ToolEntity.DataBean.DatasBean, BaseViewHolder>(
        R.layout.item_tool,
        null
    ) {

    override fun convert(holder: BaseViewHolder, item: ToolEntity.DataBean.DatasBean) {
        holder.setText(R.id.tv_author_name, item.author)
            .setText(R.id.tv_home_date, item.niceDate)
            .setText(R.id.tv_home_content, item.title)
            .setText(R.id.tv_home_source, item.chapterName)

        holder.itemView.setOnClickListener {
            ContentActivity.startAct(holder.itemView.context as Activity, item.link!!, item.title!!
            )
        }
    }
}