package com.project.hc.imtest.chat;


import android.Manifest;
import com.example.hongcheng.common.base.BasicActivity;
import com.example.hongcheng.common.util.ScreenUtils;
import com.example.hongcheng.common.util.ToastUtils;
import com.project.hc.imtest.R;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * 聊天对话界面
 */
public class CustomChatActivity extends BasicActivity {

    private CustomChatFragment chatFragment;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_container;
    }

    @Override
    public void initView() {
        ScreenUtils.setLightStatusBar(this, true);
        ScreenUtils.setWindowStatusBarColor(this, R.color.bar_default);
        chatFragment = new CustomChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment_container, chatFragment).commit();
    }

    @Override
    public void requestPermission(boolean isSuccess) {
        super.requestPermission(isSuccess);
        if(!isSuccess) {
            ToastUtils.show(this, "缺少必要权限");
        } else {
            if(chatFragment.isClickCamera()) {
                chatFragment.selectPicFromCamera();
            }
        }
    }
}
