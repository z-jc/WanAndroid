package com.android.wan.ui.activity

import android.graphics.Color
import android.view.MenuItem
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.wan.R
import com.android.wan.model.entity.TodoListEntity
import com.android.wan.model.model.ApiModel
import com.android.wan.model.model.ApiModelImpl
import com.android.wan.ui.adapter.TodoAdapter
import com.android.wan.util.RvAnimUtils
import com.dq.ui.base.BaseActivity
import com.dq.util.ILog
import com.dq.util.ToastUtil
import com.dq.util.http.JsonUtil
import com.dq.util.http.RxhttpUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lxj.xpopup.XPopup
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yanzhenjie.recyclerview.OnItemMenuClickListener
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.title_bar_base.*

class TodoActivity : BaseActivity(), OnLoadMoreListener, OnRefreshListener {

    var apiModel: ApiModel? = null
    var pageIndex: Int = 1
    var statu = 0 //1完成0未完成
    var type = 1//1工作2生活3娱乐
    var orderby = 1//完成日期顺序
    var isRefresh = false//false刷新 true加载
    var todoAdapter: TodoAdapter? = null

    override fun getContentView(): Int? {
        setTitleBackground(BG_WHITE)
        return R.layout.activity_todo
    }

    override fun initView() {
        super.initView()
        apiModel = ApiModelImpl()
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
                            type = 1
                            pageIndex = 1
                            todoAdapter!!.data.clear()
                            refreshLayout.autoRefresh()
                        }
                        2 -> {
                            type = 2
                            pageIndex = 1
                            todoAdapter!!.data.clear()
                            refreshLayout.autoRefresh()
                        }
                        3 -> {
                            type = 3
                            pageIndex = 1
                            todoAdapter!!.data.clear()
                            refreshLayout.autoRefresh()
                        }
                    }
                }
                .show()
        }
        navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.todo_1 -> {
                        statu = 0
                        pageIndex = 1
                        todoAdapter!!.data.clear()
                        refreshLayout.autoRefresh()
                        return true
                    }
                    R.id.todo_2 -> {
                        statu = 1
                        pageIndex = 1
                        todoAdapter!!.data.clear()
                        refreshLayout.autoRefresh()
                        return true
                    }
                }
                return false
            }
        })

        swipeRecyclerView.setSwipeMenuCreator(SwipeMenuCreator { leftMenu, rightMenu, position ->
            val deleteItem = SwipeMenuItem(this)
            deleteItem.setBackgroundColor(Color.parseColor("#FF3D39"))
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT).width = 220
            rightMenu.addMenuItem(deleteItem)

            if (statu == 0) {//未完成
                val updateItem = SwipeMenuItem(this)
                updateItem.setBackgroundColor(resources.getColor(R.color.Color_4184F2))
                    .setText("标记为已完成")
                    .setTextColor(Color.WHITE)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT).width = 220
                leftMenu.addMenuItem(updateItem)
            } else if (statu == 1) {//已完成
                val updateItem = SwipeMenuItem(this)
                updateItem.setBackgroundColor(resources.getColor(R.color.Color_4184F2))
                    .setText("复原")
                    .setTextColor(Color.WHITE)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT).width = 220
                leftMenu.addMenuItem(updateItem)
            }
        })

        swipeRecyclerView.setOnItemMenuClickListener(OnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            if (menuBridge.direction == 1) {
                if (statu == 0) {//未完成
                    updateTodo(todoAdapter!!.data.get(adapterPosition).id, 1)
                } else if (statu == 1) {//已完成
                    updateTodo(todoAdapter!!.data.get(adapterPosition).id, 0)
                }
            } else {
                deleteTodo(todoAdapter!!.data.get(adapterPosition).id)
            }
            todoAdapter!!.data.removeAt(adapterPosition)
            todoAdapter!!.notifyDataSetChanged()
        })

        todoAdapter = TodoAdapter()
        swipeRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeRecyclerView.adapter = todoAdapter

        refreshLayout.setRefreshHeader(MaterialHeader(this))
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.autoRefresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex = pageIndex!! + 1
        isRefresh = true
        getTodoList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 1
        isRefresh = false
        getTodoList()
    }

    private fun getTodoList() {
        var map: MutableMap<String, String> = mutableMapOf()
        map.put("status", statu.toString())
        map.put("type", type.toString())
        map.put("orderby", orderby.toString())
        apiModel!!.getTodoList(map, pageIndex, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
                var listEntity: TodoListEntity =
                    JsonUtil.fromJson<TodoListEntity>(
                        response,
                        TodoListEntity()
                    ) as TodoListEntity
                if (listEntity.errorCode == 0) {
                    if (isRefresh!!) {
                        todoAdapter!!.addData((listEntity.data!!.datas as MutableList<TodoListEntity.DataBean.DatasBean>?)!!)
                    } else {
                        todoAdapter!!.setNewInstance((listEntity.data!!.datas as MutableList<TodoListEntity.DataBean.DatasBean>?)!!)
                    }
                } else {
                    ToastUtil.showShortToast(this@TodoActivity, listEntity.errorMsg)
                }
            }

            override fun onFinish() {
                RvAnimUtils.setAnim(todoAdapter!!)
                refreshLayout.finishLoadMore()
                refreshLayout.finishRefresh()
            }

            override fun onError(error: String?) {
                ILog.e("网路异常$error")
                ToastUtil.showShortToast(this@TodoActivity, "网络异常")
            }

            override fun onStart() {
            }
        })
    }

    private fun updateTodo(id: Int, status: Int) {
        var map: MutableMap<String, String> = mutableMapOf()
        map.put("status", status.toString())
        apiModel!!.updateTodo(map, id, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
            }

            override fun onFinish() {
                ILog.e("请求结束")
            }

            override fun onError(error: String?) {
                ILog.e("请求结束")
            }

            override fun onStart() {
            }
        })
    }

    private fun deleteTodo(id: Int) {
        apiModel!!.deleteTodo(id, this, object : RxhttpUtil.RxHttpCallBack {
            override fun onSuccess(response: String?) {
                ILog.e("请求成功$response")
            }

            override fun onFinish() {
                ILog.e("请求结束")
            }

            override fun onError(error: String?) {
                ILog.e("请求结束")
            }

            override fun onStart() {
            }
        })
    }
}