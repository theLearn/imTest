package com.project.hc.imtest.activity

import android.graphics.Color
import android.os.Handler
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
import com.example.hongcheng.common.view.DividerItemDecoration
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.RedPackageListAdapter
import com.project.hc.imtest.model.RedPackageInfo
import kotlinx.android.synthetic.main.body_my_get.*


class MyGetActivity : AppCommonActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mAdapter: RedPackageListAdapter
    private var page: Int = 0
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
            page = 0
            dataList.clear()
            getData()
        }

        showReplace(tv_my_get_today, R.string.my_get_today_tip, "100.0", R.color.text_red_ff50)
        showReplace(tv_my_get_month, R.string.my_get_month_tip, "100.0", R.color.text_red_ff50)
    }

    override fun onRefresh() {
        page = 0
        dataList.clear()
        getData()
    }

    private fun getData() {
        Handler().postDelayed({
            for (i in 1..20) {
                dataList.add(RedPackageInfo("踩雷红包", "+2000.0", "2019-02-20 18:20:20"))
            }

            srl_my_get.isRefreshing = false
            mAdapter.data = dataList
            rv_my_get.enableLoadMore(dataList.size <= 40)
        }, 2000)
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