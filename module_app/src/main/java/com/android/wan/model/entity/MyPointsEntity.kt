package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

/**
 * FileName: MyPointsEntity
 * Author: admin
 * Date: 2020/7/2 19:50
 * Description:
 */
class MyPointsEntity : BaseEntity() {
    /**
     * data : {"curPage":1,"datas":[{"coinCount":15,"date":1593655840000,"desc":"2020-07-02 10:10:40 签到 , 积分：10 + 5","id":245822,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":14,"date":1593417357000,"desc":"2020-06-29 15:55:57 签到 , 积分：10 + 4","id":243718,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":13,"date":1593329977000,"desc":"2020-06-28 15:39:37 签到 , 积分：10 + 3","id":242869,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":12,"date":1593189008000,"desc":"2020-06-27 00:30:08 签到 , 积分：10 + 2","id":241949,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":11,"date":1593019644000,"desc":"2020-06-25 01:27:24 签到 , 积分：10 + 1","id":241390,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":10,"date":1592982018000,"desc":"2020-06-24 15:00:18 签到 , 积分：10 + 0","id":241163,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"}],"offset":0,"over":true,"pageCount":1,"size":20,"total":6}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * curPage : 1
         * datas : [{"coinCount":15,"date":1593655840000,"desc":"2020-07-02 10:10:40 签到 , 积分：10 + 5","id":245822,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":14,"date":1593417357000,"desc":"2020-06-29 15:55:57 签到 , 积分：10 + 4","id":243718,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":13,"date":1593329977000,"desc":"2020-06-28 15:39:37 签到 , 积分：10 + 3","id":242869,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":12,"date":1593189008000,"desc":"2020-06-27 00:30:08 签到 , 积分：10 + 2","id":241949,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":11,"date":1593019644000,"desc":"2020-06-25 01:27:24 签到 , 积分：10 + 1","id":241390,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"},{"coinCount":10,"date":1592982018000,"desc":"2020-06-24 15:00:18 签到 , 积分：10 + 0","id":241163,"reason":"签到","type":1,"userId":68228,"userName":"X1612418254"}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 6
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
             * coinCount : 15
             * date : 1593655840000
             * desc : 2020-07-02 10:10:40 签到 , 积分：10 + 5
             * id : 245822
             * reason : 签到
             * type : 1
             * userId : 68228
             * userName : X1612418254
             */
            var coinCount = 0
            var date: Long = 0
            var desc: String? = null
            var id = 0
            var reason: String? = null
            var type = 0
            var userId = 0
            var userName: String? = null

        }
    }
}