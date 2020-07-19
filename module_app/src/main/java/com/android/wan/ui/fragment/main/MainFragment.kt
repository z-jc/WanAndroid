package com.android.wan.ui.fragment.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Vibrator
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
import com.android.wan.ui.activity.*
import com.android.wan.ui.adapter.MainAdapter
import com.android.wan.ui.fragment.home.HomeFragment
import com.android.wan.ui.fragment.project.ProjectFragment
import com.android.wan.ui.fragment.public.PublicFragment
import com.android.wan.ui.fragment.system.SystemFragment
import com.android.wan.ui.holder.GlideEngine
import com.android.wan.ui.view.LoadingUtil
import com.android.wan.util.BrowserUtil
import com.bumptech.glide.Glide
import com.dq.login.activity.LoginActivity
import com.dq.login.config.LoginConfig
import com.dq.login.model.entity.LoginHistoryEntity
import com.dq.ui.base.BaseFragment
import com.dq.util.DisplayUtil
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import kotlinx.android.synthetic.main.fragment_main.*
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.SupportFragment
import org.litepal.LitePal
import java.util.*

class MainFragment : BaseFragment() {

    private val fragments = arrayOfNulls<SupportFragment>(4)
    private var mainAdapter: MainAdapter? = null
    private var vibrator: Vibrator? = null
    private var apiModel: ApiModel? = null

    override fun getContentView(): Int? {
        return R.layout.fragment_main
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        vibrator = activity!!.getSystemService(SupportActivity.VIBRATOR_SERVICE) as Vibrator?
        imgMainBack.visibility = View.VISIBLE
        imgMainBack.setImageResource(R.drawable.icon_main_title_left)
        fragments[0] = HomeFragment.createFragment()
        fragments[1] = PublicFragment.createFragment()
        fragments[2] = SystemFragment.createFragment()
        fragments[3] = ProjectFragment.createFragment()
        loadMultipleRootFragment(
            R.id.main_fragment,
            0,
            fragments[0],
            fragments[1],
            fragments[2],
            fragments[3]
        )
        navigation.selectedItemId = R.id.main_1
        tvMainTitle.textSize = 16F
        imgMainBack.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        imgMenuTitle.setOnClickListener {
            ToastUtil.showShortToast(activity, "积分排行榜")
        }
        imgMenuHeader.setOnClickListener {
            if (!LoginConfig().getIsLogin()) {
                LoginActivity.start(activity as MainActivity)
            } else {
                EasyPhotos.createAlbum(this, true, GlideEngine.instance!!)
                    .setFileProviderAuthority(AppConstant.provider)
                    .start(object : SelectCallback() {
                        override fun onResult(photos: ArrayList<Photo>?, isOriginal: Boolean) {
                            if (getUserEntity() == null || getUserEntity().size == 0) {
                            } else {
                                val loginHistoryEntity = getUserEntity()[0]
                                loginHistoryEntity.userPhoto = photos!!.get(0).path
                                loginHistoryEntity.save()
                            }
                            onResume()
                        }
                    })
            }
        }

        imgMainTitle.setOnClickListener {
            startAct(activity, SearchActivity())
        }

        var lp: DrawerLayout.LayoutParams = layoutMenu.layoutParams as DrawerLayout.LayoutParams
        lp.width = DisplayUtil.getScreenWidth(activity) / 20 * 13
        layoutMenu.requestLayout()

        mainAdapter = MainAdapter()
        recyclerViewMenu.layoutManager = LinearLayoutManager(activity)
        recyclerViewMenu.adapter = mainAdapter

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
                }
                return false
            }
        })

        mainAdapter!!.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    if (LoginConfig().getIsLogin()) {
                        startAct(activity, MyPointsActivity())
                    } else {
                        LoginActivity.start(activity!!)
                    }
                }
                1 -> return@setOnItemClickListener
                2 -> {
                    if (LoginConfig().getIsLogin()) {
                        startAct(activity, ShareMyActivity())
                    } else {
                        LoginActivity.start(activity!!)
                    }
                }
                3 -> {
                    startAct(activity, ReadHistoryActivity())
                }
                4 -> {
                    if (LoginConfig().getIsLogin()) {
                        startAct(activity, TodoActivity())
                    } else {
                        LoginActivity.start(activity!!)
                    }
                }
                5 -> startQrCode(QrConfig.TYPE_ONCE)
                6 -> return@setOnItemClickListener
                7 -> logout()
            }
        }
        imgMenuTitle.setOnClickListener {
            if (LoginConfig().getIsLogin()) {
                startAct(activity, IntegralRankingActivity())
            } else {
                LoginActivity.start(activity!!)
            }
        }
        onResume()
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
                activity
            ) { code, result ->
                vibrator!!.vibrate(30)
                BrowserUtil.startLocal(activity as MainActivity, result)
            }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        if (mainAdapter != null) {
            mainAdapter!!.setList(AppDataSourse.getMainList())
        }
        tvMenuRank.text = "等级" + LoginConfig().getUserLevel() + "  排名" + LoginConfig().getUserRank()
        if (LoginConfig().getIsLogin()) {
            tvMenuUser.text = LoginConfig().getUserName()

            if (getUserEntity() == null || getUserEntity().size == 0) {
                Glide.with(this)
                    .load(R.mipmap.ic_default_avatar)
                    .into(imgMenuHeader)
            } else {
                Glide.with(this)
                    .load(getUserEntity()[0].userPhoto)
                    .error(R.mipmap.ic_default_avatar)
                    .placeholder(R.mipmap.ic_default_avatar)
                    .into(imgMenuHeader)
            }
        } else {
            tvMenuUser.text = "去登陆"
            Glide.with(this)
                .load(R.mipmap.ic_default_avatar)
                .into(imgMenuHeader)
        }
    }

    fun getUserEntity(): MutableList<LoginHistoryEntity> {
        var historyEntity: MutableList<LoginHistoryEntity> =
            LitePal.where("userName=?", LoginConfig().getUserName())
                .find(LoginHistoryEntity::class.java)
        return historyEntity
    }

    /**
     * 退出登录
     */
    private fun logout() {
        apiModel!!.logOut(activity as MainActivity, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("退出成功$response")
                var logoutEntity: LogoutEntity =
                    JsonUtil.fromJson<LogoutEntity>(response, LogoutEntity()) as LogoutEntity
                if (logoutEntity.errorCode == 0) {
                    LoginConfig().clear()
                    onResume()
                } else {
                    ToastUtil.showShortToast(activity, logoutEntity.errorMsg)
                }
            }

            override fun onFinish() {
                LoadingUtil.dismissLoading()
            }

            override fun onError(error: String?) {
                ToastUtil.showShortToast(activity, "网络异常")
            }

            override fun onStart() {
                LoadingUtil.showLoading(activity, "正在退出...")
            }
        })
    }

    private var nowPosition = 0

    @SuppressLint("SetTextI18n")
    private fun selectFragment(index: Int) {
        when (index) {
            0 -> tvMainTitle.text = "玩Android"
            1 -> tvMainTitle.text = resources.getText(R.string.text_main_bottom_the_public)
            2 -> tvMainTitle.text = resources.getText(R.string.text_main_bottom_the_system)
            3 -> tvMainTitle.text = resources.getText(R.string.text_main_bottom_the_project)
        }
        if (nowPosition != index) {
            val prePosition = nowPosition
            nowPosition = index
            showHideFragment(fragments[nowPosition], fragments[prePosition])
        }
    }

    companion object {
        fun createFragment(): MainFragment {
            return MainFragment()
        }
    }
}