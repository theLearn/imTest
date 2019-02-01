package com.project.hc.imtest.activity

import android.view.View
import com.example.hongcheng.common.util.ImageLoadUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_person_info.*

class PersonInfoActivity : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_person_info
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_person_info
    }

    override fun initBodyView(view: View) {
        ImageLoadUtils.bindImageUrlForRound(iv_person_info_photo, "", R.mipmap.icon_photo_default)
        tv_person_info_nick.setText(R.string.person_nick_default)
        tv_person_info_phone.text = "18502729006"

        ll_person_info_photo.setOnClickListener(this)
        ll_person_info_nick.setOnClickListener(this)
        ll_person_info_phone.setOnClickListener(this)
        bt_logout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(ViewUtils.isFastClick()) return
        when(view?.id) {
            R.id.ll_person_info_photo -> {}
            R.id.ll_person_info_nick -> {}
            R.id.ll_person_info_phone -> {}
            R.id.bt_logout -> {}
            else -> {

            }
        }
    }
}