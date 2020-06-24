package com.dq.login.model.model

import androidx.appcompat.app.AppCompatActivity
import com.dq.util.http.RxhttpUtil

/**
 * Author:ZJC
 * Date:2020/2/4  16:58
 * Description:ApiModel
 */
interface ApiModel {

    /**
     * 用户登录接口
     */
    fun login(map: Map<String, String>, a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 用户注册接口
     */
    fun registered(
        map: Map<String, String>,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    )

    /**
     * 获取个人积分
     */
    fun getUserIntegral(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

}