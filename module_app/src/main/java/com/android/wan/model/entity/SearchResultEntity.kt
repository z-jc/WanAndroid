package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

class SearchResultEntity : BaseEntity() {
    /**
     * data : {"curPage":1,"datas":[{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12755,"link":"https://blog.csdn.net/fengyeNom1/article/details/104562967","niceDate":"2020-04-07 11:08","niceShareDate":"2020-04-07 11:08","origin":"","prefix":"","projectLink":"","publishTime":1586228935000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1586228935000,"shareUser":"冯人唐","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"*studio3<\/em>.6新特性【视图绑定】是时候开始使用ViewBinding了","type":0,"userId":2318,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"挖掘匠","canEdit":false,"chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":1479,"link":"http://www.jianshu.com/p/9b25087a5d7d","niceDate":"2017-10-31 19:47","niceShareDate":"未知时间","origin":"","prefix":"","projectLink":"","publishTime":1509450445000,"realSuperChapterId":150,"selfVisible":0,"shareDate":null,"shareUser":"","superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"Android *Studio3<\/em>.0正式版填坑路","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"24K纯帅豆","canEdit":false,"chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":1478,"link":"http://www.jianshu.com/p/15afb8234d19","niceDate":"2017-10-31 19:47","niceShareDate":"未知时间","origin":"","prefix":"","projectLink":"","publishTime":1509450421000,"realSuperChapterId":150,"selfVisible":0,"shareDate":null,"shareUser":"","superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"Android *Studio3<\/em>.0更新之路（遇坑必入）","type":0,"userId":-1,"visible":1,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":3}
     * errorCode : 0
     * errorMsg :
     *** */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * curPage : 1
         * datas : [{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12755,"link":"https://blog.csdn.net/fengyeNom1/article/details/104562967","niceDate":"2020-04-07 11:08","niceShareDate":"2020-04-07 11:08","origin":"","prefix":"","projectLink":"","publishTime":1586228935000,"realSuperChapterId":493,"selfVisible":0,"shareDate":1586228935000,"shareUser":"冯人唐","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"*studio3<\/em>.6新特性【视图绑定】是时候开始使用ViewBinding了","type":0,"userId":2318,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"挖掘匠","canEdit":false,"chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":1479,"link":"http://www.jianshu.com/p/9b25087a5d7d","niceDate":"2017-10-31 19:47","niceShareDate":"未知时间","origin":"","prefix":"","projectLink":"","publishTime":1509450445000,"realSuperChapterId":150,"selfVisible":0,"shareDate":null,"shareUser":"","superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"Android *Studio3<\/em>.0正式版填坑路","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"24K纯帅豆","canEdit":false,"chapterId":60,"chapterName":"Android Studio相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":1478,"link":"http://www.jianshu.com/p/15afb8234d19","niceDate":"2017-10-31 19:47","niceShareDate":"未知时间","origin":"","prefix":"","projectLink":"","publishTime":1509450421000,"realSuperChapterId":150,"selfVisible":0,"shareDate":null,"shareUser":"","superChapterId":60,"superChapterName":"开发环境","tags":[],"title":"Android *Studio3<\/em>.0更新之路（遇坑必入）","type":0,"userId":-1,"visible":1,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 3
         *** */
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
             * fresh : false
             * id : 12755
             * link : https://blog.csdn.net/fengyeNom1/article/details/104562967
             * niceDate : 2020-04-07 11:08
             * niceShareDate : 2020-04-07 11:08
             * origin :
             * prefix :
             * projectLink :
             * publishTime : 1586228935000
             * realSuperChapterId : 493
             * selfVisible : 0
             * shareDate : 1586228935000
             * shareUser : 冯人唐
             * superChapterId : 494
             * superChapterName : 广场Tab
             * tags : []
             * title : *studio3*.6新特性【视图绑定】是时候开始使用ViewBinding了
             * type : 0
             * userId : 2318
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