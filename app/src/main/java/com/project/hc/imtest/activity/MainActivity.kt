package com.project.hc.imtest.activity

import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.base.FragmentAdapter
import com.example.hongcheng.common.util.ScreenUtils
import com.project.hc.imtest.R
import com.project.hc.imtest.chat.ChatUtils
import com.project.hc.imtest.chat.CommonCallback
import com.project.hc.imtest.chat.CustomConversationListFragment
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BasicActivity() {

    private var actionBar: ActionBar? = null
    private lateinit var mAdapter: FragmentAdapter
    private val fragments: MutableList<Fragment> = arrayListOf()
    private val titles: MutableList<String> = arrayListOf()

    override fun getLayoutResId(): Int {
        return R.layout.content_main
    }

    override fun initView() {
        setSupportActionBar(tb_main)
        actionBar = supportActionBar
//        actionBar?.setHomeAsUpIndicator(R.mipmap.ic_menu)
//        actionBar?.setDisplayHomeAsUpEnabled(true)
        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.colorBase))

        setAbTitle(R.string.home)

        operateLoadingDialog(true)
        ChatUtils.loginChat(this, "18502729006", "137954682", object : CommonCallback{
            override fun onSuccess() {
                runOnUiThread {
                    initTab()
                    operateLoadingDialog(false)
                }
            }

            override fun onFail(message: String?) {
                runOnUiThread {
                    operateLoadingDialog(false)
                }
            }
        })
    }

    private fun initTab() {
        vp_main.removeAllViews()
        tabs_main.removeAllTabs()

        titles.add("环信")
        titles.add("通讯录")
        titles.add("发现")
        titles.add("我")

        val conversationListFragment = CustomConversationListFragment()
        conversationListFragment.setConversationListItemClickListener { conversation ->
            ChatUtils.goToChat(this@MainActivity, conversation.conversationId())
        }
        fragments.add(conversationListFragment)
        fragments.add(CustomConversationListFragment())
        fragments.add(CustomConversationListFragment())
        fragments.add(CustomConversationListFragment())

        mAdapter = FragmentAdapter(supportFragmentManager)
        mAdapter.mTitles = titles
        mAdapter.mFragments = fragments

        vp_main.adapter = mAdapter
        vp_main.offscreenPageLimit = titles.size - 1
        tabs_main.setupWithViewPager(vp_main)

        for (i in 0..3) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_tab_bottom, tabs_main, false)
            val iconView = itemView.findViewById<ImageView>(R.id.iv_tab_item)
            iconView.setBackgroundResource(R.drawable.selector_tab_img_first)
            val txtView = itemView.findViewById<TextView>(R.id.tv_tab_item)
            txtView.text = titles[i]
            tabs_main.getTabAt(i)?.customView = itemView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tb_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_message -> {
            }
            R.id.action_fav -> {
            }
            R.id.action_search -> {

            }
            else -> {
            }
        }
        return true
    }

    private fun setAbTitle(resId: Int) {
        actionBar?.setTitle(resId)
    }

    private fun setAbTitle(title: String) {
        actionBar?.title = title
    }

}
