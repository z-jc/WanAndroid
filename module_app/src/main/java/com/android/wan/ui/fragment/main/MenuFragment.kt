package com.android.wan.ui.fragment.main

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.SquareListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ShareArticleActivity
import com.android.wan.ui.adapter.MenuAdapter
import com.android.wan.util.RvAnimUtils
import com.dq.login.activity.LoginActivity
import com.dq.login.config.LoginConfig
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.title_bar_base.*

class MenuFragment : BaseFragment(), OnLoadMoreListener, OnRefreshListener {

    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var apiModel: ApiModel? = null
    var mAdapter: MenuAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_menu
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = MenuAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    override fun initData() {
        super.initData()
        imgBack.visibility = View.INVISIBLE
        tvTitle.text = "广场"
        imgTitle.setImageResource(R.drawable.icon_title_add)
        imgTitle.setOnClickListener {
            if (!LoginConfig().getIsLogin()) {
                LoginActivity.start(activity!!)
            } else {
                startAct(activity,ShareArticleActivity())
            }
        }
    }

    private fun getSquareList() {
        activity?.let {
            apiModel!!.getSquareList(pageIndex!!, it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    var listEntity: SquareListEntity =
                        JsonUtil.fromJson<SquareListEntity>(
                            response,
                            SquareListEntity()
                        ) as SquareListEntity
                    if (listEntity.errorCode == 0) {
                        if (isRefresh!!) {
                            mAdapter!!.addData((listEntity.data!!.datas as MutableList<SquareListEntity.DataBean.DatasBean>?)!!)
                        } else {
                            mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<SquareListEntity.DataBean.DatasBean>?)!!)
                        }
                    } else {
                        ToastUtil.showShortToast(activity, listEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    RvAnimUtils.setAnim(mAdapter!!)
                    refreshLayout.finishLoadMore()
                    refreshLayout.finishRefresh()
                }

                override fun onError(error: String?) {
                    ILog.e("请求失败:$error")
                    ToastUtil.showShortToast(activity, "网络异常")
                }

                override fun onStart() {
                }
            })
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getSquareList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        isRefresh = false
        getSquareList()
    }

    companion object {
        fun createFragment(): MenuFragment {
            return MenuFragment()
        }
    }
}