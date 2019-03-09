package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ImageLoadUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.PhotoViewHolder

class PhotoAdapter : BaseListAdapter<String, PhotoViewHolder>()  {

    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))
    }

    override fun onBaseBindViewHolder(holder: PhotoViewHolder, position: Int) {
        ImageLoadUtils.bindImageUrlForRound(holder.photo, data[position], R.mipmap.icon_photo_default)
    }
}