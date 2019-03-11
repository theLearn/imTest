package com.project.hc.imtest.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.example.hongcheng.common.util.ToastUtils;
import com.example.hongcheng.data.retrofit.ActionException;
import com.example.hongcheng.data.retrofit.BaseSubscriber;
import com.example.hongcheng.data.retrofit.RetrofitClient;
import com.example.hongcheng.data.retrofit.RetrofitManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.project.hc.imtest.R;
import com.project.hc.imtest.activity.RedPackageSendActivity;
import com.project.hc.imtest.api.ApiRetrofit;
import com.project.hc.imtest.application.BaseApplication;
import com.project.hc.imtest.fragment.OpenRedPackageFragment;
import com.project.hc.imtest.model.GroupInfo;

public class CustomChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {
    protected int[] itemIds = { ITEM_RED_PACKAGE};
    protected int[] itemStrings = { R.string.red_package};
    protected int[] itemdrawables = { R.mipmap.icon_send_red};
    private  boolean isCl;

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
        if(EaseConstant.CHATTYPE_SINGLE != chatType) {
            inputMenu.forbiddenWords();
        } else {
            inputMenu.enableToggleMore(true);
        }

        getGroupInfo();
    }

    private void getGroupInfo() {
        RetrofitClient.getInstance().map(
                RetrofitManager.createRetrofit(BaseApplication.getInstance(), ApiRetrofit.class)
                    .getGroupInfoById(toChatUsername), new BaseSubscriber<GroupInfo> () {
                    @Override
                    public void onBaseNext(GroupInfo groupInfo) {
                        isCl = "2".equals(groupInfo.getType()) ;
                    }

                    @Override
                    public void onError(ActionException e) {
                        ToastUtils.show(BaseApplication.getInstance(), e.getErrorMessage());
                 }
        });
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
        if (!TextUtils.isEmpty(message.getStringAttribute("hb_id",""))) {
            OpenRedPackageFragment openRedPackageFragment = new OpenRedPackageFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isCl", isCl);
            bundle.putParcelable("message", message);
            openRedPackageFragment.setArguments(bundle);
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
        intent.putExtra("isCl", isCl);
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
