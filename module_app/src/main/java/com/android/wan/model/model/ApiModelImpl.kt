package com.android.wan.model.model

import androidx.appcompat.app.AppCompatActivity
import com.android.wan.model.http.HttpConfig
import com.dq.util.http.RxhttpUtil

/**
 * Author:ZJC
 * Date:2020/2/4  17:00
 * Description:ApiModelImpl
 */
class ApiModelImpl : ApiModel {

    /**
     * 首页banner接口
     */
    override fun getHomeBanner(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.homeBannerUrl, a, callback)
    }

    /**
     * 首页列表接口
     */
    override fun getHomeList(index: Int, a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.homeListUrl(index), a, callback)
    }

    /**
     * 退出登录接口
     */
    override fun logOut(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.logOutUrl, a, callback)
    }
}