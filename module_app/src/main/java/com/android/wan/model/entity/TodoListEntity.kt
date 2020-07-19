package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

class TodoListEntity : BaseEntity() {
    /**
     * data : {"curPage":1,"datas":[{"completeDate":null,"completeDateStr":"","content":"safafa","date":1595088000000,"dateStr":"2020-07-19","id":23764,"priority":0,"status":0,"title":"qqq","type":1,"userId":70206},{"completeDate":null,"completeDateStr":"","content":"111","date":1184774400000,"dateStr":"2007-07-19","id":23766,"priority":0,"status":0,"title":"111","type":1,"userId":70206}],"offset":0,"over":true,"pageCount":1,"size":20,"total":2}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * curPage : 1
         * datas : [{"completeDate":null,"completeDateStr":"","content":"safafa","date":1595088000000,"dateStr":"2020-07-19","id":23764,"priority":0,"status":0,"title":"qqq","type":1,"userId":70206},{"completeDate":null,"completeDateStr":"","content":"111","date":1184774400000,"dateStr":"2007-07-19","id":23766,"priority":0,"status":0,"title":"111","type":1,"userId":70206}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 2
         */
        var curPage = 0
        var offset = 0
        var isOver = false
        var pageCount = 0
        var size = 0
        var total = 0
        var datas: List<DatasBean>? =
            null

        class DatasBean {
            /**
             * completeDate : null
             * completeDateStr :
             * content : safafa
             * date : 1595088000000
             * dateStr : 2020-07-19
             * id : 23764
             * priority : 0
             * status : 0
             * title : qqq
             * type : 1
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
}