package com.project.hc.imtest.activity

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
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
import kotlinx.android.synthetic.main.body_my_get.*


class MyGetActivity : AppCommonActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val PAGE_SIZE : Int = 20
    }

    private lateinit var mAdapter: RedPackageListAdapter
    private var page: Int = 1
    private var dataList: MutableList<RedPackageInfo> = arrayListOf()

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_my_get
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_my_get
    }

    override fun initBodyView(view: View) {
        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_nothing, srl_my_get, false)
        rv_my_get.layoutManager = LinearLayoutManager(rv_my_get.context)
        rv_my_get.itemAnimator = DefaultItemAnimator()
        rv_my_get.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST, 2, resources.getColor(R.color.default_background)
            )
        )
        mAdapter = RedPackageListAdapter()
        rv_my_get.adapter = mAdapter
        rv_my_get.setEmptyView(emptyView)
        emptyView.visibility = View.GONE

        // 设置加载更多监听
        rv_my_get.setLoadMoreListener { getData() }

        srl_my_get.setProgressBackgroundColorSchemeColor(Color.WHITE)
        srl_my_get.setColorSchemeResources(R.color.colorBase)
        srl_my_get.setProgressViewOffset(false, 0, ScreenUtils.dp2px(this, 30.0f))
        srl_my_get.setOnRefreshListener(this)
        srl_my_get.post {
            srl_my_get.isRefreshing = true
            page = 1
            dataList.clear()
            getData()
        }
    }

    override fun onRefresh() {
        page = 1
        dataList.clear()
        getData()
    }

    private fun getData() {
        compositeDisposable.add(
            RetrofitClient.getInstance().map<RecordList>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .getProfit(page, PAGE_SIZE), object : BaseSubscriber<RecordList>() {
                    override fun onError(e: ActionException) {
                        srl_my_get.isRefreshing = false
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : RecordList) {
                        srl_my_get.isRefreshing = false
                        showReplace(tv_my_get_today, R.string.my_get_today_tip, obj.today_profit, R.color.text_red_ff50)
                        showReplace(tv_my_get_month, R.string.my_get_month_tip, obj.month_profit, R.color.text_red_ff50)
                        val count = try {
                            Integer.parseInt(obj.count)
                        } catch (e : NumberFormatException) {
                            0
                        }
                        dataList.addAll(obj.data)
                        mAdapter.data = dataList
                        rv_my_get.enableLoadMore(dataList.size < count)
                        page++
                    }
                }))
    }

    private fun showReplace(tv: TextView, tip: Int, replace: String, replaceColor: Int) {
        val strTip = resources.getString(tip)
        val index = strTip.indexOf("x")
        val content = strTip.replace("x", replace)
        val colorSpan = ForegroundColorSpan(
            resources.getColor(replaceColor)
        )
        val sbs = SpannableString(content)
        sbs.setSpan(colorSpan, index, index + replace.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        tv.text = sbs
    }
}