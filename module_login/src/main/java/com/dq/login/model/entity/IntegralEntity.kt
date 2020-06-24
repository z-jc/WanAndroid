package com.dq.login.model.entity

import com.dq.util.http.BaseEntity

/**
 * FileName: IntegralEntity
 * Author: admin
 * Date: 2020/6/24 16:40
 * Description:
 */
class IntegralEntity : BaseEntity() {
    /**
     * data : {"coinCount":10,"level":1,"rank":"42138","userId":68228,"username":"X**12418254"}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * coinCount : 10
         * level : 1
         * rank : 42138
         * userId : 68228
         * username : X**12418254
         */
        var coinCount = 0
        var level = 0
        var rank: String? = null
        var userId = 0
        var username: String? = null

    }
}