package com.android.wan.ui.activity

import com.android.wan.R
import com.dq.ui.base.BaseActivity
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.title_bar_base.*

class TodoActivity : BaseActivity() {
    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_todo
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "工作"
        imgBack.setImageResource(R.drawable.icon_back_white)
        imgTitle.setImageResource(R.drawable.icon_todo_cat)
        imgBack.setOnClickListener { finish() }
        imgTitle.setOnClickListener {
            XPopup.Builder(this)
                .atView(imgTitle)
                .asAttachList(
                    arrayOf("新建", "工作", "学习", "生活"), null
                ) { position, text ->
                    when (position) {
                        0 -> {
                            startAct(this@TodoActivity, NewTodoActivity())
                        }
                        1 -> {

                        }
                        2 -> {

                        }
                        3 -> {

                        }
                    }
                }
                .show()
        }
    }
}