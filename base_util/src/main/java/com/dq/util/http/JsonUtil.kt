package com.dq.util.http

import android.content.Context
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Author : Z-JC
 * Date : 2020/1/12
 * Description :
 */
object JsonUtil {

    /**
     * json串转实体类对象
     *
     * @param response
     * @param baseEntity
     * @return
     */
    fun <T> fromJson(response: String?, baseEntity: BaseEntity): BaseEntity {
        var baseEntity: BaseEntity = baseEntity
        baseEntity = Gson().fromJson(response, baseEntity.javaClass)
        return baseEntity
    }

    /**
     * 解析单个Key
     *
     * @param response
     * @param key
     * @return
     */
    fun parsingKey(response: String?, key: String?): String {
        var value = ""
        value = try {
            val jsonObject = JSONObject(response)
            jsonObject.getString(key)
        } catch (e: JSONException) {
            e.printStackTrace()
            ""
        }
        return value
    }

    /**
     * 解析单个Key
     *
     * @param response
     * @param key
     * @return
     */
    fun parsKey(response: String?, key: String?): Int {
        var code = -1
        try {
            val jsonObject = JSONObject(response)
            code = jsonObject.getInt(key)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return code
    }

    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    fun getJsonString(context: Context, fileName: String?): String {
        val stringBuilder = StringBuilder()
        //获得assets资源管理器
        val assetManager = context.assets
        //使用IO流读取json文件内容
        try {
            val bufferedReader = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName!!), "utf-8"
                )
            )
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}