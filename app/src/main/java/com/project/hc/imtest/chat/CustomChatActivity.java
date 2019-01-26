package com.project.hc.imtest.chat;


import com.example.hongcheng.common.base.BasicActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
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
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container, chatFragment).commit();
    }
}
