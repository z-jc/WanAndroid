package com.android.demo.ui.fragment

import android.view.View
import com.android.demo.R
import com.android.demo.ui.activity.MainActivity
import com.android.demo.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.http.RxhttpUtil
import com.dq.util.http.RxhttpUtil.RxHttpCallBack
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.title_bar_base.*
import java.util.*

class HomeFragment : BaseFragment() {
    
    override fun getContentView(): Int? {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        imgBack.visibility = View.GONE
        tvTitle.text = "首页"
        val registeredUrl = "https://www.wanandroid.com/user/register"
        val loginUrl = "https://www.wanandroid.com/user/login"
        val userMap: MutableMap<String, String> = HashMap()
        val loginMap: MutableMap<String, String> = HashMap()
        val psd = randomPsd
        val user = randomUser
        userMap["username"] = user
        userMap["password"] = psd
        userMap["repassword"] = psd
        loginMap["username"] = user
        loginMap["password"] = psd

        btnGet.setOnClickListener(View.OnClickListener {
            ILog.e("map1:$userMap")
            ILog.e("map2:$loginMap")
            RxhttpUtil.getInstance().post(
                activity as MainActivity?,
                registeredUrl,
                loginUrl,
                userMap,
                loginMap,
                object : RxHttpCallBack {
                    override fun onStart() {
                        ILog.e("开始请求")
                    }
                    override fun onSuccess(response: String) {
                        ILog.e("开始成功:$response")
                    }
                    override fun onError(error: String) {
                        ILog.e("开始失败:$error")
                    }
                    override fun onFinish() {
                        ILog.e("请求结束")
                    }
                })
        })
    }

    /**
     * 获取用户名
     *
     * @return
     */
    private val randomUser: String
        private get() {
            val max = 100
            val min = 1
            val randomNum = System.currentTimeMillis()
            return "x" + (randomNum % (max - min) + min).toString()
        }

    /**
     * 获取用户密码
     *
     * @return
     */
    private val randomPsd: String
        private get() {
            val max = 100
            val min = 1
            val randomNum = System.currentTimeMillis()
            return (randomNum % (max - min) + min).toString() + "abc"
        }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        setTitleBackground(BG_WHITE)
    }

    companion object {
        fun createFragment(): HomeFragment {
            return HomeFragment()
        }
    }
}
