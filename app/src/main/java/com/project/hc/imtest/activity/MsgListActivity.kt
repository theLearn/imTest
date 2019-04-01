package com.project.hc.imtest.activity

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.example.hongcheng.common.base.BaseExtentModel
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.SPUtils
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.MsgListAdapter
import com.project.hc.imtest.api.ApiConstants
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.MsgInfo
import kotlinx.android.synthetic.main.body_msg_list.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class MsgListActivity : AppCommonActivity(),  SwipeRefreshLayout.OnRefreshListener,
    BaseListAdapter.OnItemClickListener {

    companion object {
        const val SYSTEM_NOTIFY : Int = 0
        const val PLATFORM_NOTIFY : Int = 1
        private const val PAGE_SIZE : Int = 20
    }

    private lateinit var mAdapter : MsgListAdapter
    private var page : Int = 1
    private var dataList : MutableList<BaseExtentModel<MsgInfo>> = arrayListOf()
    private var readList : MutableList<String> = arrayListOf()

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
        readList.addAll(SPUtils.readSp(this, BaseApplication.getInstance()?.loginInfo?.userId + ApiConstants.MSG_READ_LIST))

        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_nothing, srl_msg, false)
        rv_msg_list.layoutManager = LinearLayoutManager(rv_msg_list.context)
        rv_msg_list.itemAnimator = DefaultItemAnimator()
        rv_msg_list.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST, 2, resources.getColor(R.color.default_background)
            )
        )
        mAdapter = MsgListAdapter()
        mAdapter.onItemClickListener = this
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
            page = 1
            dataList.clear()
            getData()
        }

        val type = intent.getIntExtra("type", SYSTEM_NOTIFY)
        if(PLATFORM_NOTIFY == type) {
            tv_app_common_title.setText(R.string.platform_notify)
        }
    }

    override fun onRefresh() {
        page = 1
        dataList.clear()
        getData()
    }

    private fun getData() {
        compositeDisposable.add(
            RetrofitClient.getInstance().map<List<MsgInfo>>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .getSystemNotifyList(page, PAGE_SIZE), object : BaseSubscriber<List<MsgInfo>>() {
                    override fun onError(e: ActionException) {
                        srl_msg.isRefreshing = false
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : List<MsgInfo>) {
                        srl_msg.isRefreshing = false
                        for (info in obj) {
                            dataList.add(BaseExtentModel(hasRead(info), info))
                        }

                        mAdapter.data = dataList
                        rv_msg_list.enableLoadMore(obj.size >= PAGE_SIZE)
                        page++
                    }
                }))
    }

    private fun hasRead(model : MsgInfo) : Boolean{
        for(s in readList) {
            if(s == model.id) {
                return true
            }
        }

        return false
    }

    override fun onItemClick(position: Int) {
        val model = mAdapter.getItem(position)
        if(!model.checked) {
            readList.add(model.model.id)
            SPUtils.writeSp(this, BaseApplication.getInstance()?.loginInfo?.userId + ApiConstants.MSG_READ_LIST, readList)
            model.checked = true
            rv_msg_list.adapter?.notifyItemChanged(position)
        }
        val intent = Intent(this, NoticeDetailActivity::class.java)
        intent.putExtra("model", model.model)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int) {
    }
}