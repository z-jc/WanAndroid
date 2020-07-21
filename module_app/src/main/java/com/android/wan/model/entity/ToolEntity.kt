package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

/**
 * FileName: ToolEntity
 * Author: admin
 * Date: 2020/7/21 14:16
 * Description:
 */
class ToolEntity : BaseEntity() {
    /**
     * data : {"curPage":1,"datas":[{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"
     *
     *在 Android 7.0了，引入了「Network Security Configuration」，当时默认配置为信任系统内置证书以及用户安装证书，并且允许访问 HTTP 请求。<\/p>\r\n
     *
     *而在 Android 9.0上，默认只信任系统证书了，并且默认不再允许访问 HTTP 请求，所以在很多适配P文章中，因为无法访问 HTTP 了，无法抓包了，所以我们要引入如下配置：<\/p>\r\n<pre>`<base-config> \r\n    <trust-anchors>\r\n        <certificates src="system" />\r\n                 <certificates src="user" /> // 信任用户安装证书\r\n    </trust-anchors>\r\n</base-config>\r\n<\/code><\/pre>`</pre>
     *
     *问题来了，今天在一个技术群，有人说：<\/p>\r\n
     *
     ***高版本的(AndroidP )，需要配置 android:networkSecurityConfig=&quot;@xml/network_security_config&quot;(上面的配置) ，才能抓包<\/strong><\/p>\r\n\r\n 1. 这个结论一定是正确的吗？<\/li>\r\n 1. 我在Android P以及以上的设备中不配置networkSecurityConfig，就一定无法抓包吗？<\/li>\r\n<\/ol>\r\n**
     *
     *其实之前问过类似的问题，上次没人回答，这次细化到具体场景了。<\/p>","envelopePic":"","id":144799,"link":"https://www.wanandroid.com/wenda/show/13949","niceDate":"2020-07-18 12:43","origin":"","originId":13949,"publishTime":1595047416000,"title":"每日一问 | Android P 上，需要配置 network_security_config ，才能抓包，正确吗？","userId":68228,"visible":0,"zan":0},{"author":"","chapterId":466,"chapterName":"Gson","courseId":13,"desc":"","envelopePic":"","id":141715,"link":"https://juejin.im/post/5bdf159251882516e246ad75","niceDate":"2020-07-02 15:37","origin":"","originId":14120,"publishTime":1593675461000,"title":"新一代Json解析库Moshi使用及原理解析","userId":68228,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":2}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"
         *
         *在 Android 7.0了，引入了「Network Security Configuration」，当时默认配置为信任系统内置证书以及用户安装证书，并且允许访问 HTTP 请求。<\/p>\r\n
         *
         *而在 Android 9.0上，默认只信任系统证书了，并且默认不再允许访问 HTTP 请求，所以在很多适配P文章中，因为无法访问 HTTP 了，无法抓包了，所以我们要引入如下配置：<\/p>\r\n<pre>`<base-config> \r\n    <trust-anchors>\r\n        <certificates src="system" />\r\n                 <certificates src="user" /> // 信任用户安装证书\r\n    </trust-anchors>\r\n</base-config>\r\n<\/code><\/pre>`</pre>
         *
         *问题来了，今天在一个技术群，有人说：<\/p>\r\n
         *
         ***高版本的(AndroidP )，需要配置 android:networkSecurityConfig=&quot;@xml/network_security_config&quot;(上面的配置) ，才能抓包<\/strong><\/p>\r\n\r\n 1. 这个结论一定是正确的吗？<\/li>\r\n 1. 我在Android P以及以上的设备中不配置networkSecurityConfig，就一定无法抓包吗？<\/li>\r\n<\/ol>\r\n**
         *
         *其实之前问过类似的问题，上次没人回答，这次细化到具体场景了。<\/p>","envelopePic":"","id":144799,"link":"https://www.wanandroid.com/wenda/show/13949","niceDate":"2020-07-18 12:43","origin":"","originId":13949,"publishTime":1595047416000,"title":"每日一问 | Android P 上，需要配置 network_security_config ，才能抓包，正确吗？","userId":68228,"visible":0,"zan":0},{"author":"","chapterId":466,"chapterName":"Gson","courseId":13,"desc":"","envelopePic":"","id":141715,"link":"https://juejin.im/post/5bdf159251882516e246ad75","niceDate":"2020-07-02 15:37","origin":"","originId":14120,"publishTime":1593675461000,"title":"新一代Json解析库Moshi使用及原理解析","userId":68228,"visible":0,"zan":0}]
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
             * author : xiaoyang
             * chapterId : 440
             * chapterName : 官方
             * courseId : 13
             * desc :
             *
             *在 Android 7.0了，引入了「Network Security Configuration」，当时默认配置为信任系统内置证书以及用户安装证书，并且允许访问 HTTP 请求。
             *
             * 而在 Android 9.0上，默认只信任系统证书了，并且默认不再允许访问 HTTP 请求，所以在很多适配P文章中，因为无法访问 HTTP 了，无法抓包了，所以我们要引入如下配置：
             * <pre>`<base-config>
             * <trust-anchors>
             * <certificates src="system" />
             * <certificates src="user" /> // 信任用户安装证书
             * </trust-anchors>
             * </base-config>
            `</pre> *
             * 问题来了，今天在一个技术群，有人说：
             *
             * **高版本的(AndroidP )，需要配置 android:networkSecurityConfig=&quot;@xml/network_security_config&quot;(上面的配置) ，才能抓包**
             *
             *  1. 这个结论一定是正确的吗？
             *  1. 我在Android P以及以上的设备中不配置networkSecurityConfig，就一定无法抓包吗？
             *
             *
             * 其实之前问过类似的问题，上次没人回答，这次细化到具体场景了。
             * envelopePic :
             * id : 144799
             * link : https://www.wanandroid.com/wenda/show/13949
             * niceDate : 2020-07-18 12:43
             * origin :
             * originId : 13949
             * publishTime : 1595047416000
             * title : 每日一问 | Android P 上，需要配置 network_security_config ，才能抓包，正确吗？
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
}