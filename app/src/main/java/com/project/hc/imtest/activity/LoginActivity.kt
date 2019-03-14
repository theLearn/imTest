package com.project.hc.imtest.activity

import android.content.Intent
import android.view.View
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.*
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiConstants
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.LoginInfo
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

        val phone : String = SPUtils.getStringFromSP(this, ApiConstants.MOBILE)
        val password : String = SPUtils.getStringFromSP(this, ApiConstants.PASSWORD)
        if(!StringUtils.isEmpty(phone)) {
            et_login_phone.setText(phone)
            et_login_pw.setText(password)
        }
        verifyPermissions()
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

        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<LoginInfo>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .login(phone, password), object : BaseSubscriber<LoginInfo>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(loginInfo : LoginInfo) {
                        operateLoadingDialog(false)
                        BaseApplication.getInstance()?.loginInfo = loginInfo
                        SPUtils.putValueToSP(this@LoginActivity, ApiConstants.MOBILE, phone)
                        SPUtils.putValueToSP(this@LoginActivity, ApiConstants.PASSWORD, password)
                        SPUtils.putValueToSP(this@LoginActivity, ApiConstants.AUTOLOGIN, true)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }))
    }

    override fun requestPermission(isSuccess: Boolean) {
        super.requestPermission(isSuccess)
        if(!isSuccess) {
            ToastUtils.show(this, "缺少必要权限")
            finish()
        } else {
            if(SPUtils.getBooleanFromSP(this, ApiConstants.AUTOLOGIN, false)){
                login()
            }
        }
    }
}
