package com.android.wan.ui.fragment.project

import android.os.Handler
import androidx.fragment.app.Fragment
import com.android.wan.R
import com.android.wan.model.entity.ProjectTitleEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.MainActivity
import com.android.wan.ui.adapter.BaseViewPagerAdapter
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.ui.view.ViewPagerUtil
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment() {

    var mainActivity: MainActivity? = null
    var listEntity: ProjectTitleEntity? = null
    var apiModel: ApiModel? = null
    private val mFragments: MutableList<Fragment> = mutableListOf()
    private var titleList: MutableList<String> = mutableListOf()

    override fun getContentView(): Int? {
        return R.layout.fragment_project
    }

    override fun initView() {
        super.initView()
        mainActivity = activity as MainActivity
        apiModel = ApiModelImpl()
        getTitleList()
        ViewPagerUtil().setAnim(viewPagerProject)
    }

    private fun getTitleList() {
        activity?.let {
            apiModel!!.getProjectTitleList(it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    listEntity = JsonUtil.fromJson<ProjectTitleEntity>(
                        response,
                        ProjectTitleEntity()
                    ) as ProjectTitleEntity
                    if (listEntity!!.errorCode == 0) {
                        for (item: ProjectTitleEntity.DataBean in listEntity!!.data!!) {
                            titleList.add(item.name!!)
                            mFragments.add(ProjectChildFragment.createFragment(item.id))
                        }
                        setTitle()
                    } else {
                        ToastUtil.showShortToast(activity, listEntity!!.errorMsg)
                    }
                }

                override fun onFinish() {
                    LoadingUtil.dismissLoading()
                    ILog.e("请求结束")
                }

                override fun onError(error: String?) {
                    ToastUtil.showShortToast(activity, "网络异常")
                    ILog.e("请求失败:$error")
                }

                override fun onStart() {
                    LoadingUtil.showLoading(activity, "获取中...")
                }
            })
        }
    }

    fun setTitle() {
        val mAdapter = BaseViewPagerAdapter(
            (activity as MainActivity).supportFragmentManager,
            mFragments,
            titleList
        )
        viewPagerProject.adapter = mAdapter
        viewPagerProject.offscreenPageLimit = mFragments.size
        tabLayout.setViewPager(viewPagerProject)
        tabLayout.currentTab = 0//指定显示哪个tab
        viewPagerProject.currentItem = 0
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        if(isIntercept){
            Handler().postDelayed({
                if (tabLayout != null) {
                    tabLayout.currentTab = 0
                    viewPagerProject.currentItem = 0
                }
            }, 300)
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        isIntercept = true
    }

    companion object {
        var isIntercept = true
        fun createFragment(): ProjectFragment {
            return ProjectFragment()
        }
    }
}