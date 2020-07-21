package com.android.wan.ui.adapter

import android.widget.ImageView
import com.android.wan.R
import com.android.wan.model.entity.SearchHistoryEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * FileName: SearchHistoryAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class SearchHistoryAdapter(itemClickLister: OnItemClickLister) : BaseQuickAdapter<SearchHistoryEntity, BaseViewHolder>(
    R.layout.item_search_history,
    null
) {
    private var itemClick: OnItemClickLister? = null

    init {
        this.itemClick = itemClickLister
    }

    override fun convert(holder: BaseViewHolder, item: SearchHistoryEntity) {
        holder.setText(R.id.tvQueryName, item.query)
        holder.getView<ImageView>(R.id.imgDel).setOnClickListener {
            itemClick?.onDel(holder.layoutPosition, item)
        }

        holder.itemView.setOnClickListener {
            itemClick?.onItem(holder.layoutPosition, item)
        }
    }

    interface OnItemClickLister {
        fun onItem(position: Int, entity: SearchHistoryEntity)
        fun onDel(position: Int, entity: SearchHistoryEntity)
    }
}