package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

/**
 * FileName: ToolAddOutEntity
 * Author: admin
 * Date: 2020/7/21 14:37
 * Description:
 */
class ToolAddOutEntity : BaseEntity() {
    /**
     * data : {"author":"zjc","chapterId":0,"chapterName":"","courseId":13,"desc":"","envelopePic":"","id":145253,"link":"https://blog.csdn.net/qq_28779083/article/details/106998367","niceDate":"刚刚","origin":"","originId":-1,"publishTime":1595313397261,"title":"Flutter入门项目《玩安卓Flutter版》","userId":68228,"visible":0,"zan":0}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * author : zjc
         * chapterId : 0
         * chapterName :
         * courseId : 13
         * desc :
         * envelopePic :
         * id : 145253
         * link : https://blog.csdn.net/qq_28779083/article/details/106998367
         * niceDate : 刚刚
         * origin :
         * originId : -1
         * publishTime : 1595313397261
         * title : Flutter入门项目《玩安卓Flutter版》
         * userId : 68228
         * visible : 0
         * zan : 0
         */
        var author: String? = null
        var chapterId = 0
        var chapterName: String? = null
        var courseId = 0
        var desc: String? = null
        var envelopePic: String? = null
        var id = 0
        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var originId = 0
        var publishTime: Long = 0
        var title: String? = null
        var userId = 0
        var visible = 0
        var zan = 0

    }
}