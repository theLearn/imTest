package com.project.hc.imtest.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.hongcheng.common.lifecycle.ActivityLifecycleImpl
import com.example.hongcheng.common.util.SPUtils
import com.project.hc.imtest.api.ApiConstants
import com.project.hc.imtest.chat.ChatUtils
import com.project.hc.imtest.chat.CommonCallback


class LoginConflictActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenInvalid()
        ChatUtils.logoutChat(false, object : CommonCallback {
            override fun onSuccess() {
            }

            override fun onFail(message: String) {
            }
        })
    }

    private fun tokenInvalid() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("警告")
        builder.setMessage("您的账号在其他设备上登录，请重新登录！")
        builder.setCancelable(false)
        builder.setPositiveButton("确定") { dialog, which ->
            SPUtils.putValueToSP(this, ApiConstants.AUTOLOGIN, false)
            ActivityLifecycleImpl.getInstance().finishAll()
            startActivity(Intent(this, LoginActivity::class.java)) }
        builder.setNegativeButton(
            "取消"
        ) { dialog, which ->
            ActivityLifecycleImpl.getInstance().finishAll()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}