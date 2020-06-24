package com.dq.login.model.entity

import com.dq.util.http.BaseEntity

/**
 * FileName: LoginEntity
 * Author: admin
 * Date: 2020/6/24 15:07
 * Description:
 */
class LoginEntity : BaseEntity() {
    /**
     * data : {"admin":false,"chapterTops":[],"collectIds":[],"email":"","icon":"","id":68228,"nickname":"X1612418254","password":"","publicName":"X1612418254","token":"","type":0,"username":"X1612418254"}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * admin : false
         * chapterTops : []
         * collectIds : []
         * email :
         * icon :
         * id : 68228
         * nickname : X1612418254
         * password :
         * publicName : X1612418254
         * token :
         * type : 0
         * username : X1612418254
         */
        var isAdmin = false
        var email: String? = null
        var icon: String? = null
        var id = 0
        var nickname: String? = null
        var password: String? = null
        var publicName: String? = null
        var token: String? = null
        var type = 0
        var username: String? = null
        var chapterTops: List<*>? = null
        var collectIds: List<*>? = null

    }
}