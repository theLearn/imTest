package com.project.hc.imtest.fragment


import android.content.Intent
import android.view.View
import com.example.hongcheng.common.base.BasicFragment
import com.example.hongcheng.common.util.ViewUtils
import com.hyphenate.chat.EMConversation
import com.project.hc.imtest.R
import com.project.hc.imtest.activity.MsgListActivity
import com.project.hc.imtest.activity.WfIntroduceActivity
import com.project.hc.imtest.chat.ChatUtils
import kotlinx.android.synthetic.main.fragment_service.*

class ServiceFragment : BasicFragment(), View.OnClickListener {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_service
    }

    override fun initView() {
        ll_service_recharge_12_type.setOnClickListener(this)
        ll_service_cash_ye_type.setOnClickListener(this)
        ll_service_system_notify.setOnClickListener(this)
        ll_service_platform_notify.setOnClickListener(this)
        ll_service_jl_introduce.setOnClickListener(this)
        ll_service_cl_introduce.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.ll_service_recharge_12_type -> {
                ChatUtils.goToChat(activity, "73162089889793", getString(R.string.recharge_12_type), EMConversation.EMConversationType.Chat)
            }
            R.id.ll_service_cash_ye_type -> {
                ChatUtils.goToChat(activity, "73162089889793", getString(R.string.cash_ye_type), EMConversation.EMConversationType.Chat)
            }
            R.id.ll_service_system_notify -> {
                startActivity(Intent(activity, MsgListActivity::class.java))
            }
            R.id.ll_service_platform_notify -> {
                val intent= Intent(activity, MsgListActivity::class.java)
                intent.putExtra("type", MsgListActivity.PLATFORM_NOTIFY)
                startActivity(intent)
            }
            R.id.ll_service_jl_introduce -> {
                startActivity(Intent(activity, WfIntroduceActivity::class.java))
            }
            R.id.ll_service_cl_introduce -> {
                val intent= Intent(activity, WfIntroduceActivity::class.java)
                intent.putExtra("type", WfIntroduceActivity.WF_TYPE_CL)
                startActivity(intent)
            }
            else -> {

            }
        }
    }
}
