package com.android.wan.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.HomeListEntity
import com.android.wan.model.entity.SquareListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.HomeAdapter
import com.android.wan.ui.adapter.SquareAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_square.*
import kotlinx.android.synthetic.main.fragment_square.recyclerView
import kotlinx.android.synthetic.main.fragment_square.refreshLayout

class SquareFragment : BaseFragment(), OnLoadMoreListener, OnRefreshListener {

    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var apiModel: ApiModel? = null
    var mAdapter: SquareAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_square
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = SquareAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    override fun initData() {
        super.initData()
        imgFabAdd.setOnClickListener {
            ToastUtil.showLongToast(activity,"去添加")
        }
    }

    private fun getSquareList() {
        activity?.let {
            apiModel!!.getSquareList(pageIndex!!, it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("请求成功$response")
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
                    mAdapter!!.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
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
        fun createFragment(): SquareFragment {
            return SquareFragment()
        }
    }
}