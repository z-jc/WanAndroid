package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

/**
 * FileName: IntegralRankingEnity
 * Author: admin
 * Date: 2020/7/2 14:42
 * Description:
 */
class IntegralRankingEnity : BaseEntity() {
    /**
     * data : {"curPage":2,"datas":[{"coinCount":7902,"level":80,"rank":"31","userId":30114,"username":"E**an_Jin"},{"coinCount":7849,"level":79,"rank":"32","userId":3825,"username":"请**娃哈哈"},{"coinCount":7804,"level":79,"rank":"33","userId":7075,"username":"c**ndroid"},{"coinCount":7704,"level":78,"rank":"34","userId":7541,"username":"l**64301766"},{"coinCount":7623,"level":77,"rank":"35","userId":23244,"username":"a**ian"},{"coinCount":7602,"level":77,"rank":"36","userId":6095,"username":"W**derfulMtf"},{"coinCount":7537,"level":76,"rank":"37","userId":9180,"username":"c**2018"},{"coinCount":7513,"level":76,"rank":"38","userId":29103,"username":"9**889276@qq.com"},{"coinCount":7393,"level":74,"rank":"39","userId":29390,"username":"L**ing"},{"coinCount":7387,"level":74,"rank":"40","userId":1580,"username":"k**od21"},{"coinCount":7387,"level":74,"rank":"41","userId":30006,"username":"星**tar"},{"coinCount":7308,"level":74,"rank":"42","userId":14839,"username":"x**y_sjc"},{"coinCount":7218,"level":73,"rank":"43","userId":863,"username":"m**qitian"},{"coinCount":7188,"level":72,"rank":"44","userId":20567,"username":"v**0123"},{"coinCount":7053,"level":71,"rank":"45","userId":29398,"username":"l**bwstory"},{"coinCount":6988,"level":70,"rank":"46","userId":4662,"username":"1**71599512"},{"coinCount":6983,"level":70,"rank":"47","userId":28457,"username":"w**dla"},{"coinCount":6970,"level":70,"rank":"48","userId":9778,"username":"1**11985351"},{"coinCount":6945,"level":70,"rank":"49","userId":9296,"username":"j**123456"},{"coinCount":6892,"level":69,"rank":"50","userId":5899,"username":"贝**的黑夜"},{"coinCount":6843,"level":69,"rank":"51","userId":7365,"username":"l**kad"},{"coinCount":6820,"level":69,"rank":"52","userId":16064,"username":"1**30703051"},{"coinCount":6777,"level":68,"rank":"53","userId":14854,"username":"z**23456"},{"coinCount":6757,"level":68,"rank":"54","userId":29030,"username":"s**世界"},{"coinCount":6738,"level":68,"rank":"55","userId":21873,"username":"L**gSh1z"},{"coinCount":6720,"level":68,"rank":"56","userId":6142,"username":"c**huah"},{"coinCount":6644,"level":67,"rank":"57","userId":28454,"username":"c**xzxzc"},{"coinCount":6534,"level":66,"rank":"58","userId":2657,"username":"a**111993@163.com"},{"coinCount":6420,"level":65,"rank":"59","userId":20375,"username":"z**hailong"},{"coinCount":6380,"level":64,"rank":"60","userId":26522,"username":"z**1997"}],"offset":30,"over":false,"pageCount":1432,"size":30,"total":42946}
     * errorCode : 0
     * errorMsg :
     */
    var data: DataBean? = null
    var errorCode = 0
    var errorMsg: String? = null

    class DataBean {
        /**
         * curPage : 2
         * datas : [{"coinCount":7902,"level":80,"rank":"31","userId":30114,"username":"E**an_Jin"},{"coinCount":7849,"level":79,"rank":"32","userId":3825,"username":"请**娃哈哈"},{"coinCount":7804,"level":79,"rank":"33","userId":7075,"username":"c**ndroid"},{"coinCount":7704,"level":78,"rank":"34","userId":7541,"username":"l**64301766"},{"coinCount":7623,"level":77,"rank":"35","userId":23244,"username":"a**ian"},{"coinCount":7602,"level":77,"rank":"36","userId":6095,"username":"W**derfulMtf"},{"coinCount":7537,"level":76,"rank":"37","userId":9180,"username":"c**2018"},{"coinCount":7513,"level":76,"rank":"38","userId":29103,"username":"9**889276@qq.com"},{"coinCount":7393,"level":74,"rank":"39","userId":29390,"username":"L**ing"},{"coinCount":7387,"level":74,"rank":"40","userId":1580,"username":"k**od21"},{"coinCount":7387,"level":74,"rank":"41","userId":30006,"username":"星**tar"},{"coinCount":7308,"level":74,"rank":"42","userId":14839,"username":"x**y_sjc"},{"coinCount":7218,"level":73,"rank":"43","userId":863,"username":"m**qitian"},{"coinCount":7188,"level":72,"rank":"44","userId":20567,"username":"v**0123"},{"coinCount":7053,"level":71,"rank":"45","userId":29398,"username":"l**bwstory"},{"coinCount":6988,"level":70,"rank":"46","userId":4662,"username":"1**71599512"},{"coinCount":6983,"level":70,"rank":"47","userId":28457,"username":"w**dla"},{"coinCount":6970,"level":70,"rank":"48","userId":9778,"username":"1**11985351"},{"coinCount":6945,"level":70,"rank":"49","userId":9296,"username":"j**123456"},{"coinCount":6892,"level":69,"rank":"50","userId":5899,"username":"贝**的黑夜"},{"coinCount":6843,"level":69,"rank":"51","userId":7365,"username":"l**kad"},{"coinCount":6820,"level":69,"rank":"52","userId":16064,"username":"1**30703051"},{"coinCount":6777,"level":68,"rank":"53","userId":14854,"username":"z**23456"},{"coinCount":6757,"level":68,"rank":"54","userId":29030,"username":"s**世界"},{"coinCount":6738,"level":68,"rank":"55","userId":21873,"username":"L**gSh1z"},{"coinCount":6720,"level":68,"rank":"56","userId":6142,"username":"c**huah"},{"coinCount":6644,"level":67,"rank":"57","userId":28454,"username":"c**xzxzc"},{"coinCount":6534,"level":66,"rank":"58","userId":2657,"username":"a**111993@163.com"},{"coinCount":6420,"level":65,"rank":"59","userId":20375,"username":"z**hailong"},{"coinCount":6380,"level":64,"rank":"60","userId":26522,"username":"z**1997"}]
         * offset : 30
         * over : false
         * pageCount : 1432
         * size : 30
         * total : 42946
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
             * coinCount : 7902
             * level : 80
             * rank : 31
             * userId : 30114
             * username : E**an_Jin
             */
            var coinCount = 0
            var level = 0
            var rank: String? = null
            var userId = 0
            var username: String? = null

        }
    }
}