package com.android.wan.ui.adapter

import android.app.Activity
import android.widget.ImageView
import com.android.wan.R
import com.android.wan.model.entity.SharePersonEntity
import com.android.wan.ui.activity.ContentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.ToastUtil

/**
 * FileName: SharePersonAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class SharePersonAdapter : BaseQuickAdapter<SharePersonEntity.DataBean.ShareArticlesBean.DatasBean, BaseViewHolder>(
        R.layout.item_share_person,
        null
    ) {

    override fun convert(helper: BaseViewHolder, item: SharePersonEntity.DataBean.ShareArticlesBean.DatasBean) {
        helper.setText(R.id.tv_author_name, item.shareUser)
            .setText(R.id.tv_share_person_date, item.niceDate)
            .setText(R.id.tv_share_person_content, item.title)
            .setText(R.id.tv_share_person_source, item.chapterName + " / " + item.superChapterName)

        var imgItem: ImageView = helper.getView(R.id.img_collection)
        imgItem.setImageResource(R.drawable.ic_like)
        imgItem.setOnClickListener {
            ToastUtil.showShortToast(helper.itemView.context, "收藏")
        }

        helper.itemView.setOnClickListener {
            ContentActivity.startAct(helper.itemView.context as Activity, item.link!!, item.title!!
            )
        }

    }
}