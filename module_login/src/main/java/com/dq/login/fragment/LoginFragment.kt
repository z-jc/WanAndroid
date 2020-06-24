package com.dq.login.fragment

import android.text.TextUtils
import com.dq.login.R
import com.dq.login.activity.LoginActivity
import com.dq.login.config.LoginConfig
import com.dq.login.model.entity.IntegralEntity
import com.dq.login.model.entity.LoginEntity
import com.dq.login.model.model.ApiModel
import com.dq.login.model.model.ApiModelImpl
import com.dq.login.view.LoadUtil
import com.dq.ui.base.BaseFragment
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

/**
 * FileName: LoginFragment
 * Author: admin
 * Date: 2020/6/24 10:51
 * Description:
 */
class LoginFragment : BaseFragment() {

    var apiModel: ApiModel? = null
    var loginActivity: LoginActivity? = null

    override fun getContentView(): Int? {
        apiModel = ApiModelImpl()
        loginActivity = getActivity() as LoginActivity?
        return R.layout.fragment_login
    }

    override fun initView() {
        super.initView()
        tvGotoRegistered.setOnClickListener { loginActivity!!.showRegistered() }
        tvLogin.setOnClickListener {
            if (TextUtils.isEmpty(getUserName())) {
                ToastUtil.showShortToast(loginActivity, "请输入用户名")
            }
            if (TextUtils.isEmpty(getPsw())) {
                ToastUtil.showShortToast(loginActivity, "请输入密码")
            }
            login()
        }
    }

    private fun login(){
        val map = TreeMap<String, String>()
        map["username"] = getUserName()
        map["password"] = getPsw()
        loginActivity?.let {
            apiModel!!.login(map, it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("登录成功:$response")
                    var loginEntity: LoginEntity =
                        JsonUtil.fromJson<LoginEntity>(
                            response,
                            LoginEntity()
                        ) as LoginEntity
                    if (loginEntity.errorCode == 0) {
                        LoginConfig().setUserName(loginEntity!!.data!!.nickname!!)
                        LoginConfig().setIsLogin(true)
                        getUserIntegral()
                    } else {
                        ToastUtil.showShortToast(loginActivity, loginEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    ILog.e("登录结束")
                }

                override fun onError(error: String?) {
                    LoadUtil.dismissLoading()
                    ToastUtil.showShortToast(loginActivity, "网络异常")
                    ILog.e("登录异常:$error")
                }

                override fun onStart() {
                    LoadUtil.showLoading(loginActivity, "正在登陆...")
                    ILog.e("开始登录")
                }
            })
        }
    }

    /**
     * 获取个人积分
     */
    private fun getUserIntegral() {
        apiModel!!.getUserIntegral(loginActivity!!, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("个人积分$response")
                var integralEntity: IntegralEntity =
                    JsonUtil.fromJson<IntegralEntity>(response, IntegralEntity()) as IntegralEntity
                if (integralEntity.errorCode == 0) {
                    LoginConfig().setUserIntegral(integralEntity.data!!.coinCount)
                    LoginConfig().setUserRank(integralEntity!!.data!!.rank!!)
                    LoginConfig().setUseLevel(integralEntity!!.data!!.level!!)
                }
            }

            override fun onFinish() {
                LoadUtil.dismissLoading()
                loginActivity!!.finish()
            }
            override fun onError(error: String?) {}
            override fun onStart() {}
        })
    }

    private fun getUserName(): String {
        return edLoginUser.text.toString()
    }

    private fun getPsw(): String {
        return edLoginPsw.text.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        edLoginUser.setText("")
        edLoginPsw.setText("")
    }

    companion object {
        fun createFragment(): LoginFragment {
            return LoginFragment()
        }
    }
}