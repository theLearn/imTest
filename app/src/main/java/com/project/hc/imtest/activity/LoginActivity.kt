package com.project.hc.imtest.activity

import android.content.Intent
import android.view.View
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BasicActivity(), View.OnClickListener {

    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        ScreenUtils.setLightStatusBar(this, true)
        ScreenUtils.setWindowStatusBarColor(this, R.color.white)
        iv_login_cancel.setOnClickListener(this)
        tv_register_account.setOnClickListener(this)
        tv_forget_pw.setOnClickListener(this)
        bt_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.iv_login_cancel -> finish()
            R.id.tv_register_account -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.tv_forget_pw -> {
                startActivity(Intent(this, ForgetPwActivity::class.java))
            }
            R.id.bt_login -> {
                login()
            }
            else -> {

            }
        }
    }

    private fun login() {
        val phone = et_login_phone.text.toString().trim()
        if (ValidateUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号")
            return
        }
        if (!ValidateUtils.isPhone(phone)) {
            ToastUtils.show(this, "手机号格式不正确")
            return
        }
        val password = et_login_pw.text.toString().trim()
        if (ValidateUtils.isEmpty(password)) {
            ToastUtils.show(this, "请输入密码")
            return
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}