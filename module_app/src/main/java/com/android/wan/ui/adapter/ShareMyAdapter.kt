package com.android.wan.ui.adapter

import android.app.Activity
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.wan.R
import com.android.wan.model.entity.ShareMyEntity
import com.android.wan.model.entity.ToolAddInEntity
import com.android.wan.model.entity.ToolCancelInEntity
import com.android.wan.model.http.JsonUtil
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ContentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.ILog
import com.dq.util.http.RxhttpUtil

/**
 * FileName: ShareMyAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class ShareMyAdapter : BaseQuickAdapter<ShareMyEntity.DataBean.ShareArticlesBean.DatasBean, BaseViewHolder>(
        R.layout.item_share_my,
        null
    ) {

    override fun convert(holder: BaseViewHolder, item: ShareMyEntity.DataBean.ShareArticlesBean.DatasBean) {
        holder.setText(R.id.tv_author_name, item.shareUser)
            .setText(R.id.tv_public_date, item.niceDate)
            .setText(R.id.tv_public_content, item.title)
            .setText(R.id.tv_public_source, item.chapterName + " / " + item.superChapterName)

        val imgItem: ImageView = holder.getView(R.id.img_collection)
        if(item.isCollect){
            imgItem.setImageResource(R.drawable.ic_like)
        }else{
            imgItem.setImageResource(R.drawable.ic_like_not)
        }

        imgItem.setOnClickListener {
            if (item.isCollect) {
                cancelCollect(
                    item,
                    holder.layoutPosition,
                    item.id,
                    holder.itemView.context as AppCompatActivity
                )
            } else {
                addCollect(
                    item,
                    holder.layoutPosition,
                    item.id,
                    holder.itemView.context as AppCompatActivity
                )
            }
        }

        holder.itemView.setOnClickListener {
            ContentActivity.startAct(holder.itemView.context as Activity, item.link!!, item.title!!
            )
        }
    }

    private fun addCollect(
        item: ShareMyEntity.DataBean.ShareArticlesBean.DatasBean,
        pos: Int,
        id: Int,
        act: AppCompatActivity
    ) {
        val apiModel: ApiModel = ApiModelImpl()
        apiModel.addToolIn(id, act, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("添加成功:$response")
                val toolAddInEntity: ToolAddInEntity = JsonUtil.fromJson<ToolAddInEntity>(
                    response,
                    ToolAddInEntity()
                ) as ToolAddInEntity
                if (toolAddInEntity.errorCode == 0) {
                    item.isCollect = true
                    notifyItemChanged(pos)
                }
            }

            override fun onFinish() {
            }

            override fun onError(error: String?) {
                ILog.e("添加失败:$error")
            }

            override fun onStart() {
            }
        })
    }

    private fun cancelCollect(
        item: ShareMyEntity.DataBean.ShareArticlesBean.DatasBean,
        pos: Int,
        id: Int,
        act: AppCompatActivity
    ) {
        val apiModel: ApiModel = ApiModelImpl()
        apiModel.cancelToolIn(id, act, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("取消成功:$response")
                val toolCancelInEntity: ToolCancelInEntity = JsonUtil.fromJson<ToolCancelInEntity>(
                    response,
                    ToolCancelInEntity()
                ) as ToolCancelInEntity
                if (toolCancelInEntity.errorCode == 0) {
                    item.isCollect = false
                    notifyItemChanged(pos)
                }
            }

            override fun onFinish() {
            }

            override fun onError(error: String?) {
                ILog.e("取消失败:$error")
            }

            override fun onStart() {
            }
        })
    }
}