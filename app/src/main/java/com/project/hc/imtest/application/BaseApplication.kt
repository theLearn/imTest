package com.project.hc.imtest.application

import android.support.multidex.MultiDexApplication
import android.text.TextUtils
import com.example.hongcheng.common.lifecycle.ActivityLifecycleImpl
import com.example.hongcheng.common.util.LoggerUtils
import com.example.hongcheng.common.util.ResUtil
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseFlowableSubscriber
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.example.hongcheng.data.room.DBInit
import com.example.hongcheng.data.room.RoomClient
import com.example.hongcheng.data.room.entity.EaseUserInfoEntity
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hyphenate.easeui.EaseUI
import com.hyphenate.easeui.domain.EaseUser
import com.hyphenate.easeui.utils.EaseCommonUtils
import com.project.hc.imtest.model.LoginInfo
import io.reactivex.FlowableOnSubscribe
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseApplication : MultiDexApplication() {

    var loginInfo : LoginInfo? = null
    private var contactList : MutableMap<String, EaseUser> = hashMapOf()

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
        options.acceptInvitationAlways = false
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.autoTransferMessageAttachments = true
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true)
        //初始化
        EMClient.getInstance().init(applicationContext, options)
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true)
        EaseUI.getInstance().init(applicationContext, options)
        EaseUI.getInstance().setUserProfileProvider { username -> getUserInfo(username) }

        val interceptors: MutableList<Interceptor> = arrayListOf()
        interceptors.add(AddTokenInterceptors())
        RetrofitManager.setInterceptors(interceptors)
        initContactList()
    }

    private class AddTokenInterceptors : Interceptor {

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

    private fun getUserInfo(username: String): EaseUser {
        //获取 EaseUser实例, 这里从内存中读取
        //如果你是从服务器中读读取到的，最好在本地进行缓存
        var user: EaseUser? = null
        //如果用户是本人，就设置自己的头像
        if (username == EMClient.getInstance().currentUser) {
            user = EaseUser(username)
            user.avatar = loginInfo?.photo
            user.nickname = loginInfo?.nickname
            return user
        }
        //收到别人的消息，设置别人的头像
        if (contactList.containsKey(username)) {
            user = contactList[username]
        } else {
            initContactList()
            user= contactList[username]
        }
        //如果用户不是你的联系人，则进行初始化
        if (user == null) {
            user = EaseUser(username)
            EaseCommonUtils.setUserInitialLetter(user)
        } else {
            if (TextUtils.isEmpty(user.avatar)) {//如果名字为空，则显示环信号码
                user.nickname = user.username
            }
        }
        return user
    }

    private fun initContactList() {
        val source : List<EaseUserInfoEntity>? = DBInit.getInstance().getAppDatabase()?.getEaseUserDao()?.getAll()
        if (source != null && !source.isEmpty()) {
            contactList.clear()
            for (entity : EaseUserInfoEntity in source) {
                val user = EaseUser(entity.userNo)
                user.avatar = entity.photo
                user.nickname = entity.name
                contactList[entity.userNo] = user
            }
        }
    }

    fun insertUser(users: List<EaseUser>) {
        val temp: MutableList<EaseUserInfoEntity> = arrayListOf()
        for(user : EaseUser in users) {
            val oldUser : EaseUser? = contactList[user.username]
            if(oldUser != null) {
                if(TextUtils.isEmpty(user.nickname) && TextUtils.isEmpty(user.avatar)) {
                    continue
                } else if(user.avatar == oldUser.avatar && user.nickname == oldUser.nickname) {
                    continue
                }
            }
            temp.add(EaseUserInfoEntity(user.username, user.avatar, user.nickname))
            contactList[user.username] = user
        }

        if(temp.isEmpty()) return
        
        RoomClient.getInstance().create(FlowableOnSubscribe {
            val result = DBInit.getInstance().getAppDatabase()?.getEaseUserDao()?.insertOrReplace(temp)
            if (result != null) {
                it.onNext(result)
            }
            it.onComplete()
        }, object : BaseFlowableSubscriber<List<Long>>() {
            override fun onBaseNext(t: List<Long>?) {
                if (t != null && !t.isEmpty()) {
                    LoggerUtils.error("insert", t.toString())
                }
            }

            override fun onError(e: ActionException?) {
                LoggerUtils.error("insert", e.toString())
            }
        })
    }
}
