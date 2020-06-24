package com.dq.login.model.http

/**
 * Author : Z-JC
 * Date : 2020/1/12
 * Description :
 */
class HttpConfig {
    companion object {

        /**
         * 登录接口地址
         */
        var userLoginUrl: String = "https://www.wanandroid.com/user/login"

        /**
         * 注册接口地址
         */
        var userRegisteredUrl: String = "https://www.wanandroid.com/user/register"

        /**
         * 获取个人积分接口
         */
        var userIntegralUrl: String = "https://www.wanandroid.com/lg/coin/userinfo/json"
    }
}