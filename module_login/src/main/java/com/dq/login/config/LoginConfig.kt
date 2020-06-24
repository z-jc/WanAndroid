package com.dq.login.config

import com.dq.util.SPUtil

/**
 * FileName: LoginConfig
 * Author: admin
 * Date: 2020/6/24 14:17
 * Description:
 */
class LoginConfig {

    /**
     * 保存用户名
     */
    fun setUserName(userName: String) {
        SPUtil.setString("user_name", userName)
    }

    /**
     * 获取用户名
     */
    fun getUserName(): String? {
        return SPUtil.getString("user_name")
    }

    /**
     * 保存用户积分
     */
    fun setUserIntegral(integral: Int) {
        SPUtil.setInteger("user_integral", integral)
    }

    /**
     * 获取用户积分
     */
    fun getUserIntegral(): Int? {
        return SPUtil.getInteger("user_integral")
    }

    /**
     * 保存用户等级
     */
    fun setUseLevel(level: Int) {
        SPUtil.setInteger("user_level", level)
    }

    /**
     * 获取用户等级
     */
    fun getUserLevel(): Int? {
        return SPUtil.getInteger("user_level")
    }

    /**
     * 保存用户排名
     */
    fun setUseRank(rank: String) {
        SPUtil.setString("user_rank", rank)
    }

    /**
     * 获取用户排名
     */
    fun getUserRank(): String? {
        return SPUtil.getString("user_rank")
    }

    /**
     * 保存登录状态
     */
    fun setIsLogin(isLogin: Boolean) {
        SPUtil.setBoolean("is_login", isLogin)
    }

    /**
     * 获取登录状态
     */
    fun getIsLogin(): Boolean {
        return SPUtil.getBoolean("is_login")
    }

    /**
     * 退出登录时调用
     */
    fun clear() {
        setIsLogin(false)
        setUserName("")
        setUseLevel(0)
        setUseRank("")
        setUserIntegral(0)
    }
}