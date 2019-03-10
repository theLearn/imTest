package com.project.hc.imtest.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.base.BasicFragment
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.GroupListAdapter
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.chat.ChatUtils
import com.project.hc.imtest.model.BaseListResponse
import com.project.hc.imtest.model.GroupInfo
import kotlinx.android.synthetic.main.fragment_group_list.*


class GroupListFragment : BasicFragment() {

    private lateinit var mAdapter: GroupListAdapter
    private var leftList: MutableList<GroupInfo> = arrayListOf()
    private var rightList: MutableList<GroupInfo> = arrayListOf()
    private var leftLoadSuccess : Boolean = false
    private var rightLoadSuccess : Boolean = false

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
                joinGroup(mAdapter.getItem(position).gid)
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        getData("2")
    }

    private fun joinGroup(groupId: String) {
        operateLoadingDialog(true)
        EMClient.getInstance().groupManager().asyncJoinGroup(groupId, object : EMCallBack {
            override fun onSuccess() {
                activity?.runOnUiThread {
                    operateLoadingDialog(false)
                    toGroup(groupId)
                    ToastUtils.show(activity, "加入成功")
                }
            }

            override fun onProgress(p0: Int, p1: String?) {
            }

            override fun onError(p0: Int, p1: String?) {
                activity?.runOnUiThread {
                    operateLoadingDialog(false)
                    if (601 == p0) {
                        toGroup(groupId)
                    } else {
                        ToastUtils.show(activity, "加入失败")
                    }
                }
            }
        })
    }

    private fun toGroup(groupId: String) {
        ChatUtils.goToChat(activity, groupId, "", EMConversation.EMConversationType.GroupChat)
    }

    fun loadData(isLeft: Boolean) {
        if (isLeft) {
            if(leftLoadSuccess) {
                mAdapter.data = leftList
                mAdapter.notifyDataSetChanged()
            } else {
                getData("2")
            }
        } else {
            if(rightLoadSuccess)
            {
                mAdapter.data = rightList
                mAdapter.notifyDataSetChanged()
            } else {
                getData("1")
            }
        }
    }

    private fun getData(type : String) {
        operateLoadingDialog(true)
        RetrofitClient.getInstance().map<BaseListResponse<GroupInfo>>(
            RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                .getAllGroup(type, 0, 100), object : BaseSubscriber<BaseListResponse<GroupInfo>>() {
                override fun onError(e: ActionException) {
                    operateLoadingDialog(false)
                    ToastUtils.show(BaseApplication.getInstance(), e.message)
                }

                override fun onBaseNext(obj: BaseListResponse<GroupInfo>) {
                    operateLoadingDialog(false)
                    if("1" == type) {
                        rightLoadSuccess = true
                        rightList.addAll(obj.data)
                        mAdapter.data = rightList
                    } else if ("2" == type) {
                        leftLoadSuccess = true
                        leftList.addAll(obj.data)
                        mAdapter.data = leftList
                    }

                    mAdapter.notifyDataSetChanged()
                }
            })
    }
}