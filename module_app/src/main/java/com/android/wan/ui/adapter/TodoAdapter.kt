package com.android.wan.ui.adapter

import com.android.wan.R
import com.android.wan.model.entity.TodoListEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * FileName: TodoAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class TodoAdapter : BaseQuickAdapter<TodoListEntity.DataBean.DatasBean, BaseViewHolder>(
        R.layout.item_todo,
        null
    ) {

    override fun convert(helper: BaseViewHolder, item: TodoListEntity.DataBean.DatasBean) {
        helper.setText(R.id.tvTodoTitle, item.title)
            .setText(R.id.tvTodoDate, item.dateStr)
            .setText(R.id.tvTodoDetails, item.content)
    }
}