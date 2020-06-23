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
    fun getHomeBanner(a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack);

    /**
     * 首页列表接口
     * @param index 页码
     */
    fun getHomeList(index: Int, a: AppCompatActivity, callback: RxhttpUtil.RxHttpCallBack);

}