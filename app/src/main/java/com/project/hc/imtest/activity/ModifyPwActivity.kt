package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.SPUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiConstants
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import kotlinx.android.synthetic.main.body_modify_pw.*


class ModifyPwActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.modify_pw
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_modify_pw
    }

    override fun initBodyView(view: View) {
        bt_confirm_modify.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.bt_confirm_modify -> {
                submit()
            }
            else -> {

            }
        }
    }

    private fun submit() {
        val oldPw = et_modify_pw_old.text.toString().trim()
        if (ValidateUtils.isEmpty(oldPw)) {
            ToastUtils.show(this, "请输入旧密码")
            return
        }

        val newPw = et_modify_pw_new.text.toString().trim()
        if (ValidateUtils.isEmpty(newPw)) {
            ToastUtils.show(this, "请输入新密码")
            return
        }

        if (oldPw == newPw) {
            ToastUtils.show(this, "新密码和旧密码相同")
            return
        }

        val newPwConfirm = et_modify_pw_new_confirm.text.toString().trim()
        if (ValidateUtils.isEmpty(newPwConfirm)) {
            ToastUtils.show(this, "请再次输入新密码")
            return
        }

        if (newPwConfirm != newPw) {
            ToastUtils.show(this, "两次输入新密码不相同")
            return
        }

        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .changePw(oldPw, newPw), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        SPUtils.putValueToSP(this@ModifyPwActivity, ApiConstants.PASSWORD, newPw)
                        ToastUtils.show(BaseApplication.getInstance(), "修改成功")
                        finish()
                    }
                }))
    }
}