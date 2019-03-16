package com.project.hc.imtest.chat;

import android.view.View;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.project.hc.imtest.R;
import com.project.hc.imtest.application.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class CustomConversationListFragment extends EaseConversationListFragment implements EMMessageListener {

    @Override
    protected void setUpView() {
        super.setUpView();

        hideTitleBar();
        getView().findViewById(R.id.il_search_bar).setVisibility(View.GONE);
        EMClient.getInstance().chatManager().addMessageListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (EMMessage message : list) {
            //************接收并处理扩展消息***********************
            String userName = message.getStringAttribute("nickname", "");
            String userPic = message.getStringAttribute("photo", "");
            String hxIdFrom = message.getFrom();
            EaseUser easeUser = new EaseUser(hxIdFrom);
            easeUser.setAvatar(userPic);
            easeUser.setNickname(userName);

            List<EaseUser> users = new ArrayList<>();
            users.add(easeUser);
            BaseApplication.getInstance().insertUser(users);

            EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
        }
        refresh();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
