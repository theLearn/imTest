package com.project.hc.imtest.fragment


import android.view.View
import com.example.hongcheng.common.base.BasicFragment
import com.example.hongcheng.common.util.ImageLoadUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.fragment_person.*

class PersonFragment : BasicFragment(), View.OnClickListener {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_person
    }

    override fun initView() {
        ImageLoadUtils.bindImageUrlForRound(iv_user_photo, "", R.mipmap.icon_photo_default)
        tv_person_account.text = String.format(getString(R.string.person_account_show), ValidateUtils.phoneNoHide("18502729006"))

        ll_person_user_info.setOnClickListener(this)
        ll_person_package.setOnClickListener(this)
        ll_person_get_cash.setOnClickListener(this)
        ll_person_red_record.setOnClickListener(this)
        ll_person_cash.setOnClickListener(this)
        ll_person_setting.setOnClickListener(this)
        ll_person_about.setOnClickListener(this)
        ll_person_call.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(ViewUtils.isFastClick()) return
        when(view?.id) {
            R.id.ll_person_user_info ->{}
            R.id.ll_person_package ->{}
            R.id.ll_person_get_cash ->{}
            R.id.ll_person_red_record ->{}
            R.id.ll_person_cash ->{}
            R.id.ll_person_setting ->{}
            R.id.ll_person_about ->{}
            R.id.ll_person_call ->{}
            else -> {

            }
        }
    }
}
