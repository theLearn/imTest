package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseExtentModel
import com.example.hongcheng.common.base.BaseListAdapter
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.MsgListViewHolder
import com.project.hc.imtest.model.MsgInfo

class MsgListAdapter : BaseListAdapter<BaseExtentModel<MsgInfo>, MsgListViewHolder>() {
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
        val msgInfo = model.model
        holder.tvTitle.text = msgInfo.title
        holder.tvTime.text = msgInfo.add_time
        holder.tvContent.text = msgInfo.content
        holder.ivFlag.setImageResource(if(model.checked) R.mipmap.icon_gray_cycle else R.mipmap.icon_theme_cycle)
    }
}