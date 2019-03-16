package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ImageLoadUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.GroupMemberViewHolder
import com.project.hc.imtest.model.GroupMemberInfo

class GroupMemberAdapter : BaseListAdapter<GroupMemberInfo, GroupMemberViewHolder>()  {

    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
        return GroupMemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group_member, parent, false))
    }

    override fun onBaseBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
        val model = data[position]
        holder.name.text = model.nickname
        ImageLoadUtils.bindImageUrlForRound(holder.photo, model.litpic, R.mipmap.icon_photo_default)
    }
}