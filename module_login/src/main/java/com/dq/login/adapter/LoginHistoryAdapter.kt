package com.dq.login.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.login.R
import com.dq.login.model.entity.LoginHistoryEntity
import org.raphets.roundimageview.RoundImageView

class LoginHistoryAdapter(itemClickLister: OnItemClickLister) :
    BaseQuickAdapter<LoginHistoryEntity, BaseViewHolder>(R.layout.item_login_history, null) {

    var itemClick: OnItemClickLister? = null

    init {
        this.itemClick = itemClickLister
    }

    override fun convert(holder: BaseViewHolder, historyEntity: LoginHistoryEntity) {
        var imgHeader: RoundImageView = holder.getView(R.id.imgHeader)
        var tvUser: TextView = holder.getView(R.id.tvUser)
        tvUser.setText(historyEntity.userName)
        Glide.with(holder.itemView.context)
            .load(historyEntity.userPhoto)
            .placeholder(R.drawable.ic_default_avatar)
            .error(R.drawable.ic_default_avatar)
            .into(imgHeader)
        holder.getView<ImageView>(R.id.imgDel).setOnClickListener {
            itemClick?.onDel(holder.layoutPosition, historyEntity)
        }

        holder.itemView.setOnClickListener {
            itemClick?.onItem(holder.layoutPosition, historyEntity)
        }
    }

    interface OnItemClickLister {
        fun onItem(position: Int, entity: LoginHistoryEntity)
        fun onDel(position: Int, entity: LoginHistoryEntity)
    }
}