package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ScreenUtils
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
import com.project.hc.imtest.model.SMSTime
import com.project.hc.imtest.util.SMSCountDownUtil
import kotlinx.android.synthetic.main.activity_forget_pw.*


class ForgetPwActivity : BasicActivity(), View.OnClickListener, SMSCountDownUtil.ICountDownTimer {

    override fun getLayoutResId(): Int {
        return R.layout.activity_forget_pw
    }

    override fun initView() {
        ScreenUtils.setLightStatusBar(this, true)
        ScreenUtils.setWindowStatusBarColor(this, R.color.white)
        iv_forget_cancel.setOnClickListener(this)
        tv_forget_sms_code_tip.setOnClickListener(this)
        bt_forget.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        SMSCountDownUtil.getInstance().addListener(this)
    }

    override fun onPause() {
        super.onPause()
        SMSCountDownUtil.getInstance().removeListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.iv_forget_cancel -> finish()
            R.id.tv_forget_sms_code_tip -> {
                val phone = et_forget_phone.text.toString().trim()
                if (ValidateUtils.isEmpty(phone)) {
                    ToastUtils.show(this, "请输入手机号")
                    return
                }
                if (!ValidateUtils.isPhone(phone)) {
                    ToastUtils.show(this, "手机号格式不正确")
                    return
                }
                getTime()
            }
            R.id.bt_forget -> {
                resetPw()
            }
            else -> {
            }
        }
    }

    private fun getTime() {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<SMSTime>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .getTime(et_forget_phone.text.toString().trim()), object : BaseSubscriber<SMSTime>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : SMSTime) {
                        operateLoadingDialog(false)
                        getSmsCode(obj.time)
                    }
                }))
    }

    private fun getSmsCode(time : String) {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .getSmsCode(et_forget_phone.text.toString().trim(), "1", time), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        SMSCountDownUtil.getInstance().start()
                    }
                }))
    }

    override fun showCountDownState(isRunning: Boolean) {
        tv_forget_sms_code_tip.isClickable = !isRunning
        tv_forget_sms_code_tip.isEnabled = !isRunning
        if (!isRunning) {
            tv_forget_sms_code_tip.setText(R.string.get_sms_code)
        }
    }

    override fun onFinish() {
        tv_forget_sms_code_tip.setText(R.string.get_sms_code)
        tv_forget_sms_code_tip.isClickable = true
        tv_forget_sms_code_tip.isEnabled = true
    }

    override fun onTick(millisUntilFinished: Long) {
        tv_forget_sms_code_tip.text = String.format(getString(R.string.second_tip), millisUntilFinished / 1000)
        tv_forget_sms_code_tip.isClickable = false
        tv_forget_sms_code_tip.isEnabled = false
    }

    private fun resetPw() {
        val phone = et_forget_phone.text.toString().trim()
        if (ValidateUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号")
            return
        }
        if (!ValidateUtils.isPhone(phone)) {
            ToastUtils.show(this, "手机号格式不正确")
            return
        }

        val smsCode = et_forget_sms_code.text.toString().trim()
        if (ValidateUtils.isEmpty(smsCode)) {
            ToastUtils.show(this, "请输入验证码")
            return
        }

        val password = et_forget_pw.text.toString().trim()
        if (ValidateUtils.isEmpty(password)) {
            ToastUtils.show(this, "请输入密码")
            return
        }

        val passwordConfirm = et_forget_confirm_pw.text.toString().trim()
        if (ValidateUtils.isEmpty(passwordConfirm)) {
            ToastUtils.show(this, "请再次输入密码")
            return
        }

        if (passwordConfirm != password) {
            ToastUtils.show(this, "两次输入密码不相同")
            return
        }

        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .forgetPw(phone, password, smsCode), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), "设置成功")
                        finish()
                    }
                }))
    }
}