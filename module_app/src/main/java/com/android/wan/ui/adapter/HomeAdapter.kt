package com.android.wan.ui.adapter

import com.android.wan.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * FileName: HomeAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class HomeAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home,null) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_item, item)
    }
}