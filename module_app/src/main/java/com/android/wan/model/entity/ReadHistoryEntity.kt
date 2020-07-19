package com.android.wan.model.entity

import org.litepal.crud.LitePalSupport

class ReadHistoryEntity : LitePalSupport() {
    var id: Long = 0
    var title: String? = null
    var link: String? = null
}