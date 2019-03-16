package com.project.hc.imtest.activity

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.example.hongcheng.common.base.BaseListAdapter
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMGroup
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.GroupMemberAdapter
import com.project.hc.imtest.chat.ChatUtils
import com.project.hc.imtest.model.GroupInfo
import kotlinx.android.synthetic.main.body_group_member_list.*


class GroupMemberListActivity : AppCommonActivity(), BaseListAdapter.OnItemClickListener {

    private lateinit var mAdapter: GroupMemberAdapter
    private var dataList : MutableList<String> = arrayListOf()

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
        mAdapter.onItemClickListener = this
        rv_group_member_list.adapter = mAdapter
        getData()
    }

    private fun getData() {
        operateLoadingDialog(true)

        val model = intent.getParcelableExtra<GroupInfo>("model")
        //根据群组ID从服务器获取群组基本信息
        EMClient.getInstance().groupManager().asyncGetGroupFromServer(model.gid, object : EMValueCallBack<EMGroup>{
            override fun onSuccess(obj : EMGroup?) {
                runOnUiThread {
                    operateLoadingDialog(false)
                    obj?.let {
                        dataList.addAll(it.members)
                        mAdapter.data = dataList
                        mAdapter.notifyDataSetChanged()
                    } }

            }

            override fun onError(p0: Int, p1: String?) {
                runOnUiThread {
                    operateLoadingDialog(false)
                }
            }
        })

    }

    override fun onItemClick(position: Int) {
        ChatUtils.goToChat(this, mAdapter.getItem(position), "", EMConversation.EMConversationType.Chat)
    }

    override fun onItemLongClick(position: Int) {
    }
}