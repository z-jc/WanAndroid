package com.android.wan.ui.adapter

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.wan.R
import com.android.wan.model.entity.IntegralRankingEnity
import com.android.wan.ui.activity.SharePersonActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.util.DisplayUtil

/**
 * FileName: IntegralRankingAdapter
 * Author: admin
 * Date: 2020/6/19 17:30
 * Description:
 */
class IntegralRankingAdapter :
    BaseQuickAdapter<IntegralRankingEnity.DataBean.DatasBean, BaseViewHolder>(
        R.layout.item_integral_ranking,
        null
    ) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun convert(holder: BaseViewHolder, item: IntegralRankingEnity.DataBean.DatasBean) {
        holder.setText(R.id.tvRank, item.rank)
            .setText(R.id.tvAuthor, item.username)
            .setText(R.id.tvIntegral, item.coinCount.toString())

        val view: View = holder.getView(R.id.view)
        val w = data.get(0).coinCount
        val i = item.coinCount
        val b: Double = (i * 1.0) / (w * 1.0)                //积分比例
        val bb = b * 100
        val lp: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams
        lp.width = (DisplayUtil.getScreenWidth(holder.itemView.context) * bb.toInt() / 100)
        view.requestLayout()

        val imgRank = holder.getView<ImageView>(R.id.imgRank)
        val tvRank = holder.getView<TextView>(R.id.tvRank)
        when (holder.layoutPosition) {
            0 -> {
                tvRank.visibility = View.INVISIBLE
                imgRank.visibility = View.VISIBLE
                holder.setImageResource(R.id.imgRank, R.drawable.icon_rank_1)
            }
            1 -> {
                tvRank.visibility = View.INVISIBLE
                imgRank.visibility = View.VISIBLE
                holder.setImageResource(R.id.imgRank, R.drawable.icon_rank_2)
            }
            2 -> {
                tvRank.visibility = View.INVISIBLE
                imgRank.visibility = View.VISIBLE
                holder.setImageResource(R.id.imgRank, R.drawable.icon_rank_3)
            }
        }

        holder.itemView.setOnClickListener {
            SharePersonActivity.start(holder.itemView.context as Activity, item.userId)
        }
    }
}