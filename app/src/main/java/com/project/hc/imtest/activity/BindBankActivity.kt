package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_bind_bank.*


class BindBankActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.bind_bank_card
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_bind_bank
    }

    override fun initBodyView(view: View) {
        bt_bind_bank_submit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.bt_bind_bank_submit -> {
                submit()
            }
            else -> {

            }
        }
    }

    private fun submit() {
        val bankName = et_bind_bank_name.text.toString().trim()
        if (ValidateUtils.isEmpty(bankName)) {
            ToastUtils.show(this, "请输入银行名称")
            return
        }

        val bankCardNo = et_bind_bank_card_no.text.toString().trim()
        if (ValidateUtils.isEmpty(bankCardNo)) {
            ToastUtils.show(this, "请输入银行卡号")
            return
        }

        if (!ValidateUtils.isBankNo(bankCardNo)) {
            ToastUtils.show(this, "银行卡号格式不正确")
            return
        }

        val bankCardNoConfirm = et_bind_bank_card_no_confirm.text.toString().trim()
        if (ValidateUtils.isEmpty(bankCardNoConfirm)) {
            ToastUtils.show(this, "请再次输入银行卡号")
            return
        }

        if (bankCardNoConfirm != bankCardNo) {
            ToastUtils.show(this, "两次输入银行卡号不相同")
            return
        }

        val name = et_bind_bank_card_name.text.toString().trim()
        if (ValidateUtils.isEmpty(name)) {
            ToastUtils.show(this, "请输入银行卡姓名")
            return
        }

        val certifyNo = et_bind_bank_certify_card_no.text.toString().trim()
        if (ValidateUtils.isEmpty(certifyNo)) {
            ToastUtils.show(this, "请输入身份证号码")
            return
        }

        if (!ValidateUtils.isIDCard(certifyNo)) {
            ToastUtils.show(this, "身份证号码格式不正确")
            return
        }

        finish()
    }
}