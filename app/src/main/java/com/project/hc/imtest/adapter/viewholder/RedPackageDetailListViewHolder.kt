package com.project.hc.imtest.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.project.hc.imtest.R

class RedPackageDetailListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tv_red_package_detail_name)
    val tvAmount = itemView.findViewById<TextView>(R.id.tv_red_package_detail_amount)
    val tvDate = itemView.findViewById<TextView>(R.id.tv_red_package_detail_date)
    val ivUserPhoto = itemView.findViewById<ImageView>(R.id.iv_red_package_detail_icon)
}