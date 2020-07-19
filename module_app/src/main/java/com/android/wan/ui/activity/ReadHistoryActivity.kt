package com.android.wan.ui.activity

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.ReadHistoryEntity
import com.android.wan.ui.adapter.ReadHistoryAdapter
import com.dq.ui.base.BaseActivity
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_readhistory.*
import kotlinx.android.synthetic.main.title_bar_base.*
import org.litepal.LitePal

class ReadHistoryActivity : BaseActivity() {

    var historyAdapter: ReadHistoryAdapter? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_readhistory
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "阅读历史"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
        var listEntity: MutableList<ReadHistoryEntity> =
            LitePal.order("id desc").find(ReadHistoryEntity::class.java)
        if (listEntity == null || listEntity.size == 0) {
            tvNoData.visibility = View.VISIBLE
            return
        }

        swipeRecyclerView.setSwipeMenuCreator(SwipeMenuCreator { leftMenu, rightMenu, position ->
            val deleteItem = SwipeMenuItem(this)
            deleteItem.setBackgroundColor(Color.parseColor("#FF3D39"))
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT).width = 220
            rightMenu.addMenuItem(deleteItem)
        })

        swipeRecyclerView.setOnItemMenuClickListener(OnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            LitePal.delete(
                ReadHistoryEntity::class.java,
                historyAdapter!!.data.get(adapterPosition).id
            )
            historyAdapter!!.data.removeAt(adapterPosition)
            historyAdapter!!.notifyDataSetChanged()
            if (historyAdapter!!.data == null || historyAdapter!!.data.size == 0) {
                tvNoData.visibility = View.VISIBLE
            }
        })

        historyAdapter = ReadHistoryAdapter()
        swipeRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeRecyclerView.adapter = historyAdapter
        historyAdapter!!.setNewInstance(listEntity)
    }
}