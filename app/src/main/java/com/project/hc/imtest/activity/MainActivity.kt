package com.project.hc.imtest.activity

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
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
import com.project.hc.imtest.fragment.PersonFragment
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BasicActivity() {

    private var actionBar: ActionBar? = null
    private lateinit var mAdapter: FragmentAdapter
    private val fragments: MutableList<Fragment> = arrayListOf()
    private val titles: MutableList<String> = arrayListOf()
    private val tabResId: Array<Int> = arrayOf(
        R.drawable.selector_tab_img_first,
        R.drawable.selector_tab_img_two,
        R.drawable.selector_tab_img_three,
        R.drawable.selector_tab_img_four
    )

    override fun getLayoutResId(): Int {
        return R.layout.content_main
    }

    override fun initView() {
        setSupportActionBar(tb_main)
        actionBar = supportActionBar
        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.colorBase))

        setAbTitle(R.string.home)

        operateLoadingDialog(true)
        ChatUtils.loginChat(this, "18502729006", "137954682", object : CommonCallback {
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

        titles.add(getString(R.string.home))
        titles.add(getString(R.string.message))
        titles.add(getString(R.string.service))
        titles.add(getString(R.string.person))

        val conversationListFragment = CustomConversationListFragment()
        conversationListFragment.setConversationListItemClickListener { conversation ->
            ChatUtils.goToChat(this@MainActivity, conversation.conversationId(), conversation.type)
        }
        fragments.add(conversationListFragment)
        fragments.add(CustomConversationListFragment())
        fragments.add(CustomConversationListFragment())
        fragments.add(PersonFragment())

        mAdapter = FragmentAdapter(supportFragmentManager)
        mAdapter.mTitles = titles
        mAdapter.mFragments = fragments

        vp_main.adapter = mAdapter
        vp_main.offscreenPageLimit = titles.size - 1
        tabs_main.setupWithViewPager(vp_main)

        for (i in 0..3) {
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_tab_bottom, tabs_main, false)
            val iconView = itemView.findViewById<ImageView>(R.id.iv_tab_item)
            iconView.setImageResource(tabResId[i])
            val txtView = itemView.findViewById<TextView>(R.id.tv_tab_item)
            txtView.text = titles[i]
            tabs_main.getTabAt(i)?.customView = itemView
        }

        vp_main.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position >= 3) {
                    setAbTitle("")
                } else {
                    setAbTitle(titles[position])
                }
            }
        })
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
