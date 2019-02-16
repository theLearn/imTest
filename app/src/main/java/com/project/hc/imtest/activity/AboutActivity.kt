package com.project.hc.imtest.activity

import android.view.View
import com.project.hc.imtest.R


class AboutActivity : AppCommonActivity(){

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.person_about_us
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_about
    }

    override fun initBodyView(view: View) {
    }
}