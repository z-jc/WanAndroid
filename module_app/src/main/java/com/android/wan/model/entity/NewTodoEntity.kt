package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

class NewTodoEntity : BaseEntity() {
    /**
     * data : {"completeDate":null,"completeDateStr":"","content":"123456","date":1595001600000,"dateStr":"2020-07-18","id":23757,"priority":0,"status":0,"title":"My Shengri","type":2,"userId":70206}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * completeDate : null
         * completeDateStr :
         * content : 123456
         * date : 1595001600000
         * dateStr : 2020-07-18
         * id : 23757
         * priority : 0
         * status : 0
         * title : My Shengri
         * type : 2
         * userId : 70206
         */
        var completeDate: Any? = null
        var completeDateStr: String? = null
        var content: String? = null
        var date: Long = 0
        var dateStr: String? = null
        var id = 0
        var priority = 0
        var status = 0
        var title: String? = null
        var type = 0
        var userId = 0

    }
}