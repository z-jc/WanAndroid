package com.android.wan.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.wan.R
import com.android.wan.model.entity.IntegralRankingEnity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * FileName: HomeAdapter
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
    }
}