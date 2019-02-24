package com.project.hc.imtest.chat;


import com.example.hongcheng.common.base.BasicActivity;
import com.example.hongcheng.common.util.ScreenUtils;
import com.project.hc.imtest.R;

/**
 * 聊天对话界面
 */
public class CustomChatActivity extends BasicActivity {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_container;
    }

    @Override
    public void initView() {
        ScreenUtils.setLightStatusBar(this, true);
        ScreenUtils.setWindowStatusBarColor(this, R.color.bar_default);
        CustomChatFragment chatFragment = new CustomChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container, chatFragment).commit();
    }
}
