package com.dq.login.fragment

import android.text.TextUtils
import com.dq.login.R
import com.dq.login.activity.LoginActivity
import com.dq.login.model.entity.RegistersdEntity
import com.dq.login.model.model.ApiModel
import com.dq.login.model.model.ApiModelImpl
import com.dq.login.view.LoadUtil
import com.dq.ui.base.BaseFragment
import com.dq.util.EdittorUtil
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.fragment_registered.*
import java.util.*

/**
 * FileName: LoginFragment
 * Author: admin
 * Date: 2020/6/24 10:51
 * Description:
 */
class RegisteredFragment : BaseFragment() {

    var apiModel: ApiModel? = null
    var loginActivity: LoginActivity? = null

    override fun getContentView(): Int? {
        apiModel = ApiModelImpl()
        loginActivity = getActivity() as LoginActivity?
        return R.layout.fragment_registered
    }

    override fun initView() {
        super.initView()
        tvGotoLogin.setOnClickListener { loginActivity!!.showLogin() }
        tvRegistered.setOnClickListener {
            if (TextUtils.isEmpty(getUserName())) {
                ToastUtil.showShortToast(loginActivity, "请输入用户名")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(getPsw())) {
                ToastUtil.showShortToast(loginActivity, "请输入密码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(getRsPsw())) {
                ToastUtil.showShortToast(loginActivity, "请再次输入密码")
                return@setOnClickListener
            }
            registered()
        }
    }

    private fun registered() {
        val map = TreeMap<String, String>()
        map["username"] = getUserName()
        map["password"] = getPsw()
        map["repassword"] = getRsPsw()
        loginActivity?.let {
            apiModel!!.registered(map, it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("注册成功:$response")
                    val registeredEntity: RegistersdEntity =
                        JsonUtil.fromJson<RegistersdEntity>(
                            response,
                            RegistersdEntity()
                        ) as RegistersdEntity
                    if (registeredEntity.errorCode == 0) {
                        ToastUtil.showShortToast(loginActivity, "注册成功")
                        loginActivity!!.showLogin()
                    } else {
                        ToastUtil.showShortToast(loginActivity, registeredEntity.errorMsg)
                    }
                }

                override fun onFinish() {
                    LoadUtil.dismissLoading()
                    ILog.e("注册结束")
                }

                override fun onError(error: String?) {
                    ToastUtil.showShortToast(loginActivity, "网络异常")
                    ILog.e("注册异常:$error")
                }

                override fun onStart() {
                    LoadUtil.showLoading(loginActivity, "注册中...")
                    ILog.e("开始注册")
                }
            })
        }
    }

    private fun getUserName(): String {
        return edRegisteredUser.text.toString()
    }

    private fun getPsw(): String {
        return edRegisteredPsw.text.toString()
    }

    private fun getRsPsw(): String {
        return edRegisteredRePsw.text.toString()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        EdittorUtil.hideInput(activity)
        edRegisteredUser.setText("")
        edRegisteredPsw.setText("")
        edRegisteredRePsw.setText("")
    }

    companion object {
        fun createFragment(): RegisteredFragment {
            return RegisteredFragment()
        }
    }
}