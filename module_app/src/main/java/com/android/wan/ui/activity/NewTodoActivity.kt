package com.android.wan.ui.activity

import android.os.Build
import android.text.TextUtils
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import com.android.wan.R
import com.android.wan.model.entity.NewTodoEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.view.LoadingUtil
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.dq.ui.base.BaseActivity
import com.dq.util.DateUtil
import com.dq.util.EdittorUtil
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import kotlinx.android.synthetic.main.activity_new_todo.*
import kotlinx.android.synthetic.main.title_bar_base.*
import java.text.SimpleDateFormat

class NewTodoActivity : BaseActivity() {

    var apiModel: ApiModel? = null
    var isPriority = 2
    var isType = 1

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_new_todo
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
        tvTitle.text = "新增"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { addTodo() }
        tvTime.text = DateUtil.getDate(DateUtil.y_m_d)
        tvTime.setOnClickListener {
            EdittorUtil.hideInput(this)
            TimePickerBuilder(this,
                OnTimeSelectListener { date, v -> //选中事件回调
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    val str = formatter.format(date)
                    tvTime.text = str
                })
                .setType(booleanArrayOf(true, true, true, true, true, true)) // 默认全部显示
                .setCancelText("取消") //取消按钮文字
                .setSubmitText("确定") //确认按钮文字
                .setTitleSize(20) //标题文字大小
                .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true) //是否循环滚动
                .setTitleColor(resources.getColor(R.color.Color_4184F2)) //标题文字颜色
                .setSubmitColor(resources.getColor(R.color.Color_4184F2)) //确定按钮文字颜色
                .setCancelColor(resources.getColor(R.color.Color_4184F2)) //取消按钮文字颜色
                .setTitleBgColor(resources.getColor(R.color.white)) //标题背景颜色 Night mode
                .setBgColor(resources.getColor(R.color.white)) //滚轮背景颜色 Night mode
                .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false) //是否显示为对话框样式
                .build().show()
        }
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radioButtonGeneral -> {
                        isPriority = 2
                    }
                    R.id.radioButtonImportance -> {
                        isPriority = 1
                    }
                }
            }
        })

        radioGroupType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> {
                    isType = 1
                }
                R.id.radioButton2 -> {
                    isType = 2
                }
                R.id.radioButton3 -> {
                    isType = 3
                }
            }
        }
    }

    private fun addTodo() {
        if (TextUtils.isEmpty(getEdTitle())) {
            ToastUtil.showShortToast(this, "请输入标题")
            return
        }
        if (TextUtils.isEmpty(getEdDetails())) {
            ToastUtil.showShortToast(this, "详情")
            return
        }
        EdittorUtil.hideInput(this)
        var map: MutableMap<String, String> = mutableMapOf()
        map["title"] = getEdTitle()
        map["content"] = getEdDetails()
        map["date"] = getTvTime()
        map["type"] = isType.toString()
        map["priority "] = isPriority.toString()
        apiModel!!.postTodo(map, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
                var todoEntity: NewTodoEntity =
                    JsonUtil.fromJson<NewTodoEntity>(response, NewTodoEntity()) as NewTodoEntity
                if (todoEntity.errorCode == 0) {
                    ToastUtil.showShortToast(this@NewTodoActivity, "添加成功")
                    finish()
                } else {
                    ToastUtil.showShortToast(this@NewTodoActivity, todoEntity.errorMsg)
                }
            }

            override fun onFinish() {
                LoadingUtil.dismissLoading()
            }

            override fun onError(error: String?) {
                ToastUtil.showShortToast(this@NewTodoActivity, "网络异常")
            }

            override fun onStart() {
                LoadingUtil.showLoading(this@NewTodoActivity, "正在添加")
            }
        })
    }

    private fun getEdTitle(): String {
        return edTodoTitle.text.toString().trim()
    }

    private fun getEdDetails(): String {
        return edTodoDetails.text.toString().trim()
    }

    private fun getTvTime(): String {
        return tvTime.text.toString().trim()
    }
}