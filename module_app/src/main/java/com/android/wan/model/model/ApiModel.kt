package com.android.wan.model.model

import androidx.appcompat.app.AppCompatActivity
import com.dq.util.http.RxhttpUtil

/**
 * Author:ZJC
 * Date:2020/2/4  16:58
 * Description:ApiModel
 */
interface ApiModel {

    /**
     * 首页banner接口
     */
    fun getHomeBanner(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 首页列表接口
     * @param index 页码
     */
    fun getHomeList(index: Int, a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 退出登录接口
     */
    fun logOut(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 广场列表接口
     * @param index 页码
     */
    fun getSquareList(index: Int, a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 公众号标题栏接口
     */
    fun getPublicTitleList(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 获取公众号列表数据
     */
    fun getPublicList(
        title: String,
        index: Int,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    )

    /**
     * 项目模块标题栏接口
     */
    fun getProjectTitleList(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack)

    /**
     * 获取项目列表数据
     */
    fun getProjectList(
        id: Int,
        index: Int,
        a: AppCompatActivity,
        callback: RxhttpUtil.RxHttpCallBack
    )
}