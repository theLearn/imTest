package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ImageLoadUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.RedPackageDetailListViewHolder
import com.project.hc.imtest.model.RedDetailInfo

class RedPackageDetailListAdapter : BaseListAdapter<RedDetailInfo, RedPackageDetailListViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): RedPackageDetailListViewHolder {
        return RedPackageDetailListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_red_package_detail_list, parent, false))
    }

    override fun onBaseBindViewHolder(holder: RedPackageDetailListViewHolder, position: Int) {
        val model = data[position]
        ImageLoadUtils.bindImageUrlForRound(holder.ivUserPhoto, model.litpic, R.mipmap.icon_photo_default)
        holder.tvName.text = model.nickname
        holder.tvAmount.text = model.money
        holder.tvDate.text = model.add_time
        when(model.if_do) {
            "max" -> {
                holder.tvDesc.text = "手气最佳"
                holder.tvDesc.setTextColor(holder.tvDesc.context.resources.getColor(R.color.colorBase))
            }
            "min" -> {
                holder.tvDesc.text = "手气最差"
                holder.tvDesc.setTextColor(holder.tvDesc.context.resources.getColor(R.color.bg_red_package))
            }
            "1" -> {
                holder.tvDesc.text = "不幸中雷"
                holder.tvDesc.setTextColor(holder.tvDesc.context.resources.getColor(R.color.bg_red_package))
            }
            else -> holder.tvDesc.text = ""
        }
    }
}