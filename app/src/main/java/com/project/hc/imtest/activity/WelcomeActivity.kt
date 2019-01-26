package com.project.hc.imtest.activity

import android.content.Intent
import android.os.Handler
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.view.citylist.CityData
import com.project.hc.imtest.R


class WelcomeActivity : BasicActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        Handler().postDelayed({
            CityData.getInstance().init()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}