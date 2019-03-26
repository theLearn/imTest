package com.project.hc.imtest.activity

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.GroupMemberAdapter
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.GroupInfo
import com.project.hc.imtest.model.GroupMemberInfo
import com.project.hc.imtest.model.GroupMemberInfoList
import kotlinx.android.synthetic.main.body_group_member_list.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class GroupMemberListActivity : AppCommonActivity() {

    private lateinit var mAdapter: GroupMemberAdapter
    private var dataList : MutableList<GroupMemberInfo> = arrayListOf()

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_group_member
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_group_member_list
    }

    override fun initBodyView(view: View) {
        rv_group_member_list.layoutManager = StaggeredGridLayoutManager(5, OrientationHelper.VERTICAL)
        rv_group_member_list.itemAnimator = DefaultItemAnimator()
        mAdapter = GroupMemberAdapter()
        rv_group_member_list.adapter = mAdapter
        getData()
    }

    private fun getData() {
        val model = intent.getParcelableExtra<GroupInfo>("model")
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<GroupMemberInfoList>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .getGroupMemberList(model.gid), object : BaseSubscriber<GroupMemberInfoList>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : GroupMemberInfoList) {
                        operateLoadingDialog(false)
                        tv_app_common_title.text = String.format(getString(R.string.title_group_member), obj.count)
                        dataList.addAll(obj.data)
                        mAdapter.data = dataList
                        mAdapter.notifyDataSetChanged()
                    }
                }))

    }
}
