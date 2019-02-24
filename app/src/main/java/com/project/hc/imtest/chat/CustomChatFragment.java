package com.project.hc.imtest.chat;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.project.hc.imtest.R;
import com.project.hc.imtest.activity.RedPackageSendActivity;
import com.project.hc.imtest.fragment.OpenRedPackageFragment;

public class CustomChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {
    protected int[] itemIds = { ITEM_RED_PACKAGE};
    protected int[] itemStrings = { R.string.red_package};
    protected int[] itemdrawables = { R.mipmap.icon_send_red};

    private CardItemClickListener customMenuItemClickListener;

    @Override
    protected void initView() {
        customMenuItemClickListener = new CardItemClickListener();
        super.initView();
    }

    protected void registerExtendMenuItem(){
        for(int i = 0; i < itemStrings.length; i++){
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], customMenuItemClickListener);
        }
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        setChatFragmentHelper(this);
        inputMenu.forbiddenWords();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        if (message.getType() == EMMessage.Type.TXT && message.getBooleanAttribute("red",false)) {
            OpenRedPackageFragment openRedPackageFragment = new OpenRedPackageFragment();
            openRedPackageFragment.show(getFragmentManager(), "OpenRedPackageFragment");
            return true;
        }
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

    class CardItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener{

        @Override
        public void onClick(int itemId, View view) {
            if(chatFragmentHelper != null){
                if(chatFragmentHelper.onExtendMenuItemClick(itemId, view)){
                    return;
                }
            }
            switch (itemId) {
                case ITEM_RED_PACKAGE:
                    toSendRed();
                    break;
                default:
                    break;
            }
        }

    }

    private void toSendRed() {
        Intent intent = new Intent(getActivity(), RedPackageSendActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, toChatUsername);
//        intent.putExtra("isCl", false);
        startActivityForResult(intent, 1);
        inputMenu.hideExtendMenuContainer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Activity.RESULT_OK == resultCode && 1 == requestCode) {
            messageList.refresh();//刷新消息数据
        }
    }
}
