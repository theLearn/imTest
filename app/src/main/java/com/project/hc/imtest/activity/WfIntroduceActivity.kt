package com.project.hc.imtest.activity

import android.view.View
import com.project.hc.imtest.R
import kotlinx.android.synthetic.main.body_wf_introduce.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class WfIntroduceActivity : AppCommonActivity(){
    companion object {
        const val WF_TYPE_JL : Int = 0
        const val WF_TYPE_CL : Int = 1
    }

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_jl_introduce
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_wf_introduce
    }

    override fun initBodyView(view: View) {
        val type = intent.getIntExtra("type", WF_TYPE_JL)
        if(WF_TYPE_CL == type) {
            tv_app_common_title.setText(R.string.title_cl_introduce)
            tv_wf_introduce.setText(R.string.cl_introduce_tip)
            tv_wf_platform_tip.visibility = View.VISIBLE
        }
    }
}