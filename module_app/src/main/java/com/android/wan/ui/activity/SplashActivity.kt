package com.android.wan.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.wan.R
import com.dq.ui.base.BaseActivity
import com.qw.soul.permission.SoulPermission
import com.qw.soul.permission.bean.Permission
import com.qw.soul.permission.bean.Permissions
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    val permission = Permissions.build(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun initView() {
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        if (Build.VERSION.SDK_INT >= 23) {
            SoulPermission.getInstance().checkAndRequestPermissions(
                permission,
                object : CheckRequestPermissionsListener {
                    override fun onAllPermissionOk(allPermissions: Array<Permission>) {
                        startAct()
                    }

                    override fun onPermissionDenied(refusedPermissions: Array<Permission>) {
                        startAct()
                    }
                })
        } else {
            startAct()
        }
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_splash
    }

    private fun startAct() {
        var animation : Animation = AnimationUtils.loadAnimation(this,R.anim.splash_alpha);
        imgSplash.startAnimation(animation);//开始动画
        animation!!.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}