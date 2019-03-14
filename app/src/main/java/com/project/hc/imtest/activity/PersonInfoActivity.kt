package com.project.hc.imtest.activity

import android.content.Intent
import android.view.View
import com.example.hongcheng.common.lifecycle.ActivityLifecycleImpl
import com.example.hongcheng.common.util.ImageLoadUtils
import com.example.hongcheng.common.util.SPUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiConstants
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.chat.ChatUtils
import com.project.hc.imtest.chat.CommonCallback
import kotlinx.android.synthetic.main.body_person_info.*

class PersonInfoActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_person_info
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_person_info
    }

    override fun initBodyView(view: View) {
        tv_person_info_phone.text = ValidateUtils.phoneNoHide(SPUtils.getStringFromSP(this, ApiConstants.MOBILE))

        ll_person_info_photo.setOnClickListener(this)
        ll_person_info_nick.setOnClickListener(this)
        ll_person_info_phone.setOnClickListener(this)
        bt_logout.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        ImageLoadUtils.bindImageUrlForRound(iv_person_info_photo, BaseApplication.getInstance()?.loginInfo?.photo, R.mipmap.icon_photo_default)
        tv_person_info_nick.text = BaseApplication.getInstance()?.loginInfo?.nickname
    }

    override fun onClick(view: View?) {
        if(ViewUtils.isFastClick()) return
        when(view?.id) {
            R.id.ll_person_info_photo -> {
                startActivity(Intent(this, ModifyPhotoActivity::class.java))
            }
            R.id.ll_person_info_nick -> {
                startActivity(Intent(this, ModifyNameActivity::class.java))
            }
            R.id.ll_person_info_phone -> {}
            R.id.bt_logout -> {
                operateLoadingDialog(true)
                ChatUtils.logoutChat(false, object : CommonCallback {
                    override fun onSuccess() {
                        runOnUiThread {
                            operateLoadingDialog(false)
                            toLogin()
                        }
                    }

                    override fun onFail(message: String) {
                        runOnUiThread {
                            operateLoadingDialog(false)
                            toLogin()
                        }
                    }
                })
            }
            else -> {

            }
        }
    }

    private fun toLogin() {
        SPUtils.putValueToSP(this, ApiConstants.AUTOLOGIN, false)
        ActivityLifecycleImpl.getInstance().finishAll()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
