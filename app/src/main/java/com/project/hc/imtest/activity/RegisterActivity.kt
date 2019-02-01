package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.util.SMSCountDownUtil
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BasicActivity(), View.OnClickListener, SMSCountDownUtil.ICountDownTimer {

    override fun getLayoutResId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        ScreenUtils.setLightStatusBar(this, true)
        ScreenUtils.setWindowStatusBarColor(this, R.color.white)
        iv_register_cancel.setOnClickListener(this)
        tv_register_sms_code_tip.setOnClickListener(this)
        bt_register.setOnClickListener(this)
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
            R.id.iv_register_cancel -> finish()
            R.id.tv_register_sms_code_tip -> {
                SMSCountDownUtil.getInstance().start()
            }
            R.id.bt_register -> {
                register()
            }
            else -> {

            }
        }
    }

    override fun showCountDownState(isRunning: Boolean) {
        tv_register_sms_code_tip.isClickable = !isRunning
        tv_register_sms_code_tip.isEnabled = !isRunning
        if (!isRunning) {
            tv_register_sms_code_tip.setText(R.string.get_sms_code)
        }
    }

    override fun onFinish() {
        tv_register_sms_code_tip.setText(R.string.get_sms_code)
        tv_register_sms_code_tip.isClickable = true
        tv_register_sms_code_tip.isEnabled = true
    }

    override fun onTick(millisUntilFinished: Long) {
        tv_register_sms_code_tip.text = String.format(getString(R.string.second_tip), millisUntilFinished / 1000)
        tv_register_sms_code_tip.isClickable = false
        tv_register_sms_code_tip.isEnabled = false
    }

    private fun register() {
        val phone = et_register_phone.text.toString().trim()
        if (ValidateUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号")
            return
        }
        if (!ValidateUtils.isPhone(phone)) {
            ToastUtils.show(this, "手机号格式不正确")
            return
        }
        val password = et_register_pw.text.toString().trim()
        if (ValidateUtils.isEmpty(password)) {
            ToastUtils.show(this, "请输入密码")
            return
        }

        val passwordConfirm = et_register_confirm_pw.text.toString().trim()
        if (ValidateUtils.isEmpty(passwordConfirm)) {
            ToastUtils.show(this, "请再次输入密码")
            return
        }

        if (passwordConfirm != password) {
            ToastUtils.show(this, "两次输入密码不相同")
            return
        }

        val nick = et_register_nick.text.toString().trim()
        if (ValidateUtils.isEmpty(nick)) {
            ToastUtils.show(this, "请输入昵称")
            return
        }

        val smsCode = et_register_sms_code.text.toString().trim()
        if (ValidateUtils.isEmpty(smsCode)) {
            ToastUtils.show(this, "请输入验证码")
            return
        }
    }
}