package com.project.hc.imtest.fragment


import android.content.Intent
import android.view.View
import com.example.hongcheng.common.base.BasicFragment
import com.example.hongcheng.common.util.ImageLoadUtils
import com.example.hongcheng.common.util.ValidateUtils
import com.example.hongcheng.common.util.ViewUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.activity.*
import kotlinx.android.synthetic.main.fragment_person.*

class PersonFragment : BasicFragment(), View.OnClickListener {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_person
    }

    override fun initView() {
        ImageLoadUtils.bindImageUrlForRound(iv_user_photo, "", R.mipmap.icon_photo_default)
        tv_person_account.text =
                String.format(getString(R.string.person_account_show), ValidateUtils.phoneNoHide("18502729006"))

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
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.ll_person_user_info -> {
                startActivity(Intent(activity, PersonInfoActivity::class.java))
            }
            R.id.ll_person_package -> {
                startActivity(Intent(activity, PackageActivity::class.java))
            }
            R.id.ll_person_get_cash -> {
                startActivity(Intent(activity, MyGetActivity::class.java))
            }
            R.id.ll_person_red_record -> {
                startActivity(Intent(activity, RedPackageHistoryActivity::class.java))
            }
            R.id.ll_person_cash -> {
            }
            R.id.ll_person_setting -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }
            R.id.ll_person_about -> {
                startActivity(Intent(activity, AboutActivity::class.java))
            }
            R.id.ll_person_call -> {
                startActivity(Intent(activity, IntroduceActivity::class.java))
            }
            else -> {

            }
        }
    }
}
