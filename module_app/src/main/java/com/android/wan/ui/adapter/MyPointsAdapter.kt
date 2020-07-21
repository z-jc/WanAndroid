package com.android.wan.ui.adapter

import com.android.wan.R
import com.android.wan.model.entity.MyPointsEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.DateUtil

/**
 * FileName: MyPointsAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class MyPointsAdapter :
    BaseQuickAdapter<MyPointsEntity.DataBean.DatasBean, BaseViewHolder>(
        R.layout.item_my_points,
        null
    ) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun convert(holder: BaseViewHolder, item: MyPointsEntity.DataBean.DatasBean) {
        holder.setText(R.id.tvTitle, item.reason)
            .setText(R.id.tvDate, DateUtil.getDate(DateUtil.ymdhms, item.date))
            .setText(R.id.tvIntegral, "+" + item.coinCount)
    }
}