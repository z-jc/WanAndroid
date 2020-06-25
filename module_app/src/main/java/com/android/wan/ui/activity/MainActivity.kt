package com.android.wan.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bertsir.zbar.QrConfig
import cn.bertsir.zbar.QrManager
import com.android.wan.R
import com.android.wan.config.AppConstant
import com.android.wan.config.AppDataSourse
import com.android.wan.model.entity.LogoutEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.MenuAdapter
import com.android.wan.ui.fragment.*
import com.android.wan.ui.holder.GlideEngine
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.BrowserUtil
import com.bumptech.glide.Glide
import com.dq.login.activity.LoginActivity
import com.dq.login.config.LoginConfig
import com.dq.ui.base.BaseActivity
import com.dq.ui.dialog.DialogCustom
import com.dq.ui.dialog.DialogCustom.ActionLister
import com.dq.util.DisplayUtil
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_bar_base.imgBack
import kotlinx.android.synthetic.main.title_bar_base.imgTitle
import kotlinx.android.synthetic.main.title_bar_base.tvTitle
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

/**
 * FileName: MainActivity
 * Author: admin
 * Date: 2020/6/17 19:35
 * Description:
 */
class MainActivity : BaseActivity() {

    private val fragments = arrayOfNulls<SupportFragment>(5)
    private var menuAdapter: MenuAdapter? = null
    private var vibrator: Vibrator? = null
    private var apiModel: ApiModel? = null

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator?
        imgBack.visibility = View.VISIBLE
        imgBack.setImageResource(R.drawable.icon_main_title_left)
        fragments[0] = HomeFragment.createFragment()
        fragments[1] = SquareFragment.createFragment()
        fragments[2] = PublicFragment.createFragment()
        fragments[3] = SystemFragment.createFragment()
        fragments[4] = ProjectFragment.createFramgemt()
        loadMultipleRootFragment(
            R.id.main_fragment,
            0,
            fragments[0],
            fragments[1],
            fragments[2],
            fragments[3],
            fragments[4]
        )
        navigation.selectedItemId = R.id.main_1
        tvTitle.textSize = 16F
        imgBack.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        imgMenuTitle.setOnClickListener {
            ToastUtil.showShortToast(this, "积分排行榜")
        }
        imgMenuHeader.setOnClickListener {
            if (!LoginConfig().getIsLogin()) {
                LoginActivity.start(this@MainActivity)
            } else {
                EasyPhotos.createAlbum(this, true, GlideEngine.instance!!)
                    .setFileProviderAuthority(AppConstant.provider)
                    .start(object : SelectCallback(){
                        override fun onResult(photos: ArrayList<Photo>?, isOriginal: Boolean) {
                            LoginConfig().setUserHeader(photos!![0].path)
                            onResume()
                        }
                    })
            }
        }
        imgTitle.setOnClickListener {
            ToastUtil.showShortToast(this, "去搜索")
        }

        var lp: DrawerLayout.LayoutParams = layoutMenu.layoutParams as DrawerLayout.LayoutParams
        lp.width = DisplayUtil.getScreenWidth(this) / 20 * 13
        layoutMenu.requestLayout()

        menuAdapter = MenuAdapter()
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.adapter = menuAdapter

        navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.main_1 -> {
                        selectFragment(0)
                        return true
                    }
                    R.id.main_2 -> {
                        selectFragment(1)
                        return true
                    }
                    R.id.main_3 -> {
                        selectFragment(2)
                        return true
                    }
                    R.id.main_4 -> {
                        selectFragment(3)
                        return true
                    }
                    R.id.main_5 -> {
                        selectFragment(4)
                        return true
                    }
                }
                return false
            }
        })

        menuAdapter!!.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> return@setOnItemClickListener
                1 -> return@setOnItemClickListener
                2 -> return@setOnItemClickListener
                3 -> return@setOnItemClickListener
                4 -> return@setOnItemClickListener
                5 -> startQrCode(QrConfig.TYPE_ONCE)
                6 -> return@setOnItemClickListener
                7 -> logout()
            }
        }
    }

    /**
     * 扫码界面
     */
    private fun startQrCode(code: Int) {
        val scan_type = 0
        val scan_view_type = 0
        val qrConfig = QrConfig.Builder()
            .setDesText("开始扫码") //扫描框下文字
            .setShowDes(true) //是否显示扫描框下面文字
            .setShowLight(true) //显示手电筒按钮
            .setShowTitle(false) //显示Title
            .setShowAlbum(true) //显示从相册选择按钮
            .setCornerColor(Color.parseColor("#4184F2")) //设置扫描框颜色
            .setLineColor(Color.parseColor("#4184F2")) //设置扫描线颜色
            .setLineSpeed(QrConfig.LINE_FAST) //设置扫描线速度
            .setScanType(scan_type) //设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
            .setScanViewType(scan_view_type) //设置扫描框类型（二维码还是条形码，默认为二维码）
            .setCustombarcodeformat(QrConfig.BARCODE_EAN13) //此项只有在扫码类型为TYPE_CUSTOM时才有效
            .setPlaySound(false) //是否扫描成功后bi~的声音
            .setIsOnlyCenter(true) //是否只识别框中内容(默认为全屏识别)
            .setTitleText("扫描二维码") //设置Tilte文字
            .setIdentify_type(code) //1:连续扫码  2:单次扫码
            .setTitleBackgroudColor(Color.parseColor("#262020")) //设置状态栏颜色
            .setTitleTextColor(Color.WHITE) //设置Title文字颜色
            .create()
        QrManager.getInstance().init(qrConfig)
            .startScan(
                this@MainActivity
            ) { code, result ->
                Log.e(TAG, "result:$result,code:$code")
                vibrator!!.vibrate(30)
                BrowserUtil.startLocal(this@MainActivity, result)
            }
    }

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_main
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        menuAdapter!!.setList(AppDataSourse.getMenuList())
        tvMenuRank.text = "等级" + LoginConfig().getUserLevel() + "  排名" + LoginConfig().getUserRank()
        if (LoginConfig().getIsLogin()) {
            tvMenuUser.text = LoginConfig().getUserName()
            Glide.with(this)
                .load(LoginConfig().getUserHeader())
                .error(R.mipmap.ic_default_avatar)
                .placeholder(R.mipmap.ic_default_avatar)
                .into(imgMenuHeader)
        } else {
            tvMenuUser.text = "去登陆"
            Glide.with(this)
                .load(R.mipmap.ic_default_avatar)
                .into(imgMenuHeader)
        }
    }

    /**
     * 退出登录
     */
    private fun logout() {
        apiModel!!.logOut(this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("退出成功$response")
                var logoutEntity: LogoutEntity =
                    JsonUtil.fromJson<LogoutEntity>(response, LogoutEntity()) as LogoutEntity
                if (logoutEntity.errorCode == 0) {
                    LoginConfig().clear()
                    onResume()
                } else {
                    ToastUtil.showShortToast(this@MainActivity, logoutEntity.errorMsg)
                }
            }

            override fun onFinish() {
                LoadingUtil.dismissLoading()
            }

            override fun onError(error: String?) {
                ToastUtil.showShortToast(this@MainActivity, "网络异常")
            }

            override fun onStart() {
                LoadingUtil.showLoading(this@MainActivity, "正在退出...")
            }
        })
    }

    private var nowPosition = 0

    @SuppressLint("SetTextI18n")
    private fun selectFragment(index: Int) {
        when (index) {
            0 -> tvTitle.text = "玩Android"
            1 -> tvTitle.text = resources.getText(R.string.text_main_bottom_square)
            2 -> tvTitle.text = resources.getText(R.string.text_main_bottom_the_public)
            3 -> tvTitle.text = resources.getText(R.string.text_main_bottom_the_system)
            4 -> tvTitle.text = resources.getText(R.string.text_main_bottom_the_project)
        }
        if (nowPosition != index) {
            val prePosition = nowPosition
            nowPosition = index
            showHideFragment(fragments[nowPosition], fragments[prePosition])
        }
    }

    override fun onBackPressedSupport() {
        DialogCustom(this)
            .setMsg("确认退出吗？")
            .setActionLister(object : ActionLister {
                override fun onLeftClick(dialog: Dialog?) {
                    dialog!!.dismiss()
                }

                override fun onRightClick(dialog: Dialog?) {
                    dialog!!.dismiss()
                    finish()
                }
            }).show()
    }
}