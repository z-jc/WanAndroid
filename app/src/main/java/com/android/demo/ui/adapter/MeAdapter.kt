package com.android.demo.ui.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.android.demo.R
import com.android.demo.model.entity.MeBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.mine.activity.AboutActivity
import com.dq.mine.activity.FeedBackActivity
import com.dq.mine.activity.UserServiceAgreementActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.util.CleanDataUtils
import com.dq.util.SystemUtil
import com.dq.util.ToastUtil

class MeAdapter : BaseQuickAdapter<MeBean, BaseViewHolder>(R.layout.item_me, null) {

    override fun convert(helper: BaseViewHolder, item: MeBean) {
        val tvItem = helper.getView<TextView>(R.id.tv_item)
        val tvContent = helper.getView<TextView>(R.id.tv_content)
        val position = helper.layoutPosition
        val context = helper.itemView.context
        val drawableLeft = context.resources.getDrawable(item.itemIcon)
        tvItem.setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            null, null, null
        )
        tvItem.compoundDrawablePadding = 4
        tvItem.setText(item.itemName)
        tvContent.visibility = View.VISIBLE
        if (position == 1) { //清理缓存
            try {
                tvContent.text = CleanDataUtils.getTotalCacheSize(context)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (position == 4) { //版本信息
            tvContent.text = "V" + SystemUtil.getVersionName(context)
        } else {
            tvContent.visibility = View.GONE
        }
        helper.itemView.setOnClickListener {
            when (position) {
                1 -> DialogCustom(context as Activity)
                    .setMsg("确认清理吗？")
                    .setActionLister(object : DialogCustom.ActionLister {
                        override fun onLeftClick(dialog: Dialog?) {
                            dialog!!.dismiss()
                        }

                        override fun onRightClick(dialog: Dialog?) {
                            dialog!!.dismiss()
                            CleanDataUtils.clearAllCache(context) //清理缓存
                            notifyItemChanged(position)
                        }
                    }).show()
                2 -> UserServiceAgreementActivity.startAct((context as Activity), 1)
                3 -> UserServiceAgreementActivity.startAct((context as Activity), 0)
                4 -> ToastUtil.showShortToast(context, "当前已是最新版本")
                5 -> AboutActivity.startAct(
                    (context as Activity),
                    SystemUtil.getVersionName(context),
                    "Demo",
                    R.mipmap.icon_logo
                )
                6 -> startAct(context as Activity, FeedBackActivity())
            }
        }
    }

    /**
     * Activity跳转
     *
     * @param startAct
     * @param endAct
     * @return
     */
    fun startAct(startAct: Activity, endAct: Activity) {
        val intent = Intent(startAct, endAct.javaClass)
        startAct.startActivity(intent)
    }
}