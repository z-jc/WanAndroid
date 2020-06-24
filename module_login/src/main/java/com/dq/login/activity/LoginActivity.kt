package com.dq.login.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dq.login.R
import com.dq.login.adapter.LoginFragmentPagerAdapter
import com.dq.login.fragment.LoginFragment
import com.dq.login.fragment.RegisteredFragment
import com.dq.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * FileName: LoginActivity
 * Author: admin
 * Date: 2020/6/24 9:00
 * Description: LoginActivity
 */
class LoginActivity : BaseActivity() {

    private var mSet1: AnimatorSet? = null
    private var mSet2: AnimatorSet? = null
    private var isRunning = false
    private var mFragment: MutableList<Fragment> = mutableListOf()

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_login
    }

    override fun initView() {
        super.initView()
        mFragment.add(LoginFragment.createFragment())
        mFragment.add(RegisteredFragment.createFragment())

        val loginAdapter =
            LoginFragmentPagerAdapter(supportFragmentManager, mFragment)
        viewPager.adapter = loginAdapter
        imgClose.setOnClickListener { finish() }
    }

    override fun initData() {
        super.initData()
    }


    companion object {
        fun start(context: Context) {
            val intent =
                Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            if (context is Activity) {
                context.overridePendingTransition(
                    R.anim.swipeback_activity_open_bottom_in,
                    R.anim.swipeback_activity_open_top_out
                )
            }
        }
    }

    private fun startCircleAnim(target: View?): AnimatorSet? {
        if (target == null) {
            return null
        }
        val xy: FloatArray = calculateRandomXY()
        val set: AnimatorSet = createTranslationAnimator(target, xy[0], xy[1])
        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                if (isRunning) {
                    startCircleAnim(target)
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        set.start()
        return set
    }

    private val mMaxMoveDuration = 10000L
    private val mMaxMoveDistanceX = 200
    private val mMaxMoveDistanceY = 20

    private fun createTranslationAnimator(
        target: View,
        toX: Float,
        toY: Float
    ): AnimatorSet {
        val fromX = target.translationX
        val fromY = target.translationY
        val duration = calculateDuration(fromX, fromY, toX, toY)
        val animatorX =
            ObjectAnimator.ofFloat(target, "translationX", fromX, toX)
        animatorX.duration = duration
        val animatorY =
            ObjectAnimator.ofFloat(target, "translationY", fromY, toY)
        animatorY.duration = duration
        val set = AnimatorSet()
        set.playTogether(animatorX, animatorY)
        return set
    }

    override fun onStart() {
        isRunning = true
        mSet1 = startCircleAnim(iv_circle_1)
        mSet2 = startCircleAnim(iv_circle_2)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        isRunning = false
    }

    private val mRandom = Random()

    private fun calculateRandomXY(): FloatArray {
        val x = mRandom.nextInt(mMaxMoveDistanceX) - mMaxMoveDistanceX * 0.5f
        val y = mRandom.nextInt(mMaxMoveDistanceY) - mMaxMoveDistanceY * 0.5f
        return floatArrayOf(x, y)
    }

    private fun calculateDuration(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float
    ): Long {
        val distance = Math.abs(
            sqrt(
                Math.abs(x1 - x2).toDouble().pow(2.0) + abs(y1 - y2).toDouble().pow(2.0)
            )
        ).toFloat()
        val maxDistance = Math.abs(
            sqrt(
                mMaxMoveDistanceX.toDouble().pow(2.0) + Math.pow(mMaxMoveDistanceY.toDouble(), 2.0)
            )
        ).toFloat()
        val duration = (mMaxMoveDuration * (distance / maxDistance)).toLong()
        Log.i("calculateDuration", "distance=$distance, duration=$duration")
        return duration
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        lav.randomBlink()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.swipeback_activity_close_top_in,
            R.anim.swipeback_activity_close_bottom_out
        )
    }
}