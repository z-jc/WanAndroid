package com.android.wan.ui.fragment.system

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.SystemActEntity
import com.android.wan.model.http.JsonUtil
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ContentActivity
import com.android.wan.ui.adapter.SystemActAdapter
import com.android.wan.util.RvAnimUtils
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_system_act.*

class SystemChildFragment : BaseFragment() , OnLoadMoreListener, OnRefreshListener {

    var apiModel: ApiModel? = null
    private var cid: Int = 0
    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var mAdapter: SystemActAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_system_act
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = SystemActAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
        mAdapter!!.setOnItemClickListener { _, _, position -> ContentActivity.startAct(activity!!, mAdapter!!.data.get(position).link!!, mAdapter!!.data.get(position).title!!) }
    }

    fun getList() {
        apiModel!!.getSystemActUrl(pageIndex!!,cid,activity as AppCompatActivity,
            object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("请求成功:$response")
                    val listEntity: SystemActEntity =
                        JsonUtil.fromJson<SystemActEntity>(
                            response,
                            SystemActEntity()
                        ) as SystemActEntity
                    if (listEntity.errorCode == 0) {
                        if (isRefresh!!) {
                            mAdapter!!.addData((listEntity.data!!.datas as MutableList<SystemActEntity.DataBean.DatasBean>?)!!)
                        } else {
                            mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<SystemActEntity.DataBean.DatasBean>?)!!)
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
                    ToastUtil.showShortToast(activity,"网络异常")
                }

                override fun onStart() {
                }
            })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        isRefresh = false
        getList()
    }

    companion object {
        fun createFragment(id: Int): SystemChildFragment {
            val fragment = SystemChildFragment()
            val args = Bundle()
            args.putInt("cid", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (arguments != null) {
            cid = arguments!!.getInt("cid");
        }
    }
}