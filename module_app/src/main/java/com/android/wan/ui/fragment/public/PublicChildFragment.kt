package com.android.wan.ui.fragment.public

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.PublicListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ContentActivity
import com.android.wan.ui.adapter.PublicChildAdapter
import com.android.wan.util.RvAnimUtils
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_public_child.*

class PublicChildFragment : BaseFragment(), OnLoadMoreListener, OnRefreshListener {

    //文章类型
    var parentChapterId: Int = 0
    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var apiModel: ApiModel? = null
    var mAdapter: PublicChildAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_public_child
    }

    companion object {
        fun createFragment(parentChapterId: Int): PublicChildFragment {
            val fragment = PublicChildFragment()
            val args = Bundle()
            args.putInt("parentChapterId", parentChapterId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = PublicChildAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
        mAdapter!!.setOnItemClickListener { _, _, position ->
            PublicFragment.isIntercept = false
            ContentActivity.startAct(
                activity!!,
                mAdapter!!.data[position].link!!,
                mAdapter!!.data[position].title!!
            )
        }
    }

    fun getPublicList() {
        activity?.let {
            pageIndex?.let { it1 ->
                apiModel!!.getPublicList(parentChapterId.toString(), it1,
                    it, object : RxhttpUtil.RxHttpCallBack {
                        override fun onSuccess(response: String?) {
                            ILog.e("获取成功$response")
                            val listEntity: PublicListEntity =
                                JsonUtil.fromJson<PublicListEntity>(
                                    response,
                                    PublicListEntity()
                                ) as PublicListEntity
                            if (listEntity.errorCode == 0) {
                                if (isRefresh!!) {
                                    mAdapter!!.addData((listEntity.data!!.datas as MutableList<PublicListEntity.DataBean.DatasBean>?)!!)
                                } else {
                                    mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<PublicListEntity.DataBean.DatasBean>?)!!)
                                }
                            } else {
                                ToastUtil.showShortToast(activity, listEntity.errorMsg)
                            }
                        }

                        override fun onFinish() {
                            ILog.e("请求结束")
                            RvAnimUtils.setAnim(mAdapter!!)
                            refreshLayout.finishLoadMore()
                            refreshLayout.finishRefresh()
                        }

                        override fun onError(error: String?) {
                            ILog.e("请求失败$error")
                            ToastUtil.showShortToast(activity, "网络异常")
                        }

                        override fun onStart() {
                            ILog.e("开始请求")
                        }
                    })
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getPublicList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        isRefresh = false
        getPublicList()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (arguments != null) {
            parentChapterId = arguments!!.getInt("parentChapterId")
        }
    }
}