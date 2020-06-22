package com.android.wan.model.entity

/**
 * Author : Z-JC
 * Date : 2020/1/12
 * Description :
 */
abstract class BaseEntity {
    /**
     * 请求成功标识
     */
    var result_ok = 0

    /**
     * 如果返回220，说明需要验证手机号，需要输入手机号再次查询
     */
    var result_sf = 220
}