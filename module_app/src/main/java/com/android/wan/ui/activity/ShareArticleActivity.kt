package com.android.wan.ui.activity

import android.text.TextUtils
import com.android.wan.R
import com.android.wan.model.entity.ShareArticleEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.BrowserUtil
import com.dq.ui.base.BaseActivity
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.activity_share_article.*
import kotlinx.android.synthetic.main.title_bar_base.*

/**
 * FileName: ShareArticleActivity
 * Author: admin
 * Date: 2020/7/2 14:08
 * Description:
 */
class ShareArticleActivity : BaseActivity() {

    private var apiModel: ApiModel? = null
    private var stringBuilder: StringBuilder = StringBuilder()

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_share_article
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        tvTitle.text = "分享文章"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
        stringBuilder.append("1、只要是任何好友都可以分享哈，并不一定要是原创！投递的文章会进入广场tab；\n")
        stringBuilder.append("2、CSDN，掘金，简书等官网博客站点会直接通过，不需要审核；\n")
        stringBuilder.append("3、其他个人站点会进入审核阶段，不要投递任何无效链接，否则可能会对你的账号产生一定影响；\n")
        stringBuilder.append("4、如果你发现错误，可以提交日志，让我们一起使网站变的更好；\n")
        stringBuilder.append("5、由于本站为个人开发与维护，会尽力保证24小时内审核，当然有可能哪天太累，会延期，请保持佛系...")
        tvNotice.text = stringBuilder.toString()

        btnShare.setOnClickListener {
            if (TextUtils.isEmpty(getEdTitle())) {
                ToastUtil.showShortToast(this@ShareArticleActivity, "请输入文章标题")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(getEdLink())) {
                ToastUtil.showShortToast(this@ShareArticleActivity, "请输入文章链接")
                return@setOnClickListener
            }
            postLink()
        }

        tvOpenLink.setOnClickListener {
            if (!TextUtils.isEmpty(getEdLink())) {
                BrowserUtil.startLocal(this@ShareArticleActivity, getEdLink())
            }
        }
    }

    private fun postLink() {
        val map: MutableMap<String, String> = mutableMapOf()
        map["title"] = getEdTitle()
        map["link"] = getEdLink()
        apiModel!!.postShareArticle(
            map,
            this@ShareArticleActivity,
            object : RxhttpUtil.RxHttpCallBack {
                override fun onSuccess(response: String?) {
                    ILog.e("请求成功:$response")
                    val shareArticleEntity: ShareArticleEntity =
                        JsonUtil.fromJson<ShareArticleEntity>(
                            response,
                            ShareArticleEntity()
                        ) as ShareArticleEntity
                    if (shareArticleEntity.errorCode == 0) {
                        ToastUtil.showShortToast(this@ShareArticleActivity, "分享成功")
                        finish()
                    } else {
                        ToastUtil.showShortToast(
                            this@ShareArticleActivity,
                            shareArticleEntity.errorMsg
                        )
                    }
                }

                override fun onFinish() {
                    LoadingUtil.dismissLoading()
                }

                override fun onError(error: String?) {
                    ToastUtil.showShortToast(this@ShareArticleActivity, "网络异常")
                }

                override fun onStart() {
                    LoadingUtil.showLoading(this@ShareArticleActivity, "正在分享...")
                }
            })
    }

    private fun getEdTitle(): String {
        return edTitle.text.toString().trim()
    }

    private fun getEdLink(): String {
        return edLink.text.toString().trim()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoadingUtil.dismissLoading()
    }
}