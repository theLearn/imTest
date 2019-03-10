package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ImageLoadUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.GroupListViewHolder
import com.project.hc.imtest.model.GroupInfo

class GroupListAdapter : BaseListAdapter<GroupInfo, GroupListViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {
        return GroupListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group_list, parent, false))
    }

    override fun onBaseBindViewHolder(holder: GroupListViewHolder, position: Int) {
        val model = data[position]
        holder.tvName.text = model.name
        ImageLoadUtils.bindImageUrlForRound(holder.ivGroupIcon, model.pic, R.mipmap.icon_photo_default)
    }
}