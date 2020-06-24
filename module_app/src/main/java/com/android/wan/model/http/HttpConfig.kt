package com.android.wan.model.http

/**
 * Author : Z-JC
 * Date : 2020/1/12
 * Description :
 */
class HttpConfig {
    companion object {

        /**
         * 首页banner接口
         */
        var homeBannerUrl: String = "https://www.wanandroid.com/banner/json"

        /**
         * 首页列表接口
         */
        fun homeListUrl(int: Int): String {
            return "https://www.wanandroid.com/article/list/$int/json"
        }

        /**
         * 退出登录
         */
        var logOutUrl: String = "https://www.wanandroid.com/user/logout/json"

    }
}