package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.BankCardInfo
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

        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<BankCardInfo>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .cardInfo, object : BaseSubscriber<BankCardInfo>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                    }

                    override fun onBaseNext(obj : BankCardInfo) {
                        operateLoadingDialog(false)
                        et_bind_bank_name.setText(obj.bank_name)
                        et_bind_bank_card_no.setText(obj.bank_code)
                        et_bind_bank_card_name.setText(obj.user_name)
                        et_bind_bank_certify_card_no.setText(obj.card_id)
                    }
                }))
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

        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .bindCard(bankName, bankCardNo, bankCardNo, name, certifyNo), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), "绑定成功")
                        finish()
                    }
                }))
    }
}