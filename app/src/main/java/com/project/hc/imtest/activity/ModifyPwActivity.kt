package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
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

        finish()
    }
}