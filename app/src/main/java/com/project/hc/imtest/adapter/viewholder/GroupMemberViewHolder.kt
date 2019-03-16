package com.project.hc.imtest.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.project.hc.imtest.R

class GroupMemberViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val photo : ImageView = itemView.findViewById(R.id.iv_group_member_photo)
    val name : TextView = itemView.findViewById(R.id.tv_group_member_name)
}