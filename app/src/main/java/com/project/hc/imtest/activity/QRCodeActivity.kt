package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ImageLoadUtils
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import kotlinx.android.synthetic.main.body_qr_code.*
import com.project.hc.imtest.util.QRCodeUtil
import android.graphics.Bitmap




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
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<String>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .qrCode, object : BaseSubscriber<String>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : String) {
                        operateLoadingDialog(false)
                        val mBitmap = QRCodeUtil.createQRCodeBitmap(obj, iv_qr_code.width, iv_qr_code.height)
                        iv_qr_code.setImageBitmap(mBitmap)
                    }
                }))
    }
}