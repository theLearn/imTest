package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_bind_alipay.*


class BindAlipayActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.bind_alipay
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_bind_alipay
    }

    override fun initBodyView(view: View) {
        bt_bind_alipay_submit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.bt_bind_alipay_submit -> {
                submit()
            }
            else -> {

            }
        }
    }

    private fun submit() {
        val alipay = et_bind_alipay_account.text.toString().trim()
        if (ValidateUtils.isEmpty(alipay)) {
            ToastUtils.show(this, "请输入支付宝账号")
            return
        }

        val alipayConfirm = et_bind_alipay_account_confirm.text.toString().trim()
        if (ValidateUtils.isEmpty(alipayConfirm)) {
            ToastUtils.show(this, "请再次输入支付宝账号")
            return
        }

        if (alipayConfirm != alipay) {
            ToastUtils.show(this, "两次输入账号不相同")
            return
        }

        val name = et_bind_alipay_name.text.toString().trim()
        if (ValidateUtils.isEmpty(name)) {
            ToastUtils.show(this, "请输入真实姓名")
            return
        }

        finish()
    }
}