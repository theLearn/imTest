package com.project.hc.imtest.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.project.hc.imtest.R

class RedPackageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvType = itemView.findViewById<TextView>(R.id.tv_red_package_type)
    val tvTime = itemView.findViewById<TextView>(R.id.tv_red_package_time)
    val tvAmount = itemView.findViewById<TextView>(R.id.tv_red_package_amount)
}