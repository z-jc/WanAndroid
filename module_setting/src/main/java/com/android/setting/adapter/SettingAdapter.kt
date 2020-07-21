package com.android.setting.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import com.android.setting.R
import com.android.setting.config.SettingConfig
import com.android.setting.entity.SettingEntity
import com.android.setting.ui.AboutActivity
import com.android.setting.ui.FeedBackActivity
import com.android.setting.ui.UserServiceAgreementActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dq.ui.dialog.DialogCustom
import com.dq.ui.dialog.DialogEdit
import com.dq.util.CleanDataUtils
import com.dq.util.ILog
import com.dq.util.SystemUtil
import com.dq.util.ToastUtil
import com.lxj.xpopup.XPopup


/**
 * FileName: SettingAdapter
 * Author: admin
 * Date: 2020/7/20 17:06
 * Description:
 */
class SettingAdapter() :
    BaseQuickAdapter<SettingEntity, BaseViewHolder>(R.layout.item_setting, null) {
    override fun convert(holder: BaseViewHolder, item: SettingEntity) {
        holder.setText(R.id.tvItemName, item.itemName)
        var tvItemValue = holder.getView<TextView>(R.id.tvItemContent)
        tvItemValue.visibility = View.INVISIBLE
        when (holder.layoutPosition) {
            0 -> {
                tvItemValue.visibility = View.VISIBLE
                tvItemValue.text = getName(SettingConfig.getAdapterAnim())
            }
            2 -> {
                tvItemValue.visibility = View.VISIBLE
                tvItemValue.text = CleanDataUtils.getTotalCacheSize(holder.itemView.context)
            }
            3 -> {
                tvItemValue.visibility = View.VISIBLE
                tvItemValue.text = SystemUtil.getVersionName(holder.itemView.context)
            }
        }

        holder.itemView.setOnClickListener {
            when (holder.layoutPosition) {
                0 -> {
                    XPopup.Builder(context)
                        .asBottomList(
                            "选择动画",
                            arrayOf("无", "渐显", "缩放", "底部滑入", "左侧滑入", "右侧滑入")
                        ) { position, _ ->
                            SettingConfig.setAdapterAnim(position)
                            notifyItemChanged(holder.layoutPosition)
                        }
                        .show()
                }
                1 -> {
                    DialogEdit(holder.itemView.context as Activity)
                        .setHint("请输入内容")
                        .setActionLister(object : DialogEdit.ActionLister {
                            override fun onSuccess(dialog: Dialog?, value: String) {
                                dialog!!.dismiss()
                                SettingConfig.setFootView(value)
                            }
                        }).show()
                }
                2 -> {
                    DialogCustom(holder.itemView.context as Activity)
                        .setLeft("否")
                        .setRight("是")
                        .setMsg("确认清空吗？")
                        .setActionLister(object : DialogCustom.ActionLister {
                            override fun onLeftClick(dialog: Dialog?) {
                                dialog!!.dismiss()
                            }

                            override fun onRightClick(dialog: Dialog?) {
                                dialog!!.dismiss()
                                CleanDataUtils.clearAllCache(holder.itemView.context)
                                notifyItemChanged(holder.layoutPosition)
                            }
                        }).show()
                }
                3 -> {
                    ToastUtil.showShortToast(holder.itemView.context, "当前已是最新版本")
                }
                4 -> {
                    FeedBackActivity.startAct(holder.itemView.context)
                }
                5 -> {
                    AboutActivity.startAct(
                        holder.itemView.context as Activity,
                        SystemUtil.getVersionName(holder.itemView.context),
                        SystemUtil.getAppName(holder.itemView.context),
                        R.drawable.icon_logo
                    )
                }
                6 -> {
                    UserServiceAgreementActivity.startAct(
                        holder.itemView.context as Activity,
                        UserServiceAgreementActivity.TYPE_PRIVACY
                    )
                }
                7 -> {
                    UserServiceAgreementActivity.startAct(
                        holder.itemView.context as Activity,
                        UserServiceAgreementActivity.TYPE_USER
                    )
                }
                8 -> {
                    startLocal(holder.itemView.context as Activity, "https://blog.csdn.net/qq_32136827")
                }
                9 -> {
                    startLocal(holder.itemView.context as Activity, "https://gitee.com/wasadsdfa/WanAndroid")
                }
            }
        }
    }

    /**
     * 手机自带浏览器打开
     */
    private fun startLocal(a: Activity, url: String?) {
        ILog.e("跳转到浏览器:${a.javaClass.simpleName}")
        val uri = Uri.parse(url)
        val intent =
            Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        a.startActivity(intent)
    }

    private fun getName(anim: Int): String {
        var name: String = when (anim) {
            0 -> "无"
            1 -> "渐显"
            2 -> "缩放"
            3 -> "底部滑入"
            4 -> "左侧滑入"
            5 -> "右侧滑入"
            else -> {
                "无"
            }
        }
        return name
    }
}