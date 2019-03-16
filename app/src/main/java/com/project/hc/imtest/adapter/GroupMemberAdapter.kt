package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.hyphenate.easeui.utils.EaseUserUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.GroupMemberViewHolder

class GroupMemberAdapter : BaseListAdapter<String, GroupMemberViewHolder>()  {

    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        return GroupMemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group_member, parent, false))
    }

    override fun onBaseBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        EaseUserUtils.setUserAvatar(null, data[position], holder.photo)
        EaseUserUtils.setUserNick(data[position], holder.name)
    }
}