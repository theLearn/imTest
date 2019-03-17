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
import com.google.gson.Gson
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMMessage.ChatType
import com.project.hc.imtest.R
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import com.project.hc.imtest.model.GroupInfo
import com.project.hc.imtest.model.RedDetailInfo
import com.project.hc.imtest.model.SendRedInfo
import kotlinx.android.synthetic.main.body_red_pacage_setting.*


class RedPackageSendActivity : AppCommonActivity(), View.OnClickListener, TextWatcher {
    private lateinit var mGroupInfo: GroupInfo
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
        mGroupInfo = intent.getParcelableExtra("groupInfo")
        toChatUsername = mGroupInfo.gid
        isCl = "2" == mGroupInfo.type
        bt_confirm_red_package_setting.setOnClickListener(this)
        et_red_package_setting_amount.addTextChangedListener(this)
        et_red_package_setting_ws.addTextChangedListener(this)
        ll_red_package_setting_ws.visibility = if(isCl) View.VISIBLE else View.GONE
        et_red_package_setting_amount.hint = String.format(getString(R.string.hint_red_package_amount_input), mGroupInfo.minMoney, mGroupInfo.maxMoney)
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
            RetrofitClient.getInstance().map<RedDetailInfo>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .sendClRed(et_red_package_setting_amount.text.toString().trim(), toChatUsername, et_red_package_setting_ws.text.toString().trim()), object : BaseSubscriber<RedDetailInfo>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : RedDetailInfo) {
                        operateLoadingDialog(false)
                        sendRedPackage(obj.hb_id)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }))
    }

    private fun sendJl() {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<RedDetailInfo>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .sendJlRed(et_red_package_setting_amount.text.toString().trim(), toChatUsername), object : BaseSubscriber<RedDetailInfo>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : RedDetailInfo) {
                        operateLoadingDialog(false)
                        sendRedPackage(obj.hb_id)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }))
    }

    private fun sendRedPackage(hb_id : String) {
        val sendInfo = SendRedInfo()
        sendInfo.mobile = BaseApplication.getInstance()?.loginInfo?.mobile!!
        sendInfo.nickname = BaseApplication.getInstance()?.loginInfo?.nickname!!
        sendInfo.photo = BaseApplication.getInstance()?.loginInfo?.photo!!
        sendInfo.type = "red"
        sendInfo.redCode = hb_id
        sendInfo.money = et_red_package_setting_amount.text.toString().trim()
        if(isCl) {
            sendInfo.redType = "cailei"
            sendInfo.thunder = et_red_package_setting_ws.text.toString().trim()
        } else {
            sendInfo.redType = "jielong"
            sendInfo.jlDes = if("1" == mGroupInfo.jl) "最小接龙" else "最大接龙"
        }

        //发送扩展消息
        val message = EMMessage.createTxtSendMessage(Gson().toJson(sendInfo).toString(), toChatUsername)
        //增加自己的属性
        message.setAttribute("mobile", sendInfo.mobile)
        message.setAttribute("nickname", sendInfo.nickname)
        message.setAttribute("photo", sendInfo.photo)
        message.setAttribute("type", "red")
        message.setAttribute("redCode", hb_id)
        message.setAttribute("money", et_red_package_setting_amount.text.toString().trim())
        if(isCl) {
            message.setAttribute("redType", "cailei")
            message.setAttribute("thunder", et_red_package_setting_ws.text.toString().trim())
        } else {
            message.setAttribute("redType", "jielong")
            message.setAttribute("jlDes", if("1" == mGroupInfo.jl) "最小接龙" else "最大接龙")
        }

        //设置群聊和聊天室发送消息
        message.chatType = ChatType.GroupChat
        //发送扩展消息
        EMClient.getInstance().chatManager().sendMessage(message)
    }
}