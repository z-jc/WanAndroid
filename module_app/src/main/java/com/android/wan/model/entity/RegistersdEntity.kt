package com.android.wan.model.entity

/**
 * FileName: RegistersdEntity
 * Author: admin
 * Date: 2020/6/5 10:43
 * Description:
 */
class RegistersdEntity : BaseEntity() {
    /**
     * data : {"admin":false,"chapterTops":[],"collectIds":[],"email":"","icon":"","id":66029,"nickname":"x960188","password":"","publicName":"x960188","token":"","type":0,"username":"x960188"}
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
         * id : 66029
         * nickname : x960188
         * password :
         * publicName : x960188
         * token :
         * type : 0
         * username : x960188
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