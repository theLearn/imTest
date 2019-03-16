package com.project.hc.imtest.activity

import android.view.View
import com.project.hc.imtest.R
import com.project.hc.imtest.model.MsgInfo
import kotlinx.android.synthetic.main.body_notice_detail.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class NoticeDetailActivity : AppCommonActivity(){

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.platform_notify
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_notice_detail
    }

    override fun initBodyView(view: View) {
        val model = intent.getParcelableExtra<MsgInfo>("model")
        tv_app_common_title.text = model.title
        tv_notice_detail.text = model.content
    }
}