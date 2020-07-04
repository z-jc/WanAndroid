package com.android.wan.ui.adapter

import android.widget.ImageView
import com.android.wan.R
import com.android.wan.model.entity.ProjectListEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.ToastUtil

/**
 * FileName: ProjectChildAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class ProjectChildAdapter :
    BaseQuickAdapter<ProjectListEntity.DataBean.DatasBean, BaseViewHolder>(
        R.layout.item_project_child_list,
        null
    ) {

    override fun convert(helper: BaseViewHolder, item: ProjectListEntity.DataBean.DatasBean) {
        helper.setText(R.id.tv_author_name, item.shareUser)
            .setText(R.id.tv_public_date, item.niceDate)
            .setText(R.id.tv_public_content, item.title)
            .setText(R.id.tv_public_source, item.chapterName + " / " + item.superChapterName)

        var imgItem: ImageView = helper.getView(R.id.img_collection)
        imgItem.setImageResource(R.drawable.ic_like)
        imgItem.setOnClickListener {
            ToastUtil.showShortToast(helper.itemView.context, "收藏")
        }
    }
}