package com.project.hc.imtest.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.project.hc.imtest.R
import com.project.hc.imtest.model.RedPackageInfo

class RedPackageListAdapter : BaseListAdapter<RedPackageInfo, RedPackageListViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): RedPackageListViewHolder {
        return RedPackageListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_red_package_record, parent, false))
    }

    override fun onBaseBindViewHolder(holder: RedPackageListViewHolder, position: Int) {
        val model = data[position]
        holder.tvType.text = model.type
        holder.tvTime.text = model.time
        holder.tvAmount.text = model.amount
    }
}