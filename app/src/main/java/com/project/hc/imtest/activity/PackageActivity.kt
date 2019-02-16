package com.project.hc.imtest.activity

import android.content.Intent
import android.view.View
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_package.*


class PackageActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_my_package
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_package
    }

    override fun initBodyView(view: View) {
        ll_recharge.setOnClickListener(this)
        ll_cash_withdrawal.setOnClickListener(this)

        tv_package_amount.text = "100.00"
    }

    override fun onClick(v: View?) {
        if(ViewUtils.isFastClick()) return
        when(v?.id) {
            R.id.ll_recharge -> {
            }
            R.id.ll_cash_withdrawal -> {
            }
            else -> {

            }
        }
    }
}