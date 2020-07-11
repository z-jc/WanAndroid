package com.dq.login.model.entity

import org.litepal.crud.LitePalSupport

class LoginHistoryEntity : LitePalSupport() {
    var id: Long = 0
    var userName: String? = null
    var userPsw: String? = null
    var userPhoto: String? = null
}