package com.android.wan.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.MyPointsEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.MyPointsAdapter
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.RvAnimUtils
import com.dq.login.config.LoginConfig
import com.dq.ui.base.BaseActivity
import com.dq.ui.dialog.DialogWebView
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_my_points.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.title_bar_base.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * FileName: IntegralRankingActivity
 * Author: admin
 * Date: 2020/7/2 14:08
 * Description:
 */
class MyPointsActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener {

    var rulesUrl = "https://www.wanandroid.com/blog/show/2653"
    var mAdapter: MyPointsAdapter? = null
    var pageIndex: Int? = 1
    var isRefresh: Boolean? = false
    private var apiModel: ApiModel? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_my_points
    }

    override fun initView() {
        super.initView()
        mAdapter = MyPointsAdapter()
        apiModel = ApiModelImpl()
        tvTitle.text = "我的积分"
        tvIntegral.text = LoginConfig().getUserIntegral().toString()
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgTitle.setImageResource(R.drawable.ic_rank_white_24dp)
        imgBack.setOnClickListener { finish() }
        imgTitle.setOnClickListener { startAct(this, IntegralRankingActivity()) }
        imgRules.setOnClickListener {
            showRules()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    private fun showRules() {
        LoadingUtil.showLoading(this,"请稍候...")
        Thread {
            val doc1: Document = Jsoup.connect(rulesUrl).get()
            val element: Elements = doc1.select("div.block") //解析题目截图地址
            runOnUiThread {
                LoadingUtil.dismissLoading()
                var loadData = element.html().replace("<div id=\"blogDetail\"","<div id=\"blogDetail\" style=\"font-size:44px\"")
                DialogWebView(this@MyPointsActivity)
                    .isShowContent(false)
                    .setLoadData(loadData)
                    .show()
            }
        }.start()
    }

    fun getMyPointsList() {
        apiModel!!.getMyPointsList(pageIndex!!, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
                var listEntity: MyPointsEntity =
                    JsonUtil.fromJson<MyPointsEntity>(
                        response,
                        MyPointsEntity()
                    ) as MyPointsEntity
                if (listEntity.errorCode == 0) {
                    if (isRefresh!!) {
                        mAdapter!!.addData((listEntity.data!!.datas as MutableList<MyPointsEntity.DataBean.DatasBean>?)!!)
                    } else {
                        mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<MyPointsEntity.DataBean.DatasBean>?)!!)
                    }
                } else {
                    ToastUtil.showShortToast(this@MyPointsActivity, listEntity.errorMsg)
                }
            }

            override fun onFinish() {
                RvAnimUtils.setAnim(mAdapter!!)
                refreshLayout.finishLoadMore()
                refreshLayout.finishRefresh()
            }

            override fun onError(error: String?) {
                ILog.e("请求失败$error")
                ToastUtil.showShortToast(this@MyPointsActivity, "网络异常")
            }

            override fun onStart() {
                ILog.e("开始请求")
            }
        })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getMyPointsList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 1
        isRefresh = false
        getMyPointsList()
    }

}