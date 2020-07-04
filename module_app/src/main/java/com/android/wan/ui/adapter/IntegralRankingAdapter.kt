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

    override fun convert(helper: BaseViewHolder, item: IntegralRankingEnity.DataBean.DatasBean) {
        helper.setText(R.id.tvRank, item.rank)
            .setText(R.id.tvAuthor, item.username)
            .setText(R.id.tvIntegral, item.coinCount.toString())

        var view: View = helper.getView(R.id.view)
        var w = data.get(0).coinCount
        var i = item.coinCount
        var b: Double = (i * 1.0) / (w * 1.0)                //积分比例
        var bb = b * 100
        var lp: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams
        lp.width = (DisplayUtil.getScreenWidth(helper.itemView.context) * bb.toInt() / 100)
        view.requestLayout()

        var imgRank = helper.getView<ImageView>(R.id.imgRank)
        var tvRank = helper.getView<TextView>(R.id.tvRank)
        when (helper.layoutPosition) {
            0 -> {
                tvRank.visibility = View.INVISIBLE
                imgRank.visibility = View.VISIBLE
                helper.setImageResource(R.id.imgRank, R.drawable.icon_rank_1)
            }
            1 -> {
                tvRank.visibility = View.INVISIBLE
                imgRank.visibility = View.VISIBLE
                helper.setImageResource(R.id.imgRank, R.drawable.icon_rank_2)
            }
            2 -> {
                tvRank.visibility = View.INVISIBLE
                imgRank.visibility = View.VISIBLE
                helper.setImageResource(R.id.imgRank, R.drawable.icon_rank_3)
            }
        }

        helper.itemView.setOnClickListener {
            SharePersonActivity.start(helper.itemView.context as Activity, item.userId)
        }
    }
}