package com.project.hc.imtest.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.example.hongcheng.common.util.ToastUtils;
import com.example.hongcheng.common.view.fragment.LoadingFragment;
import com.example.hongcheng.data.retrofit.ActionException;
import com.example.hongcheng.data.retrofit.BaseSubscriber;
import com.example.hongcheng.data.retrofit.RetrofitClient;
import com.example.hongcheng.data.retrofit.RetrofitManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.project.hc.imtest.R;
import com.project.hc.imtest.activity.GroupMemberListActivity;
import com.project.hc.imtest.activity.RedPackageDetailActivity;
import com.project.hc.imtest.activity.RedPackageSendActivity;
import com.project.hc.imtest.api.ApiRetrofit;
import com.project.hc.imtest.application.BaseApplication;
import com.project.hc.imtest.fragment.OpenRedPackageFragment;
import com.project.hc.imtest.model.GroupInfo;
import com.project.hc.imtest.model.RedPackageDetailInfo;

import java.util.ArrayList;
import java.util.List;

public class CustomChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper, OpenRedPackageFragment.OnOverdueListener {
    private static final int SEND_RED_REQUEST_CODE = 777;
    protected int[] itemIds = {};
    protected int[] itemStrings = {};
    protected int[] itemdrawables = {};
    private  boolean isCl;
    private LoadingFragment mLoadingDialog;
    private GroupInfo mGroupInfo;

    private CardItemClickListener customMenuItemClickListener;

    @Override
    protected void initView() {
        customMenuItemClickListener = new CardItemClickListener();
        if(EaseConstant.CHATTYPE_SINGLE == chatType) {
            itemIds = new int[]{ ITEM_PICTURE};
            itemStrings = new int[] { R.string.attach_picture};
            itemdrawables = new int[] {R.drawable.ease_chat_image_selector};
        }
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
            getGroupInfo();
        }
    }

    private void getGroupInfo() {
        RetrofitClient.getInstance().map(
                RetrofitManager.createRetrofit(BaseApplication.getInstance(), ApiRetrofit.class)
                    .getGroupInfoById(toChatUsername), new BaseSubscriber<GroupInfo> () {
                    @Override
                    public void onBaseNext(GroupInfo groupInfo) {
                        mGroupInfo = groupInfo;
                        isCl = "2".equals(groupInfo.getType()) ;

                        EaseUser easeUser = new EaseUser(mGroupInfo.getGid());
                        easeUser.setAvatar(mGroupInfo.getPic());
                        easeUser.setNickname(mGroupInfo.getName());

                        List<EaseUser> users = new ArrayList<>();
                        users.add(easeUser);
                        BaseApplication.getInstance().insertUser(users);

                        if(false) {
                            itemIds = new int[]{ ITEM_RED_PACKAGE};
                            itemStrings = new int[] {  R.string.red_package};
                            itemdrawables = new int[] {R.mipmap.icon_send_red};
                            inputMenu.forbiddenWords();
                        } else {
                            itemIds = new int[]{ ITEM_PICTURE,ITEM_RED_PACKAGE};
                            itemStrings = new int[]{  R.string.attach_picture, R.string.red_package};
                            itemdrawables = new int[]{ R.drawable.ease_chat_image_selector, R.mipmap.icon_send_red};
                        }

                        registerExtendMenuItem();
                    }

                    @Override
                    public void onError(ActionException e) {
                        ToastUtils.show(BaseApplication.getInstance(), e.getErrorMessage());
                        registerExtendMenuItem();
                 }
        });
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        // 通过扩展属性，将userPic和userName发送出去。
        String userPic = BaseApplication.getInstance().getLoginInfo().getPhoto();
        if (!TextUtils.isEmpty(userPic)) {
            message.setAttribute("photo", userPic);
        }
        String userName = BaseApplication.getInstance().getLoginInfo().getNickname();
        if (!TextUtils.isEmpty(userName)) {
            message.setAttribute("nickname", userName);
        }
    }

    @Override
    public void onEnterToChatDetails() {
        Intent intent = new Intent(getActivity(), GroupMemberListActivity.class);
        intent.putExtra("model", mGroupInfo);
        startActivity(intent);
    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        String redCode = message.getStringAttribute("redCode","");
        if (message.getType() == EMMessage.Type.TXT && !TextUtils.isEmpty(redCode)) {
            if(message.getBooleanAttribute("rob", false)) {
                Intent intent = new Intent(getActivity(), RedPackageDetailActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("isCl", isCl);
                startActivity(intent);
            } else {
                getRedDetail(redCode, message);
            }
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

    @Override
    public void onOverdue() {
        messageList.refresh();//刷新消息数据
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
                case ITEM_TAKE_PICTURE:
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal();
                    break;
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
        intent.putExtra("groupInfo", mGroupInfo);
        startActivityForResult(intent, SEND_RED_REQUEST_CODE);
        inputMenu.hideExtendMenuContainer();
    }

    private void getRedDetail(String redCode, final EMMessage message) {
        operateLoadingDialog(true);
        RetrofitClient.getInstance().map(
                RetrofitManager.createRetrofit(BaseApplication.getInstance(), ApiRetrofit.class)
                        .getRedDetail(redCode, 0, 100), new BaseSubscriber<RedPackageDetailInfo> () {
                    @Override
                    public void onBaseNext(RedPackageDetailInfo obj) {
                        operateLoadingDialog(false);
                        OpenRedPackageFragment openRedPackageFragment = new OpenRedPackageFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isCl", isCl);
                        bundle.putParcelable("message", message);
                        bundle.putParcelable("redDetail", obj);
                        openRedPackageFragment.setArguments(bundle);
                        openRedPackageFragment.setListener(CustomChatFragment.this);
                        openRedPackageFragment.show(getFragmentManager(), "OpenRedPackageFragment");
                    }

                    @Override
                    public void onError(ActionException e) {
                        operateLoadingDialog(false);
                        ToastUtils.show(BaseApplication.getInstance(), e.getErrorMessage());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode && SEND_RED_REQUEST_CODE == requestCode) {
            messageList.refresh();//刷新消息数据
        }
    }

    private void operateLoadingDialog(Boolean isOpen) {
        if (isOpen && mLoadingDialog == null) {
            mLoadingDialog = new LoadingFragment();
        }

        boolean isShow = mLoadingDialog.getDialog() !=null && mLoadingDialog.getDialog() .isShowing();
        if(!isOpen) {
            if(isShow) {
                mLoadingDialog.dismiss();
            }
        } else if(!mLoadingDialog.isAdded() && !isShow) {
            mLoadingDialog.show(getActivity().getSupportFragmentManager(), "LoadingFragment");
        }
    }
}
