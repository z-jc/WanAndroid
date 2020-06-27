package com.android.wan.ui.fragment.home

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.HomeBannerEntity
import com.android.wan.model.entity.HomeListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ContentActivity
import com.android.wan.ui.adapter.HomeAdapter
import com.android.wan.ui.holder.CustomViewHolder
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.RvAnimUtils
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.dq.util.http.RxhttpUtil.RxHttpCallBack
import com.ms.banner.Banner
import com.ms.banner.BannerConfig
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), OnLoadMoreListener, OnRefreshListener {

    var mAdapter: HomeAdapter? = null
    var headerView: View? = null
    var banner: Banner? = null
    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var apiModel: ApiModel? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = HomeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        headerView = LayoutInflater.from(activity).inflate(R.layout.layout_home_header, null)
        banner = headerView!!.findViewById(R.id.banner)
        mAdapter!!.addHeaderView(headerView!!)
        refreshLayout.setRefreshHeader(MaterialHeader(activity))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        getHomeBanner()
    }

    private fun getHomeBanner() {
        activity?.let {
            apiModel!!.getHomeBanner(it, object : RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    val bannerEntity: HomeBannerEntity =
                        JsonUtil.fromJson<HomeBannerEntity>(
                            response,
                            HomeBannerEntity()
                        ) as HomeBannerEntity
                    if (bannerEntity.errorCode == 0) {
                        startBanner(bannerEntity)
                    } else {
                        ToastUtil.showShortToast(activity, bannerEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    LoadingUtil.dismissLoading()
                    refreshLayout.autoRefresh()
                }

                override fun onError(error: String?) {
                }

                override fun onStart() {
                    LoadingUtil.showLoading(activity,"获取中...")
                }
            })
        }
    }

    private fun getHomeList() {
        activity?.let {
            apiModel!!.getHomeList(pageIndex!!, it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    var listEntity: HomeListEntity =
                        JsonUtil.fromJson<HomeListEntity>(
                            response,
                            HomeListEntity()
                        ) as HomeListEntity
                    if (listEntity.errorCode == 0) {
                        if (isRefresh!!) {
                            mAdapter!!.addData((listEntity.data!!.datas as MutableList<HomeListEntity.DataBean.DatasBean>?)!!)
                        } else {
                            mAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<HomeListEntity.DataBean.DatasBean>?)!!)
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

    private fun startBanner(bannerEntity: HomeBannerEntity) {

        val imageList: MutableList<String> = mutableListOf()
        val titleList: MutableList<String> = mutableListOf()
        for (item: HomeBannerEntity.DataBean in bannerEntity.data!!) {
            imageList.add(item.imagePath!!)
            titleList.add(item.title!!)
        }

        banner!!.setAutoPlay(true)
            .setPages(imageList, CustomViewHolder())
            .setBannerTitles(titleList)
            .setBannerStyle(BannerConfig.NOT_INDICATOR)
            .setDelayTime(3000)
            .start()

        banner!!.setOnBannerClickListener { _, position ->
            bannerEntity.data!![position].url?.let {
                activity?.let { it1 ->
                    bannerEntity.data!![position].title?.let { it2 ->
                        ContentActivity.startAct(
                            it1,
                            it, it2
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun createFragment(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getHomeList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        isRefresh = false
        getHomeList()
    }
}