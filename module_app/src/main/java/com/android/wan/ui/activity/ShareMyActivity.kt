package com.android.wan.ui.activity

import android.app.Dialog
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.DeleteShareMyEntity
import com.android.wan.model.entity.ShareMyEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.ShareMyAdapter
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.RvAnimUtils
import com.dq.login.config.LoginConfig
import com.dq.ui.base.BaseActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_share_my.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * FileName: ShareMyActivity
 * Author: admin
 * Date: 2020/7/2 14:08
 * Description:
 */
class ShareMyActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener {

    var mAdapter: ShareMyAdapter? = null
    var pageIndex = 1
    var isRefresh: Boolean? = false
    private var apiModel: ApiModel? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_share_my
    }

    override fun initView() {
        super.initView()
        mAdapter = ShareMyAdapter()
        apiModel = ApiModelImpl()
        tvTitle.text = "我的分享"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
        mAdapter!!.setOnItemLongClickListener { adapter, view, position ->
            DialogCustom(this@ShareMyActivity)
                .setMsg("是否删除?")
                .setLeft("否")
                .setRight("是")
                .setActionLister(object : DialogCustom.ActionLister {
                    override fun onLeftClick(dialog: Dialog?) {
                        dialog!!.dismiss()
                    }

                    override fun onRightClick(dialog: Dialog?) {
                        dialog!!.dismiss()
                        deleteItem(mAdapter!!.data.get(position).id, position)
                    }
                }).show()
            false
        }
    }

    fun getShareMyList() {
        apiModel!!.getShareMyList(
            pageIndex,
            this@ShareMyActivity,
            object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("请求成功:$response")
                    var listEntity: ShareMyEntity =
                        JsonUtil.fromJson<ShareMyEntity>(
                            response,
                            ShareMyEntity()
                        ) as ShareMyEntity
                    if (listEntity.errorCode == 0) {
                        LoginConfig().setUserLevel(listEntity.data!!.coinInfo!!.level)
                        LoginConfig().setUserRank(listEntity.data!!.coinInfo!!.rank!!)
                        LoginConfig().setUserIntegral(listEntity.data!!.coinInfo!!.coinCount!!)
                        if (listEntity.data!!.shareArticles!!.datas == null || listEntity.data!!.shareArticles!!.datas!!.size == 0) {
                            tvNoData.visibility = View.VISIBLE
                            return
                        }
                        if (isRefresh!!) {
                            mAdapter!!.addData((listEntity.data!!.shareArticles!!.datas as MutableList<ShareMyEntity.DataBean.ShareArticlesBean.DatasBean>?)!!)
                        } else {
                            mAdapter!!.setNewInstance((listEntity.data!!.shareArticles!!.datas as MutableList<ShareMyEntity.DataBean.ShareArticlesBean.DatasBean>?)!!)
                        }
                    } else {
                        ToastUtil.showShortToast(this@ShareMyActivity, listEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    RvAnimUtils.setAnim(mAdapter!!)
                    refreshLayout.finishRefresh()
                    refreshLayout.finishLoadMore()
                }

                override fun onError(error: String?) {
                    ILog.e("请求失败:$error")
                    ToastUtil.showShortToast(this@ShareMyActivity, "网络异常")
                }

                override fun onStart() {
                    tvNoData.visibility = View.GONE
                }
            })
    }

    fun deleteItem(articleId: Int, position: Int) {
        apiModel!!.postDeleteShareMyPosition(
            articleId,
            this@ShareMyActivity,
            object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("请求成功:$response")
                    var listEntity: DeleteShareMyEntity =
                        JsonUtil.fromJson<DeleteShareMyEntity>(
                            response,
                            DeleteShareMyEntity()
                        ) as DeleteShareMyEntity
                    if (listEntity.errorCode == 0) {
                        ToastUtil.showShortToast(this@ShareMyActivity, "删除成功")
                        mAdapter!!.remove(position)
                        if (mAdapter!!.itemCount == 0) {
                            tvNoData.visibility = View.VISIBLE
                        }
                    } else {
                        ToastUtil.showShortToast(this@ShareMyActivity, listEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    LoadingUtil.dismissLoading()
                }

                override fun onError(error: String?) {
                    ILog.e("请求异常:$error")
                    ToastUtil.showShortToast(this@ShareMyActivity, "网络异常")
                }

                override fun onStart() {
                    LoadingUtil.showLoading(this@ShareMyActivity, "正在删除...")
                }
            })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getShareMyList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 1
        isRefresh = false
        getShareMyList()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingUtil.dismissLoading()
    }
}