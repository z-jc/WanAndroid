package com.android.wan.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.TextView
import cn.bertsir.zbar.QrConfig
import cn.bertsir.zbar.QrManager
import com.android.wan.R
import com.android.wan.bean.MenuBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.ToastUtil


class MenuAdapter : BaseQuickAdapter<MenuBean, BaseViewHolder>(R.layout.item_main_menu, null) {
    override fun convert(helper: BaseViewHolder, item: MenuBean) {
        val tvItem: TextView = helper.getView(R.id.tv_item)
        val tvContent: TextView = helper.getView(R.id.tv_content)

        val position: Int = helper.layoutPosition
        val context: Context = helper.itemView.context
        val drawableLeft = context.resources.getDrawable(item.menuIcon)
        tvItem.setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            null, null, null
        )
        tvItem.compoundDrawablePadding = 32
        tvItem.text = item.menuText

        if(helper.layoutPosition == 0){
            tvContent.text = "21"
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