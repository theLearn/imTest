package com.project.hc.imtest.activity

import android.content.Intent
import android.view.View
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_bind_account.*


class BindAccountActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.bind_account
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_bind_account
    }

    override fun initBodyView(view: View) {
        ll_bind_alipay.setOnClickListener(this)
        ll_bind_bank_card.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(ViewUtils.isFastClick()) return
        when(v?.id) {
            R.id.ll_bind_alipay -> {
                startActivity(Intent(this, BindAlipayActivity::class.java))
            }
            R.id.ll_bind_bank_card -> {
                startActivity(Intent(this, BindBankActivity::class.java))
            }
            else -> {

            }
        }
    }
}