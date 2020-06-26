package com.android.wan.ui.fragment

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.SquareListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.MainActivity
import com.android.wan.ui.adapter.SquareAdapter
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
import kotlinx.android.synthetic.main.fragment_square.*
import kotlinx.android.synthetic.main.title_bar_base.*

class SquareFragment : BaseFragment(), OnLoadMoreListener, OnRefreshListener, View.OnTouchListener {

    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var apiModel: ApiModel? = null
    var mAdapter: SquareAdapter? = null
    var slideStatu = true
    var mainActivity: MainActivity? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_square
    }

    override fun initView() {
        super.initView()
        mainActivity = activity as MainActivity
        apiModel = ApiModelImpl()
        mAdapter = SquareAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
        recyclerView.setOnTouchListener(this)
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
                ToastUtil.showLongToast(activity, "去添加")
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

    override fun onSupportVisible() {
        super.onSupportVisible()
        slideStatu = true
    }

    companion object {
        fun createFragment(): SquareFragment {
            return SquareFragment()
        }
    }

    var x1 = 0F
    var x2 = 0F
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_MOVE) {
            x2 = event.x
            if (slideStatu) {
                x1 = x2
                slideStatu = !slideStatu
            }
            var slide1 = x1 - x2
            if (slide1 > 50) {
                mainActivity!!.slidePan!!.closePane()
            }
        }
        if (event.action == MotionEvent.ACTION_UP) {
            slideStatu = true
        }
        return false
    }
}