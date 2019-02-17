package com.project.hc.imtest.activity

import android.graphics.Color
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.MsgListAdapter
import com.project.hc.imtest.model.MsgInfo
import kotlinx.android.synthetic.main.body_msg_list.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class MsgListActivity : AppCommonActivity(),  SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val SYSTEM_NOTIFY : Int = 0
        const val PLATFORM_NOTIFY : Int = 1
    }

    private lateinit var mAdapter : MsgListAdapter
    private var page : Int = 0
    private var dataList : MutableList<MsgInfo> = arrayListOf()

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.system_notify
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_msg_list
    }

    override fun initBodyView(view: View) {
        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_nothing, srl_msg, false)
        rv_msg_list.layoutManager = LinearLayoutManager(rv_msg_list.context)
        rv_msg_list.itemAnimator = DefaultItemAnimator()
        rv_msg_list.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST, 2, resources.getColor(R.color.default_background)
            )
        )
        mAdapter = MsgListAdapter()
        rv_msg_list.adapter = mAdapter
        rv_msg_list.setEmptyView(emptyView)
        emptyView.visibility = View.GONE

        // 设置加载更多监听
        rv_msg_list.setLoadMoreListener { getData() }

        srl_msg.setProgressBackgroundColorSchemeColor(Color.WHITE)
        srl_msg.setColorSchemeResources(R.color.colorBase)
        srl_msg.setProgressViewOffset(false, 0, ScreenUtils.dp2px(this, 30.0f))
        srl_msg.setOnRefreshListener(this)
        srl_msg.post{
            srl_msg.isRefreshing = true
            page = 0
            dataList.clear()
            getData()
        }

        val type = intent.getIntExtra("type", SYSTEM_NOTIFY)
        if(PLATFORM_NOTIFY == type) {
            tv_app_common_title.setText(R.string.platform_notify)
        }
    }

    override fun onRefresh() {
        page = 0
        dataList.clear()
        getData()
    }

    private fun getData() {
        Handler().postDelayed({
            for (i in 1..20) {
                dataList.add(MsgInfo("通知", "2019-02-20 18:20:20", "平台需要更新，不充值可以提现，晚上25点关群，凌晨两点之前全部都需提现，过期不处理提现"))
            }

            srl_msg.isRefreshing = false
            mAdapter.data = dataList
            rv_msg_list.enableLoadMore(dataList.size <= 40)
        }, 2000)
    }
}