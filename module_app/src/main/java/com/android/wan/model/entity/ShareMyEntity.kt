package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

class ShareMyEntity : BaseEntity() {
    /**
     * data : {"coinInfo":{"coinCount":118,"level":2,"rank":"3792","userId":68228,"username":"X**12418254"},"shareArticles":{"curPage":1,"datas":[{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":14149,"link":"https://blog.csdn.net/cheng545/article/details/53786122?utm_source=blogxgwz4","niceDate":"22小时前","niceShareDate":"22小时前","origin":"","prefix":"","projectLink":"","publishTime":1593789328000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1593789328000,"shareUser":"X1612418254","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"调用浏览器打开网页链接","type":0,"userId":68228,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":1}}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * coinInfo : {"coinCount":118,"level":2,"rank":"3792","userId":68228,"username":"X**12418254"}
         * shareArticles : {"curPage":1,"datas":[{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":14149,"link":"https://blog.csdn.net/cheng545/article/details/53786122?utm_source=blogxgwz4","niceDate":"22小时前","niceShareDate":"22小时前","origin":"","prefix":"","projectLink":"","publishTime":1593789328000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1593789328000,"shareUser":"X1612418254","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"调用浏览器打开网页链接","type":0,"userId":68228,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":1}
         */
        var coinInfo: CoinInfoBean? =
            null
        var shareArticles: ShareArticlesBean? =
            null

        class CoinInfoBean {
            /**
             * coinCount : 118
             * level : 2
             * rank : 3792
             * userId : 68228
             * username : X**12418254
             */
            var coinCount = 0
            var level = 0
            var rank: String? = null
            var userId = 0
            var username: String? = null

        }

        class ShareArticlesBean {
            /**
             * curPage : 1
             * datas : [{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":14149,"link":"https://blog.csdn.net/cheng545/article/details/53786122?utm_source=blogxgwz4","niceDate":"22小时前","niceShareDate":"22小时前","origin":"","prefix":"","projectLink":"","publishTime":1593789328000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1593789328000,"shareUser":"X1612418254","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"调用浏览器打开网页链接","type":0,"userId":68228,"visible":0,"zan":0}]
             * offset : 0
             * over : true
             * pageCount : 1
             * size : 20
             * total : 1
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
                 * apkLink :
                 * audit : 1
                 * author :
                 * canEdit : false
                 * chapterId : 494
                 * chapterName : 广场
                 * collect : false
                 * courseId : 13
                 * desc :
                 * descMd :
                 * envelopePic :
                 * fresh : true
                 * id : 14149
                 * link : https://blog.csdn.net/cheng545/article/details/53786122?utm_source=blogxgwz4
                 * niceDate : 22小时前
                 * niceShareDate : 22小时前
                 * origin :
                 * prefix :
                 * projectLink :
                 * publishTime : 1593789328000
                 * realSuperChapterId : 493
                 * selfVisible : 0
                 * shareDate : 1593789328000
                 * shareUser : X1612418254
                 * superChapterId : 494
                 * superChapterName : 广场Tab
                 * tags : []
                 * title : 调用浏览器打开网页链接
                 * type : 0
                 * userId : 68228
                 * visible : 0
                 * zan : 0
                 */
                var apkLink: String? = null
                var audit = 0
                var author: String? = null
                var isCanEdit = false
                var chapterId = 0
                var chapterName: String? = null
                var isCollect = false
                var courseId = 0
                var desc: String? = null
                var descMd: String? = null
                var envelopePic: String? = null
                var isFresh = false
                var id = 0
                var link: String? = null
                var niceDate: String? = null
                var niceShareDate: String? = null
                var origin: String? = null
                var prefix: String? = null
                var projectLink: String? = null
                var publishTime: Long = 0
                var realSuperChapterId = 0
                var selfVisible = 0
                var shareDate: Long = 0
                var shareUser: String? = null
                var superChapterId = 0
                var superChapterName: String? = null
                var title: String? = null
                var type = 0
                var userId = 0
                var visible = 0
                var zan = 0
                var tags: List<*>? = null

            }
        }
    }
}