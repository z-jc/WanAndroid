package com.dq.login.fragment

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dq.login.R
import com.dq.login.activity.LoginActivity
import com.dq.login.adapter.LoginHistoryAdapter
import com.dq.login.config.LoginConfig
import com.dq.login.model.entity.IntegralEntity
import com.dq.login.model.entity.LoginEntity
import com.dq.login.model.entity.LoginHistoryEntity
import com.dq.login.model.model.ApiModel
import com.dq.login.model.model.ApiModelImpl
import com.dq.login.view.LoadUtil
import com.dq.ui.base.BaseFragment
import com.dq.util.EdittorUtil
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.fragment_login.*
import org.litepal.LitePal
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
    var isShowList = false
    var mAdapter: LoginHistoryAdapter? = null

    override fun getContentView(): Int? {
        apiModel = ApiModelImpl()
        loginActivity = getActivity() as LoginActivity?
        return R.layout.fragment_login
    }

    override fun initView() {
        super.initView()
        tvGotoRegistered.setOnClickListener { loginActivity!!.showRegistered() }
        imgHistory.setOnClickListener {
            if (getHistoryList() == null || getHistoryList().size == 0) {
                return@setOnClickListener
            }
            if (isShowList) {
                //隐藏
                imgHistory.setImageResource(R.drawable.icon_login_up)
                recyclerView.visibility = View.GONE
            } else {
                //展开
                imgHistory.setImageResource(R.drawable.icon_login_down)
                EdittorUtil.hideInput(activity)
                recyclerView.visibility = View.VISIBLE
                setRecyclerViewList()
            }
            isShowList = !isShowList
        }

        tvLogin.setOnClickListener {
            if (TextUtils.isEmpty(getUserName())) {
                ToastUtil.showShortToast(loginActivity, "请输入用户名")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(getPsw())) {
                ToastUtil.showShortToast(loginActivity, "请输入密码")
                return@setOnClickListener
            }
            login()
        }

        edLoginUser.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                recyclerView.visibility = View.GONE
            }
        }
    }

    private fun setRecyclerViewList() {
        mAdapter = LoginHistoryAdapter(object : LoginHistoryAdapter.OnItemClickLister {
            override fun onDel(position: Int, entity: LoginHistoryEntity) {
                mAdapter!!.data.remove(entity)
                mAdapter!!.notifyDataSetChanged()
                LitePal.delete(LoginHistoryEntity::class.java, entity.id)
                if (getHistoryList() == null || getHistoryList().size == 0) {
                    recyclerView.visibility = View.GONE
                }
            }

            override fun onItem(position: Int, entity: LoginHistoryEntity) {
                recyclerView.visibility = View.GONE
                edLoginUser.setText(entity.userName)
                edLoginPsw.setText(entity.userPsw)
                EdittorUtil.hideInput(activity)
                login()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        mAdapter!!.setList(getHistoryList())
    }

    fun getHistoryList(): MutableList<LoginHistoryEntity> {
        return LitePal.order("id desc").find(LoginHistoryEntity::class.java)
    }

    private fun login() {
        val map = TreeMap<String, String>()
        map["username"] = getUserName()
        map["password"] = getPsw()
        loginActivity?.let {
            apiModel!!.login(map, it, object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("登录成功:$response")
                    val loginEntity: LoginEntity =
                        JsonUtil.fromJson<LoginEntity>(
                            response,
                            LoginEntity()
                        ) as LoginEntity
                    if (loginEntity.errorCode == 0) {

                        val historyList: MutableList<LoginHistoryEntity> =
                            LitePal.where("userName=?", getUserName())
                                .find(LoginHistoryEntity::class.java)
                        if (historyList != null && historyList.size > 0) {
                            //该账号登录过则不处理
                        } else {
                            //该账号未登录过,进行保存
                            var historyEntity: LoginHistoryEntity = LoginHistoryEntity()
                            historyEntity.userName = getUserName()
                            historyEntity.userPsw = getPsw()
                            historyEntity.save()
                        }

                        LoginConfig().setUserName(loginEntity.data!!.nickname!!)
                        LoginConfig().setIsLogin(true)
                        getUserIntegral()
                    } else {
                        LoadUtil.dismissLoading()
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
                val integralEntity: IntegralEntity =
                    JsonUtil.fromJson<IntegralEntity>(response, IntegralEntity()) as IntegralEntity
                if (integralEntity.errorCode == 0) {
                    LoginConfig().setUserIntegral(integralEntity.data!!.coinCount)
                    LoginConfig().setUserRank(integralEntity.data!!.rank!!)
                    LoginConfig().setUserLevel(integralEntity.data!!.level)
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

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        EdittorUtil.hideInput(activity)
        imgHistory.setImageResource(R.drawable.icon_login_up)
        recyclerView.visibility = View.GONE
        isShowList = false
        edLoginUser.setText("")
        edLoginPsw.setText("")
    }

    companion object {
        fun createFragment(): LoginFragment {
            return LoginFragment()
        }
    }
}