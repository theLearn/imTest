package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.MsgListViewHolder
import com.project.hc.imtest.model.MsgInfo

class MsgListAdapter : BaseListAdapter<MsgInfo, MsgListViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): MsgListViewHolder {
        return MsgListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_msg_record,
                parent,
                false
            )
        )
    }

    override fun onBaseBindViewHolder(holder: MsgListViewHolder, position: Int) {
        val model = data[position]
        holder.tvTitle.text = model.title
        holder.tvTime.text = model.time
        holder.tvContent.text = model.content
    }
}