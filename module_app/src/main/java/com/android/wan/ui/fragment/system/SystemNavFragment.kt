package com.android.wan.ui.fragment.system

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.SystemNavEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.ContentActivity
import com.android.wan.ui.adapter.SystemNavAdapter
import com.android.wan.ui.view.LoadingUtil
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.fragment_system_nav.*

class SystemNavFragment : BaseFragment() {

    var apiModel: ApiModel? = null
    var mAdapter: SystemNavAdapter? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_system_nav
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        mAdapter = SystemNavAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        getList()
        mAdapter!!.setOnItemClickListener { _, _, position ->
            SystemFragment.isIntercept = false
            ContentActivity.startAct(activity!!,
                mAdapter!!.data[position].articles!![position]!!.link!!, mAdapter!!.data[position].articles!![position]!!.title!!)
        }
    }

    fun getList() {
        apiModel!!.getSystemNavList(activity as AppCompatActivity,
            object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    var listEntity: SystemNavEntity =
                        JsonUtil.fromJson<SystemNavEntity>(
                            response,
                            SystemNavEntity()
                        ) as SystemNavEntity
                    if (listEntity.errorCode == 0) {
                        mAdapter!!.setList(listEntity.data)
                    } else {
                        ToastUtil.showShortToast(activity, listEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    LoadingUtil.dismissLoading()
                }

                override fun onError(error: String?) {
                    ILog.e("请求失败:$error")
                    ToastUtil.showShortToast(activity, "网络异常")
                }

                override fun onStart() {
                    LoadingUtil.showLoading(activity, "获取中...")
                }
            })
    }

    companion object {
        fun createFragment(): SystemNavFragment {
            return SystemNavFragment()
        }
    }
}