package com.project.hc.imtest.activity

import android.content.Intent
import android.view.View
import com.example.hongcheng.common.util.AppUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.common.util.SPUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_setting.*
import com.project.hc.imtest.api.ApiConstants

class SettingActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_setting
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_setting
    }

    override fun initBodyView(view: View) {
        tv_current_app_version.text = String.format(getString(R.string.app_version_tip), AppUtils.getVersionName(this))

        ll_setting_bind_account.setOnClickListener(this)
        ll_setting_modify_pw.setOnClickListener(this)
        ll_setting_check_app_version.setOnClickListener(this)

        val allowSound : Boolean = SPUtils.getBooleanFromSP(this, ApiConstants.ALLOWSOUND, true) 
        ssv_setting_sound.setChecked(allowSound)
        ssv_setting_sound.setOnChangedListener { slipSwitch, checkState ->
            SPUtils.putValueToSP(this@SettingActivity, ApiConstants.ALLOWSOUND  , checkState)                                 
        }
    }

    override fun onClick(v: View?) {
        if(ViewUtils.isFastClick()) return
        when(v?.id) {
            R.id.ll_setting_bind_account -> {
                startActivity(Intent(this, BindAccountActivity::class.java))
            }
            R.id.ll_setting_modify_pw -> {
                startActivity(Intent(this, ModifyPwActivity::class.java))
            }
            R.id.ll_setting_check_app_version -> {
                ToastUtils.show(this, "已是最新版本！")
            }
            else -> {

            }
        }
    }
}
