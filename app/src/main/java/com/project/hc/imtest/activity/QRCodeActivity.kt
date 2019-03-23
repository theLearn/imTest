package com.project.hc.imtest.activity

import android.os.Handler
import android.view.View
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiConstants
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.util.QRCodeUtil
import kotlinx.android.synthetic.main.body_qr_code.*


class QRCodeActivity : AppCommonActivity(){

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_qr_code
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_qr_code
    }

    override fun initBodyView(view: View) {
        Handler().postDelayed({
            val mBitmap = QRCodeUtil.createQRCodeBitmap(ApiConstants.QR_CODE_HTML_URL + BaseApplication.getInstance()?.loginInfo?.userId, iv_qr_code.width, iv_qr_code.height)
            iv_qr_code.setImageBitmap(mBitmap)
        }, 500)
    }
}