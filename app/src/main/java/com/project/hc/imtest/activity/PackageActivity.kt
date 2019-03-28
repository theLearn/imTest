package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.hyphenate.chat.EMConversation
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.chat.ChatUtils
import com.project.hc.imtest.model.KFInfo
import com.project.hc.imtest.model.PointInfo
import kotlinx.android.synthetic.main.body_package.*


class PackageActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_my_package
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_package
    }

    override fun initBodyView(view: View) {
        ll_recharge.setOnClickListener(this)
        ll_cash_withdrawal.setOnClickListener(this)

        getPoint()
    }

    private fun getPoint() {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<PointInfo>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .point, object : BaseSubscriber<PointInfo>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(pointInfo : PointInfo) {
                        operateLoadingDialog(false)
                        tv_package_amount.text = "¥ " + pointInfo.points
                    }
                }))
    }

    override fun onClick(v: View?) {
        if(ViewUtils.isFastClick()) return
        when(v?.id) {
            R.id.ll_recharge -> {
                getKfInfo(R.string.gfkf)
            }
            R.id.ll_cash_withdrawal -> {
                getKfInfo(R.string.gfkf)
            }
            else -> {

            }
        }
    }

    private fun getKfInfo(id : Int) {
        operateLoadingDialog(true)
        RetrofitClient.getInstance().map<KFInfo>(
            RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                .kfInfo, object : BaseSubscriber<KFInfo>() {
                override fun onError(e: ActionException) {
                    operateLoadingDialog(false)
                    ToastUtils.show(BaseApplication.getInstance(), e.message)
                }

                override fun onBaseNext(obj: KFInfo) {
                    operateLoadingDialog(false)
                    ChatUtils.goToChat(this@PackageActivity, obj.mid, obj.nickname, EMConversation.EMConversationType.Chat)
                }
            })
    }
}