package com.android.wan.ui.fragment.public

import androidx.fragment.app.Fragment
import com.android.wan.R
import com.android.wan.model.entity.PublicTitleEntity
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
import kotlinx.android.synthetic.main.fragment_public.*

class PublicFragment : BaseFragment() {

    var listEntity: PublicTitleEntity? = null
    var apiModel: ApiModel? = null
    protected val mFragments: MutableList<Fragment> = mutableListOf()
    protected var titleList: MutableList<String> = mutableListOf()

    override fun getContentView(): Int? {
        return R.layout.fragment_public
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        getTitleList()
        ViewPagerUtil().setAnim(viewPagerPublic)
    }
    fun getTitleList() {
        activity?.let {
            apiModel!!.getPublicTitleList(it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    listEntity = JsonUtil.fromJson<PublicTitleEntity>(
                        response,
                        PublicTitleEntity()
                    ) as PublicTitleEntity
                    if (listEntity!!.errorCode == 0) {
                        for (item: PublicTitleEntity.DataBean in listEntity!!.data!!) {
                            titleList.add(item.name!!)
                            mFragments.add(PublicChildFragment.createFragment(item.id))
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
                    LoadingUtil.showLoading(activity,"获取中...")
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
        viewPagerPublic.adapter = mAdapter
        viewPagerPublic.offscreenPageLimit = mFragments.size
        tabLayout.setViewPager(viewPagerPublic)
        tabLayout.currentTab = 0
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        if(tabLayout != null){
            tabLayout.currentTab = 0
            viewPagerPublic.currentItem = 0
        }
    }

    companion object {
        fun createFragment(): PublicFragment {
            return PublicFragment()
        }
    }
}