package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_introduce.*


class IntroduceActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_call
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_introduce
    }

    override fun initBodyView(view: View) {
        bt_submit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.bt_submit -> {
                submit()
            }
            else -> {

            }
        }
    }

    private fun submit() {
        val name = et_introduce_name.text.toString().trim()
        if (ValidateUtils.isEmpty(name)) {
            ToastUtils.show(this, "请输入姓名")
            return
        }

        val phone = et_introduce_phone.text.toString().trim()
        if (ValidateUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号")
            return
        }
        if (!ValidateUtils.isPhone(phone)) {
            ToastUtils.show(this, "手机号格式不正确")
            return
        }
        val password = et_introduce.text.toString().trim()
        if (ValidateUtils.isEmpty(password)) {
            ToastUtils.show(this, "请输入意见")
            return
        }

        finish()
    }
}