package com.android.wan.model.entity

import com.dq.util.http.BaseEntity

class ShareArticleEntity : BaseEntity() {
    /**
     * data : null
     * errorCode : -1
     * errorMsg : 链接必须以 http:// 或者 https:// 开头！
     */
    var data: Any? = null
    var errorCode = 0
    var errorMsg: String? = null

}