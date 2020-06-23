package com.android.wan.config

import com.android.wan.R
import com.android.wan.bean.MenuBean

/**
 * Author : Z-JC
 * Date : 2020/3/5
 * Description :
 */
class AppDataSourse {
    companion object {
        fun getMenuList(): List<MenuBean> {
            var list : MutableList<MenuBean> = mutableListOf()
            list.add(MenuBean(R.drawable.icon_menu_integral,"我的积分"))
            list.add(MenuBean(R.drawable.icon_menu_collection,"我的收藏"))
            list.add(MenuBean(R.drawable.icon_menu_share,"我的分享"))
            list.add(MenuBean(R.drawable.icon_menu_todo,"TODO"))
            list.add(MenuBean(R.drawable.icon_menu_qrcode,"扫一扫"))
            list.add(MenuBean(R.drawable.icon_menu_setting,"系统设置"))
            list.add(MenuBean(R.drawable.icon_menu_logout,"退出登录"))
            return list
        }
    }
}