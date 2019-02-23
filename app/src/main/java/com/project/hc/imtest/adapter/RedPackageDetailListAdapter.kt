package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ImageLoadUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.RedPackageDetailListViewHolder
import com.project.hc.imtest.model.RedPackageDetailInfo

class RedPackageDetailListAdapter : BaseListAdapter<RedPackageDetailInfo, RedPackageDetailListViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): RedPackageDetailListViewHolder {
        return RedPackageDetailListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_red_package_detail_list, parent, false))
    }

    override fun onBaseBindViewHolder(holder: RedPackageDetailListViewHolder, position: Int) {
        val model = data[position]
        ImageLoadUtils.bindImageUrlForRound(holder.ivUserPhoto, model.url, R.mipmap.icon_photo_default)
        holder.tvName.text = model.name
        holder.tvAmount.text = model.amount
        holder.tvDate.text = model.time
    }
}