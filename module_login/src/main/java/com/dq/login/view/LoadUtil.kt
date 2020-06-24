package com.dq.login.view

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.dq.ui.dialog.DialogLoading
import com.dq.ui.dialog.DialogPolicy
import com.dq.ui.dialog.DialogPolicy.OnPolicyListener
import com.dq.ui.dialog.DialogVipOpen
import com.dq.ui.dialog.DialogVipOpen.OnClickLister

object LoadUtil {
    private var animationDrawable: AnimationDrawable? = null

    /**
     * 显示loading
     *
     * @param rlLoading
     * @param imageView
     */
    fun showLoading(rlLoading: RelativeLayout, imageView: ImageView) {
        rlLoading.visibility = View.VISIBLE
        animationDrawable = imageView.background as AnimationDrawable
        if (!animationDrawable!!.isRunning) {
            animationDrawable!!.start()
        }
    }

    /**
     * 停止帧动画
     *
     * @param rlLoading
     */
    fun dismissLoading(rlLoading: RelativeLayout) {
        rlLoading.visibility = View.GONE
        if (animationDrawable != null) {
            if (animationDrawable!!.isRunning) {
                animationDrawable!!.stop()
            }
        }
    }

    private var dialogPolicy: DialogPolicy? = null
    fun showDialogPolicy(activity: Activity?, policyListener: OnPolicyListener?) {
        if (dialogPolicy != null) {
            dialogPolicy!!.dismiss()
            dialogPolicy = null
        }
        dialogPolicy = DialogPolicy(activity!!, policyListener)
        dialogPolicy!!.show()
    }

    fun dismissDialogPolicy() {
        if (dialogPolicy != null) {
            dialogPolicy!!.dismiss()
            dialogPolicy = null
        }
    }

    private var dialogLoading: DialogLoading? = null
    fun showLoading(activity: Activity?, title: String?) {
        if (dialogLoading != null) {
            dialogLoading!!.dismiss()
            dialogLoading = null
        }
        dialogLoading = DialogLoading(activity!!).setContent(title)
        dialogLoading!!.show()
    }

    fun dismissLoading() {
        if (dialogLoading != null) {
            dialogLoading!!.dismiss()
            dialogLoading = null
        }
    }

    private var dialogVipOpen: DialogVipOpen? = null
    fun showVipOpenDialog(
        activity: Activity?,
        resourcesId: Int,
        btnText: String?,
        clickLister: OnClickLister?
    ) {
        if (dialogVipOpen != null) {
            dialogVipOpen!!.dismiss()
            dialogVipOpen = null
        }
        dialogVipOpen = DialogVipOpen(activity!!, clickLister, resourcesId, btnText!!)
        dialogVipOpen!!.show()
    }

    fun dismissDialogVipOpen() {
        if (dialogVipOpen != null) {
            dialogVipOpen!!.dismiss()
            dialogVipOpen = null
        }
    }
}