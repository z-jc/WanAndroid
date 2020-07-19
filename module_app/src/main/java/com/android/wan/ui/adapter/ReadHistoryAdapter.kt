package com.android.wan.ui.adapter

import android.app.Activity
import com.android.wan.R
import com.android.wan.model.entity.ReadHistoryEntity
import com.android.wan.ui.activity.ContentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * FileName: ReadHistoryAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class ReadHistoryAdapter : BaseQuickAdapter<ReadHistoryEntity, BaseViewHolder>(
    R.layout.item_readhistory,
    null
) {

    override fun convert(helper: BaseViewHolder, item: ReadHistoryEntity) {
        helper.setText(R.id.tvTitle, item.title)
            .setText(R.id.tvLink, item.link)

        helper.itemView.setOnClickListener {
            ContentActivity.startAct(
                helper.itemView.context as Activity, item.link!!, item.title!!
            )
        }
    }
}