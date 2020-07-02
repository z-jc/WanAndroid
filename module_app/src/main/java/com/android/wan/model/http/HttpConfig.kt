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
         * 广场列表接口
         */
        fun squareListUrl(int: Int): String {
            return "https://wanandroid.com/user_article/list/" + int + "/json"
        }

        /**
         * 退出登录
         */
        var logOutUrl: String = "https://www.wanandroid.com/user/logout/json"

        /**
         * 公众号标题栏接口
         */
        var publicTitleUrl: String = "https://wanandroid.com/wxarticle/chapters/json"

        /**
         * 获取公众号数据
         * @param title公众号类型
         * @param index 页码
         */
        fun getPublicListUrl(title: String, index: Int): String {
            return "https://wanandroid.com/wxarticle/list/" + title + "/" + index + "/json"
        }

        /**
         * 项目模块标题栏接口
         */
        var projectTitleUrl: String = "https://www.wanandroid.com/project/tree/json"

        /**
         * 项目列表数据
         * @param id项目类型
         * @param index 页码
         */
        fun getProjectListUrl(id: Int, index: Int): String {
            return "https://www.wanandroid.com/project/list/" + index + "/json?cid=" + id
        }

        /**
         * 体系列表
         */
        var systemSysUrl = "https://www.wanandroid.com/tree/json"

        /**
         * 导航列表
         */
        var systemNavUrl = "https://www.wanandroid.com/navi/json"

        /**
         * 积分排行榜
         * @param index 页码
         */
        fun getIntegralRankingListUrl(index: Int): String {
            return "https://www.wanandroid.com/coin/rank/" + index + "/json"
        }
    }
}