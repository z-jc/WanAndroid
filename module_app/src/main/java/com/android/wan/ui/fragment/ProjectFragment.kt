package com.android.wan.ui.fragment

import androidx.fragment.app.Fragment
import com.android.wan.R
import com.android.wan.model.entity.ProjectTitleEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.activity.MainActivity
import com.android.wan.ui.adapter.BaseViewPagerAdapter
import com.android.wan.ui.view.LoadingUtil
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
    protected val mFragments: MutableList<Fragment> = mutableListOf()
    protected var titleList: MutableList<String> = mutableListOf()

    override fun getContentView(): Int? {
        return R.layout.fragment_project
    }

    override fun initView() {
        super.initView()
        mainActivity = activity as MainActivity
        apiModel = ApiModelImpl()
        getTitleList()
    }

    fun getTitleList() {
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
        var mAdapter = BaseViewPagerAdapter(
            (activity as MainActivity).supportFragmentManager,
            mFragments,
            titleList
        )
        viewPagerProject.setAdapter(mAdapter);
        viewPagerProject.setOffscreenPageLimit(mFragments.size);
        tabLayout.setViewPager(viewPagerProject);
        tabLayout.setCurrentTab(0);//指定显示哪个tab
    }

    companion object {
        fun createFragment(): ProjectFragment {
            return ProjectFragment()
        }
    }
}