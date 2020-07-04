package com.android.wan.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.SharePersonEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.SharePersonAdapter
import com.android.wan.ui.view.LoadingUtil
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
import kotlinx.android.synthetic.main.activity_share_person.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * FileName: SharePersonActivity
 * Author: admin
 * Date: 2020/7/2 14:08
 * Description:
 */
class SharePersonActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener {

    var mAdapter: SharePersonAdapter? = null
    var pageIndex = 1
    var isRefresh: Boolean? = false
    var userId = -1
    private var apiModel: ApiModel? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_share_person
    }

    override fun initView() {
        super.initView()
        userId = intent.getIntExtra(USER_ID, -1)
        mAdapter = SharePersonAdapter()
        apiModel = ApiModelImpl()
        tvTitle.visibility = View.INVISIBLE
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    fun getSharePersonList() {
        apiModel!!.getSharePerson(
            userId,
            pageIndex,
            this@SharePersonActivity,
            object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("请求成功:$response")
                    var listEntity: SharePersonEntity =
                        JsonUtil.fromJson<SharePersonEntity>(
                            response,
                            SharePersonEntity()
                        ) as SharePersonEntity
                    if (listEntity.errorCode == 0) {
                        tvRank.text =
                            "ID:" + listEntity.data!!.coinInfo!!.userId + "  等级:" + listEntity.data!!.coinInfo!!.level + "  排名：" + listEntity.data!!.coinInfo!!.rank
                        tvUser.text = listEntity.data!!.coinInfo!!.username!!
                        if (isRefresh!!) {
                            mAdapter!!.addData((listEntity.data!!.shareArticles!!.datas as MutableList<SharePersonEntity.DataBean.ShareArticlesBean.DatasBean>?)!!)
                        } else {
                            mAdapter!!.setNewInstance((listEntity.data!!.shareArticles!!.datas as MutableList<SharePersonEntity.DataBean.ShareArticlesBean.DatasBean>?)!!)
                        }
                    } else {
                        ToastUtil.showShortToast(this@SharePersonActivity, listEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    RvAnimUtils.setAnim(mAdapter!!)
                    refreshLayout.finishRefresh()
                    refreshLayout.finishLoadMore()
                }

                override fun onError(error: String?) {
                    ILog.e("请求失败:$error")
                    ToastUtil.showShortToast(this@SharePersonActivity, "网络异常")
                }

                override fun onStart() {
                }
            })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getSharePersonList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 1
        isRefresh = false
        getSharePersonList()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingUtil.dismissLoading()
    }

    companion object {
        var USER_ID = "userId"
        fun start(a: Activity, userId: Int) {
            var i: Intent = Intent(a, SharePersonActivity::class.java)
            i.putExtra(USER_ID, userId)
            a.startActivity(i)
        }
    }
}