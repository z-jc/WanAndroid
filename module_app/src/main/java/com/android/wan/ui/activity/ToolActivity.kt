package com.android.wan.ui.activity

import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.ToolCancelOutEntity
import com.android.wan.model.entity.ToolEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.ToolAdapter
import com.android.wan.util.RvAnimUtils
import com.dq.ui.base.BaseActivity
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_tool.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * FileName: ToolActivity
 * Author: admin
 * Date: 2020/7/21 13:55
 * Description:
 */
class ToolActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener {

    var toolAdapter: ToolAdapter? = null
    var apiModel: ApiModel? = null
    var pageIndex: Int = 0
    var isRefresh = false//false刷新 true加载

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_tool
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        tvTitle.text = "我的收藏"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
        toolAdapter = ToolAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.setSwipeMenuCreator { _, rightMenu, _ ->
            val deleteItem = SwipeMenuItem(this)
            deleteItem.setBackgroundColor(Color.parseColor("#FF3D39"))
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT).width = 170
            rightMenu.addMenuItem(deleteItem)
        }

        recyclerView.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            cancelTool(
                adapterPosition,
                toolAdapter!!.data[adapterPosition].id,
                toolAdapter!!.data[adapterPosition].originId
            )
        }

        recyclerView.adapter = toolAdapter

        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    /**
     * 获取收藏列表
     */
    private fun getToolList() {
        apiModel!!.userTool(pageIndex, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
                val listEntity: ToolEntity =
                    JsonUtil.fromJson<ToolEntity>(
                        response,
                        ToolEntity()
                    ) as ToolEntity
                if (listEntity.errorCode == 0) {
                    if (isRefresh) {
                        toolAdapter!!.addData((listEntity.data!!.datas as MutableList<ToolEntity.DataBean.DatasBean>?)!!)
                    } else {
                        toolAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<ToolEntity.DataBean.DatasBean>?)!!)
                    }
                } else {
                    ToastUtil.showShortToast(this@ToolActivity, listEntity.errorMsg)
                }
            }

            override fun onFinish() {
                RvAnimUtils.setAnim(toolAdapter!!)
                refreshLayout.finishLoadMore()
                refreshLayout.finishRefresh()
            }

            override fun onError(error: String?) {
                ILog.e("网路异常$error")
                ToastUtil.showShortToast(this@ToolActivity, "网络异常")
            }

            override fun onStart() {

            }
        })
    }

    /**
     * 取消收藏
     */
    private fun cancelTool(pos: Int, id: Int, originId: Int) {
        val map: MutableMap<String, String> = mutableMapOf()
        map["originId"] = originId.toString()
        apiModel!!.cancelToolOut(id, map, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
                val toolCancelOutEntity: ToolCancelOutEntity =
                    JsonUtil.fromJson<ToolCancelOutEntity>(
                        response,
                        ToolCancelOutEntity()
                    ) as ToolCancelOutEntity
                if (toolCancelOutEntity.errorCode == 0) {
                    toolAdapter!!.data.removeAt(pos)
                    toolAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFinish() {
            }

            override fun onError(error: String?) {
                ILog.e("网路异常$error")
                ToastUtil.showShortToast(this@ToolActivity, "网络异常")
            }

            override fun onStart() {
            }
        })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex += 1
        isRefresh = true
        getToolList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        isRefresh = false
        getToolList()
    }
}