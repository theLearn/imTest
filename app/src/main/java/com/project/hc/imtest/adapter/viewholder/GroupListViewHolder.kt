package com.project.hc.imtest.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.project.hc.imtest.R

class GroupListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tv_group_name)
    val tvDes = itemView.findViewById<TextView>(R.id.tv_group_des)
    val ivGroupIcon = itemView.findViewById<ImageView>(R.id.iv_group_icon)
}