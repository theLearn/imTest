package com.project.hc.imtest.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.base.BasicFragment
import com.example.hongcheng.common.util.ToastUtils
import com.hyphenate.EMCallBack
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMCursorResult
import com.hyphenate.chat.EMGroupInfo
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.GroupListAdapter
import com.project.hc.imtest.chat.ChatUtils
import kotlinx.android.synthetic.main.fragment_group_list.*


class GroupListFragment : BasicFragment() {

    private lateinit var mAdapter: GroupListAdapter
    private val leftList: MutableList<EMGroupInfo> = arrayListOf()
    private val rightList: MutableList<EMGroupInfo> = arrayListOf()

    override fun getLayoutResId(): Int {
        return R.layout.fragment_group_list
    }

    override fun initView() {
        rv_group_list.layoutManager = LinearLayoutManager(rv_group_list.context)
        rv_group_list.itemAnimator = DefaultItemAnimator()
        mAdapter = GroupListAdapter()
        rv_group_list.adapter = mAdapter

        mAdapter.onItemClickListener = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                joinGroup(mAdapter.getItem(position).groupId)
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        EMClient.getInstance().groupManager()
            .asyncGetPublicGroupsFromServer(10, "0", object : EMValueCallBack<EMCursorResult<EMGroupInfo>> {
                override fun onSuccess(value: EMCursorResult<EMGroupInfo>?) {
                    value?.data?.let {
                        leftList.clear()
                        leftList.addAll(it)
                    }

                    activity?.runOnUiThread {
                        loadData(true)
                    }
                }

                override fun onError(error: Int, errorMsg: String) {
                }
            })
    }

    private fun joinGroup(groupId: String) {
        operateLoadingDialog(true)
        EMClient.getInstance().groupManager().asyncJoinGroup(groupId, object : EMCallBack {
            override fun onSuccess() {
                operateLoadingDialog(false)
                toGroup(groupId)
                ToastUtils.show(activity, "加入成功")
            }

            override fun onProgress(p0: Int, p1: String?) {
            }

            override fun onError(p0: Int, p1: String?) {
                operateLoadingDialog(false)
                if (601 == p0) {
                    toGroup(groupId)
                } else {
                    ToastUtils.show(activity, "加入失败")
                }
            }
        })
    }

    private fun toGroup(groupId: String) {
        ChatUtils.goToChat(activity, groupId, "", EMConversation.EMConversationType.GroupChat)
    }

    fun loadData(isLeft: Boolean) {
        if (isLeft) {
            mAdapter.data = leftList
        } else {
            mAdapter.data = rightList
        }

        mAdapter.notifyDataSetChanged()
    }
}