package com.android.wan.model.entity

import org.litepal.crud.LitePalSupport

class SearchHistoryEntity : LitePalSupport() {
    var id: Long = 0
    var query: String? = null
}