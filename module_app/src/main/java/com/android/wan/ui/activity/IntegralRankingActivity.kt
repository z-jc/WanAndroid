package com.android.wan.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.HomeListEntity
import com.android.wan.model.entity.IntegralRankingEnity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.IntegralRankingAdapter
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
import kotlinx.android.synthetic.main.activity_integral_ranking.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * FileName: IntegralRankingActivity
 * Author: admin
 * Date: 2020/7/2 14:08
 * Description:
 */
class IntegralRankingActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener {

    var mAdapter: IntegralRankingAdapter? = null
    var pageIndex: Int? = 1
    var isRefresh: Boolean? = false
    private var apiModel: ApiModel? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_integral_ranking
    }

    override fun initView() {
        super.initView()
        mAdapter = IntegralRankingAdapter()
        apiModel = ApiModelImpl()
        tvTitle.text = "积分排行榜"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    fun getIntegralRankingList() {
        apiModel!!.getIntegralRankingList(pageIndex!!, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
                var listEntity: IntegralRankingEnity =
                    JsonUtil.fromJson<IntegralRankingEnity>(
                        response,
                        IntegralRankingEnity()
                    ) as IntegralRankingEnity
                if (listEntity.errorCode == 0) {
                    if (isRefresh!!) {
                        mAdapter!!.addData((listEntity.data!!.datas as MutableList<IntegralRankingEnity.DataBean.DatasBean>?)!!)
                    } else {
                        mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<IntegralRankingEnity.DataBean.DatasBean>?)!!)
                    }
                } else {
                    ToastUtil.showShortToast(this@IntegralRankingActivity, listEntity.errorMsg)
                }
            }

            override fun onFinish() {
                RvAnimUtils.setAnim(mAdapter!!)
                refreshLayout.finishLoadMore()
                refreshLayout.finishRefresh()
            }

            override fun onError(error: String?) {
                ILog.e("请求失败$error")
                ToastUtil.showShortToast(this@IntegralRankingActivity, "网络异常")
            }

            override fun onStart() {
                ILog.e("开始请求")
            }

        })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getIntegralRankingList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 1
        isRefresh = false
        getIntegralRankingList()
    }

}