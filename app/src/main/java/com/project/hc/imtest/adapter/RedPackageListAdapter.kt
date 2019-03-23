package com.project.hc.imtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.viewholder.RedPackageListViewHolder
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.RedPackageInfo

class RedPackageListAdapter : BaseListAdapter<RedPackageInfo, RedPackageListViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): RedPackageListViewHolder {
        return RedPackageListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_red_package_record,
                parent,
                false
            )
        )
    }

    override fun onBaseBindViewHolder(holder: RedPackageListViewHolder, position: Int) {
        val model = data[position]
        holder.tvTime.text = model.add_time
        holder.tvType.text = model.remark

        if("2" == model.type || "8" == model.type){
            holder.tvAmount.setTextColor(BaseApplication.getInstance()?.resources?.getColor(R.color.colorBase)!!)
            holder.tvAmount.text =  "+" + model.money
        } else {
            holder.tvAmount.setTextColor(BaseApplication.getInstance()?.resources?.getColor(R.color.bg_red_package)!!)
            holder.tvAmount.text =  "-" + model.money
        }
    }
}