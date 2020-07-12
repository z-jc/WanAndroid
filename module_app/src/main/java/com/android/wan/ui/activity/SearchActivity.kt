package com.android.wan.ui.activity

import android.app.Dialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.SearchHistoryEntity
import com.android.wan.model.entity.SearchHotEntity
import com.android.wan.model.entity.SearchResultEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.SearchHistoryAdapter
import com.android.wan.ui.adapter.SearchResultAdapter
import com.android.wan.util.RvAnimUtils
import com.dq.ui.base.BaseActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.util.EdittorUtil
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.title_search.*
import org.litepal.LitePal

class SearchActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener,
    SearchHistoryAdapter.OnItemClickLister {

    var apiModel: ApiModel? = null
    var pageIndex: Int? = 0
    var isRefresh: Boolean? = false
    var mAdapter: SearchResultAdapter? = null
    var historyAdapter: SearchHistoryAdapter? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_search
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = SearchResultAdapter()
        historyAdapter = SearchHistoryAdapter(this)
        imgBack.setImageResource(R.drawable.icon_back_white)
        recyclerViewResult.layoutManager = LinearLayoutManager(this)
        recyclerViewResult.adapter = mAdapter
        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)

        recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        recyclerViewHistory.adapter = historyAdapter
        refreshHistoryList()
    }

    override fun initData() {
        super.initData()
        imgBack.setOnClickListener { finish() }
        imgTitle.setOnClickListener {
            if (TextUtils.isEmpty(getEdContent())) {
                ToastUtil.showShortToast(this, "请输入干货名称")
                return@setOnClickListener
            }
            getSearchResult()
        }
        tvClear.setOnClickListener {
            DialogCustom(this@SearchActivity)
                .setLeft("否")
                .setRight("是")
                .setMsg("是否清空")
                .setActionLister(object : DialogCustom.ActionLister {
                    override fun onLeftClick(dialog: Dialog?) {
                        dialog!!.dismiss()
                    }

                    override fun onRightClick(dialog: Dialog?) {
                        dialog!!.dismiss()
                        LitePal.deleteAll(SearchHistoryEntity::class.java)
                        refreshHistoryList()
                    }
                }).show()
        }
        edContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s.toString())) {
                    refreshLayout.visibility = View.GONE
                }
            }
        })
        getHot()
    }

    fun setTabFlowLayout(tabList: MutableList<String>) {
        flowLayout.adapter = object : TagAdapter<String?>(tabList as List<String>) {
            override fun getView(parent: FlowLayout?, position: Int, s: String?): View? {
                val tvItem: TextView = layoutInflater.inflate(
                    R.layout.item_flowlayout,
                    flowLayout, false
                ) as TextView
                tvItem.text = s
                return tvItem
            }
        }

        flowLayout.setOnTagClickListener(TagFlowLayout.OnTagClickListener { _, position, _ ->
            edContent.setText(tabList.get(position))
            edContent.setSelection(tabList.get(position).length)
            getSearchResult()
            false
        })
    }

    private fun getHot() {
        apiModel!!.getSearchHot(this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("搜索热词")
                val hotEntity: SearchHotEntity = JsonUtil.fromJson<SearchHotEntity>(
                    response,
                    SearchHotEntity()
                ) as SearchHotEntity
                if (hotEntity.errorCode == 0) {
                    var data: MutableList<String> = mutableListOf()
                    for (dates: SearchHotEntity.DataBean in hotEntity.data!!) {
                        dates.name?.let { data.add(it) }
                    }
                    setTabFlowLayout(data)
                }
            }

            override fun onFinish() {
            }

            override fun onError(error: String?) {
            }

            override fun onStart() {
            }
        })
    }

    private fun getSearchResult() {
        pageIndex?.let {
            apiModel!!.getSearchResult(getEdContent().toString(),
                it, this@SearchActivity, object : RxhttpUtil.RxHttpCallBack {
                    override fun onSuccess(response: String?) {
                        ILog.e("搜索结果:$response")
                        val resultEntity: SearchResultEntity =
                            JsonUtil.fromJson<SearchResultEntity>(
                                response,
                                SearchResultEntity()
                            ) as SearchResultEntity
                        if (resultEntity.errorCode == 0) {
                            if (isRefresh!!) {
                                mAdapter!!.addData((resultEntity.data!!.datas as MutableList<SearchResultEntity.DataBean.DatasBean>?)!!)
                            } else {
                                var historyEntity = SearchHistoryEntity()
                                historyEntity.query = getEdContent()
                                historyEntity.save()
                                mAdapter!!.setNewInstance((resultEntity.data!!.datas as MutableList<SearchResultEntity.DataBean.DatasBean>?)!!)
                            }
                        } else {
                            ToastUtil.showShortToast(this@SearchActivity, resultEntity.errorMsg)
                        }
                    }

                    override fun onFinish() {
                        RvAnimUtils.setAnim(mAdapter!!)
                        refreshLayout.finishLoadMore()
                        refreshLayout.finishRefresh()
                        refreshHistoryList()
                    }

                    override fun onError(error: String?) {
                        ILog.e("请求失败$error")
                        ToastUtil.showShortToast(this@SearchActivity, "网络异常")
                    }

                    override fun onStart() {
                        refreshLayout.visibility = View.VISIBLE
                        EdittorUtil.hideInput(this@SearchActivity)
                        if (!this@SearchActivity.isRefresh!!) {
                            mAdapter?.data?.clear()
                        }
                    }
                })
        }
    }

    /**
     * 刷新历史记录列表
     */
    private fun refreshHistoryList() {
        var historyList: MutableList<SearchHistoryEntity> =
            LitePal.order("id desc").find(SearchHistoryEntity::class.java)
        if (historyList != null) {
            historyAdapter!!.setNewInstance(historyList)
        }
    }

    private fun getEdContent(): String? {
        return edContent.text.toString().trim()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        isRefresh = false
        getSearchResult()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getSearchResult()
    }

    override fun onItem(position: Int, entity: SearchHistoryEntity) {
        edContent.setText(entity.query)
        edContent.setSelection(entity.query!!.length)
        getSearchResult()
    }

    override fun onDel(position: Int, entity: SearchHistoryEntity) {
        LitePal.delete(SearchHistoryEntity::class.java, entity.id)
        refreshHistoryList()
    }
}