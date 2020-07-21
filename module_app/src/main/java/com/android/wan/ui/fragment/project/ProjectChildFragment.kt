package com.android.wan.ui.fragment.project

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.ProjectListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ContentActivity
import com.android.wan.ui.adapter.ProjectChildAdapter
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
import kotlinx.android.synthetic.main.fragment_project_child.*

class ProjectChildFragment : BaseFragment(), OnLoadMoreListener, OnRefreshListener {

    //项目类型
    var parentChapterId: Int = 0
    var pageIndex: Int? = 1
    var isRefresh: Boolean? = false
    var apiModel: ApiModel? = null
    var mAdapter: ProjectChildAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_project_child
    }

    companion object {
        fun createFragment(parentChapterId: Int): ProjectChildFragment {
            val fragment = ProjectChildFragment()
            val args = Bundle()
            args.putInt("parentChapterId", parentChapterId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = ProjectChildAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
        mAdapter!!.setOnItemClickListener { _, _, position ->
            ProjectFragment.isIntercept = false
            ContentActivity.startAct(
                activity!!,
                mAdapter!!.data[position].link!!,
                mAdapter!!.data[position].title!!
            )
        }
    }

    private fun getProjectList() {
        ILog.e("开始请求:$parentChapterId")
        activity?.let {
            pageIndex?.let { it1 ->
                apiModel!!.getProjectList(parentChapterId, it1,
                    it, object : RxhttpUtil.RxHttpCallBack {
                        override fun onSuccess(response: String?) {
                            val listEntity: ProjectListEntity =
                                JsonUtil.fromJson<ProjectListEntity>(
                                    response,
                                    ProjectListEntity()
                                ) as ProjectListEntity
                            if (listEntity.errorCode == 0) {
                                if (isRefresh!!) {
                                    mAdapter!!.addData((listEntity.data!!.datas as MutableList<ProjectListEntity.DataBean.DatasBean>?)!!)
                                } else {
                                    mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<ProjectListEntity.DataBean.DatasBean>?)!!)
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
                            ILog.e("请求失败$error")
                            ToastUtil.showShortToast(activity, "网络异常")
                        }

                        override fun onStart() {
                        }
                    })
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getProjectList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 1
        isRefresh = false
        getProjectList()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (arguments != null) {
            parentChapterId = arguments!!.getInt("parentChapterId")
        }
    }
}