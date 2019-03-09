package com.project.hc.imtest.activity

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.RedPackageListAdapter
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.RecordList
import com.project.hc.imtest.model.RedPackageInfo
import kotlinx.android.synthetic.main.body_red_package_history.*


class RedPackageHistoryActivity : AppCommonActivity(),  SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val PAGE_SIZE : Int = 10
    }

    private lateinit var mAdapter : RedPackageListAdapter
    private var page : Int = 0
    private var dataList : MutableList<RedPackageInfo> = arrayListOf()

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_red_package_record
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_red_package_history
    }

    override fun initBodyView(view: View) {
        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_nothing, srl_red_package, false)
        rv_red_package_list.layoutManager = LinearLayoutManager(rv_red_package_list.context)
        rv_red_package_list.itemAnimator = DefaultItemAnimator()
        rv_red_package_list.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST, 2, resources.getColor(R.color.default_background)
            )
        )
        mAdapter = RedPackageListAdapter()
        rv_red_package_list.adapter = mAdapter
        rv_red_package_list.setEmptyView(emptyView)
        emptyView.visibility = View.GONE

        // 设置加载更多监听
        rv_red_package_list.setLoadMoreListener { getData() }

        srl_red_package.setProgressBackgroundColorSchemeColor(Color.WHITE)
        srl_red_package.setColorSchemeResources(R.color.colorBase)
        srl_red_package.setProgressViewOffset(false, 0, ScreenUtils.dp2px(this, 30.0f))
        srl_red_package.setOnRefreshListener(this)
        srl_red_package.post{
            srl_red_package.isRefreshing = true
            page = 0
            dataList.clear()
            getData()
        }
    }

    override fun onRefresh() {
        page = 0
        dataList.clear()
        getData()
    }

    private fun getData() {
        compositeDisposable.add(
            RetrofitClient.getInstance().map<RecordList>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .getRedRecord(page, PAGE_SIZE), object : BaseSubscriber<RecordList>() {
                    override fun onError(e: ActionException) {
                        srl_red_package.isRefreshing = false
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : RecordList) {
                        srl_red_package.isRefreshing = false
                        val count = try {
                            Integer.parseInt(obj.count)
                        } catch (e : NumberFormatException) {
                            0
                        }
                        dataList.addAll(obj.data)
                        mAdapter.data = dataList
                        rv_red_package_list.enableLoadMore(dataList.size <= count)
                        page++
                    }
                }))
    }
}