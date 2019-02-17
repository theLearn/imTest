package com.project.hc.imtest.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.project.hc.imtest.R

class MsgListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_msg_title)
    val tvTime = itemView.findViewById<TextView>(R.id.tv_msg_time)
    val tvContent = itemView.findViewById<TextView>(R.id.tv_msg_content)
}