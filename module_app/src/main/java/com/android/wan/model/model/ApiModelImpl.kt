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
    override fun getHomeList(
        index: Int,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    ) {
        RxhttpUtil.getInstance().get(HttpConfig.homeListUrl(index), a, callback)
    }

    /**
     * 退出登录接口
     */
    override fun logOut(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.logOutUrl, a, callback)
    }

    /**
     * 广场列表接口
     */
    override fun getSquareList(
        index: Int,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    ) {
        RxhttpUtil.getInstance().get(HttpConfig.squareListUrl(index), a, callback)
    }

    /**
     * 公众号标题栏接口
     */
    override fun getPublicTitleList(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.publicTitleUrl, a, callback)
    }

    /**
     * 获取公众号列表数据
     */
    override fun getPublicList(
        title: String,
        index: Int,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    ) {
        RxhttpUtil.getInstance().get(HttpConfig.getPublicListUrl(title, index), a, callback)
    }

    /**
     * 项目标题栏接口
     */
    override fun getProjectTitleList(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.projectTitleUrl, a, callback)
    }

    /**
     * 项目标题栏接口
     */
    override fun getProjectList(
        id: Int,
        index: Int,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    ) {
        RxhttpUtil.getInstance().get(HttpConfig.getProjectListUrl(id, index), a, callback)
    }

    /**
     * 体系模块列表接口
     */
    override fun getSystemSysList(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.systemSysUrl, a, callback)
    }

    /**
     * 导航模块列表接口
     */
    override fun getSystemNavList(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack) {
        RxhttpUtil.getInstance().get(HttpConfig.systemNavUrl, a, callback)
    }
}