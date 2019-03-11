package com.project.hc.imtest.activity

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMMessage.ChatType
import com.hyphenate.easeui.EaseConstant
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import kotlinx.android.synthetic.main.body_red_pacage_setting.*


class RedPackageSendActivity : AppCommonActivity(), View.OnClickListener, TextWatcher {
    var chatType : Int = EaseConstant.CHATTYPE_SINGLE
    var toChatUsername : String? = null
    var isCl : Boolean = true

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_send_red_package
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_red_pacage_setting
    }

    override fun initBodyView(view: View) {
        chatType = intent.getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE)
        toChatUsername = intent.getStringExtra(EaseConstant.EXTRA_USER_ID)
        isCl = intent.getBooleanExtra("isCl", false)
        bt_confirm_red_package_setting.setOnClickListener(this)
        et_red_package_setting_amount.addTextChangedListener(this)
        et_red_package_setting_ws.addTextChangedListener(this)
        ll_red_package_setting_ws.visibility = if(isCl) View.VISIBLE else View.GONE
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.bt_confirm_red_package_setting -> {
                submit()
            }
            else -> {

            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        val amount = et_red_package_setting_amount.text.toString().trim()
        var isEnable = false
        if(!ValidateUtils.isEmpty(amount)) {
            tv_red_package_setting_amount_show.text = amount
            if(!isCl || !ValidateUtils.isEmpty(et_red_package_setting_ws.text.toString().trim())) {
                isEnable = true
            }
        }
        bt_confirm_red_package_setting.isEnabled = isEnable
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun submit() {
        if(isCl) {
            sendCl()
        } else {
            sendJl()
        }
    }

    private fun sendCl() {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .sendClRed(et_red_package_setting_amount.text.toString().trim(), toChatUsername, et_red_package_setting_ws.text.toString().trim()), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }))
    }

    private fun sendJl() {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .sendJlRed(et_red_package_setting_amount.text.toString().trim(), toChatUsername), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }))
    }

    private fun sendRedPackage() {
        //发送扩展消息
        val message = EMMessage.createTxtSendMessage("hh", toChatUsername)
        //增加自己的属性
        message.setAttribute("red", true)
        message.setAttribute("CARDS", "cards")
        message.setAttribute("USERNAME", "God-Eye")
        message.setAttribute("USERID", "1")
        message.setAttribute("USERHEADER", "")
        message.setAttribute("USERCITY", "青岛")
        //设置群聊和聊天室发送消息
        if (chatType === EaseConstant.CHATTYPE_GROUP) {
            message.chatType = ChatType.GroupChat
        } else if (chatType === EaseConstant.CHATTYPE_CHATROOM) {
            message.chatType = ChatType.ChatRoom
        }
        //发送扩展消息
        EMClient.getInstance().chatManager().sendMessage(message)
    }
}