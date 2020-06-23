package com.android.wan.bean

/**
 * FileName: MainBean
 * Author: admin
 * Date: 2020/6/22 20:22
 * Description:
 */
class MenuBean {

    constructor(iconMenuIntegral: Int, s: String){
        this.menuIcon = iconMenuIntegral
        this.menuText = s
    }

    var menuIcon: Int = 0
    var menuText: String? = ""

}