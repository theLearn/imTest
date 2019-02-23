package com.project.hc.imtest.activity

import android.support.v7.app.ActionBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.hongcheng.common.base.CommonActivity
import com.example.hongcheng.common.util.ImageLoadUtils
import com.example.hongcheng.common.util.ScreenUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.RedPackageDetailListAdapter
import com.project.hc.imtest.model.RedPackageDetailInfo
import kotlinx.android.synthetic.main.body_red_package_detail.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class RedPackageDetailActivity : CommonActivity(){

    private lateinit var mAdapter: RedPackageDetailListAdapter

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun getTitleLayoutResId(): Int {
        return R.layout.layout_app_common_title
    }

    override fun initTitleView(view: View) {
        setSupportActionBar(tb_app_common)
        ScreenUtils.setLightStatusBar(this, true)
        ScreenUtils.setWindowStatusBarColor(this, R.color.bg_red_package)
        tb_app_common.setBackgroundResource(R.color.bg_red_package)
        if (isNeedShowBack()) {
            tb_app_common.setNavigationIcon(R.mipmap.icon_back)
            tb_app_common.setNavigationOnClickListener { onBackPressed() }
        }
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = ""
    }

    override fun setToolbarTitle(): Int {
        return 0
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_red_package_detail
    }

    override fun initBodyView(view: View) {
        ImageLoadUtils.bindImageUrlForRound(iv_red_package_detail_user_photo, "", R.mipmap.icon_photo_default)
        tv_red_package_detail_who.text = String.format(getString(R.string.who_red_package), "大眼")
        tv_red_package_amount.text = "100"
        tv_red_package_detail_tip.text = String.format(getString(R.string.open_red_package_detail_tip), "3")

        rv_red_package_open_list.layoutManager = LinearLayoutManager(rv_red_package_open_list.context)
        rv_red_package_open_list.itemAnimator = DefaultItemAnimator()
        mAdapter = RedPackageDetailListAdapter()
        rv_red_package_open_list.adapter = mAdapter

        getData()
    }

    private fun getData() {
        val dataList : MutableList<RedPackageDetailInfo> = arrayListOf()
        dataList.add(RedPackageDetailInfo("", "大眼", "20.0", "2019-02-20 18:20:20"))
        mAdapter.data = dataList
        mAdapter.notifyDataSetChanged()
    }
}