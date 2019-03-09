package com.project.hc.imtest.application

import android.support.multidex.MultiDexApplication
import com.example.hongcheng.common.lifecycle.ActivityLifecycleImpl
import com.example.hongcheng.common.util.ResUtil
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.example.hongcheng.data.room.DBInit
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hyphenate.easeui.EaseUI
import com.project.hc.imtest.model.LoginInfo
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseApplication : MultiDexApplication() {

    internal var loginInfo : LoginInfo? = null

    private object BaseApplicationHolder {
        var INSTANCE: BaseApplication? = null
    }

    companion object {
        @JvmStatic
        fun getInstance(): BaseApplication? = BaseApplicationHolder.INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        BaseApplicationHolder.INSTANCE = this
        ResUtil.init(this)
        registerActivityLifecycleCallbacks(ActivityLifecycleImpl.getInstance())
        DBInit.getInstance().init(this)

        val options = EMOptions()
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false)
// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true)
// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true)
//初始化
        EMClient.getInstance().init(applicationContext, options)
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true)
        EaseUI.getInstance().init(applicationContext, options)

        val interceptors: MutableList<Interceptor> = arrayListOf()
        interceptors.add(AddTokenInterceptors())
        RetrofitManager.setInterceptors(interceptors)
    }

    private class AddTokenInterceptors() : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val token : String? = BaseApplication.getInstance()?.loginInfo?.token
            if(token != null) {
                requestBuilder
//                .header("platform", "platform")//平台
//                .header("sysVersion", "sysVersion")//系统版本号
//                .header("device", "device")//设备信息
//                .header("screen", "screen")//屏幕大小
//                .header("uuid", "uuid")//设备唯一码
//                .header("version", "version")//app版本
//                .header("apiVersion", "apiVersion")//api版本
                    .header("token", token)//令牌
//                .header("channelId", "channelId")//渠道
//                .header("networkType", "networkType")//网络类型
            }

            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}