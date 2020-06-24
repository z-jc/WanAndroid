package com.dq.login.model.model

import androidx.appcompat.app.AppCompatActivity
import com.dq.login.model.http.HttpConfig
import com.dq.util.http.RxhttpUtil

/**
 * Author:ZJC
 * Date:2020/2/4  17:00
 * Description:ApiModelImpl
 */
class ApiModelImpl : ApiModel {

    /**
     * 用户登录
     */
    override fun login(
        map: Map<String, String>,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    ) {
        RxhttpUtil.getInstance().post(HttpConfig.userLoginUrl,map,a,callback)
    }

    /**
     * 用户注册
     */
    override fun registered(
        map: Map<String, String>,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    ) {
        RxhttpUtil.getInstance().post(HttpConfig.userRegisteredUrl,map,a,callback)
    }

    /**
     * 获取个人积分
     */
    override fun getUserIntegral(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.userIntegralUrl, a, callback)
    }
}