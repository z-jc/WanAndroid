package com.android.wan.ui.adapter

import android.app.Activity
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.wan.R
import com.android.wan.model.entity.SquareListEntity
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
 * FileName: MenuAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class MenuAdapter :
    BaseQuickAdapter<SquareListEntity.DataBean.DatasBean, BaseViewHolder>(
        R.layout.item_menu_list,
        null
    ) {

    override fun convert(helper: BaseViewHolder, item: SquareListEntity.DataBean.DatasBean) {
        helper.setText(R.id.tv_author_name, item.shareUser)
            .setText(R.id.tv_menu_date, item.niceDate)
            .setText(R.id.tv_menu_content, item.title)
            .setText(R.id.tv_menu_source, item.chapterName + " / " + item.superChapterName)

        var imgItem: ImageView = helper.getView(R.id.img_collection)
        if(item.isCollect){
            imgItem.setImageResource(R.drawable.ic_like)
        }else{
            imgItem.setImageResource(R.drawable.ic_like_not)
        }

        imgItem.setOnClickListener {
            if (item.isCollect) {
                cancelCollect(
                    item,
                    helper.layoutPosition,
                    item.id,
                    helper.itemView.context as AppCompatActivity
                )
            } else {
                addCollect(
                    item,
                    helper.layoutPosition,
                    item.id,
                    helper.itemView.context as AppCompatActivity
                )
            }
        }

        helper.itemView.setOnClickListener {
            ContentActivity.startAct(helper.itemView.context as Activity, item.link!!, item.title!!
            )
        }

    }

    private fun addCollect(
        item: SquareListEntity.DataBean.DatasBean,
        pos: Int,
        id: Int,
        act: AppCompatActivity
    ) {
        var apiModel: ApiModel = ApiModelImpl()
        apiModel.addToolIn(id, act, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("添加成功:$response")
                var toolAddInEntity: ToolAddInEntity = JsonUtil.fromJson<ToolAddInEntity>(
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
        item: SquareListEntity.DataBean.DatasBean,
        pos: Int,
        id: Int,
        act: AppCompatActivity
    ) {
        var apiModel: ApiModel = ApiModelImpl()
        apiModel.cancelToolIn(id, act, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("取消成功:$response")
                var toolCancelInEntity: ToolCancelInEntity = JsonUtil.fromJson<ToolCancelInEntity>(
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