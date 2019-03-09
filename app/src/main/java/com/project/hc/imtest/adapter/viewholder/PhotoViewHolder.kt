package com.project.hc.imtest.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.project.hc.imtest.R

class PhotoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val photo : ImageView = itemView.findViewById(R.id.iv_photo)
}