package com.project.hc.imtest.chat;

import android.content.Context;
import android.content.Intent;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;

public final class ChatUtils {

    private ChatUtils() {
    }

    /**
     * 获取所有未读消息数量
     */
    public static int getUnreadMsgCounts() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    /**
     * 获取所有未读消息数量并处理99以上消息
     */
    public static String getFormatUnreadMsgCounts() {
        int unreadCounts = getUnreadMsgCounts();
        if (unreadCounts > 99)
            return "99+";
        return unreadCounts + "";
    }

    /**
     * 开始聊天
     */
    public static void goToChat(Context context, String id, String title, EMConversation.EMConversationType type) {
        int typeId = EaseConstant.CHATTYPE_SINGLE;
        if (type == EMConversation.EMConversationType.GroupChat) {
            typeId = EaseConstant.CHATTYPE_GROUP;
        } else if (type == EMConversation.EMConversationType.ChatRoom) {
            typeId = EaseConstant.CHATTYPE_CHATROOM;
        }

        Intent i = new Intent(context, CustomChatActivity.class);
        i.putExtra(EaseConstant.EXTRA_USER_ID, id);
        i.putExtra(EaseConstant.EXTRA_CHAT_TYPE, typeId);
        i.putExtra(EaseConstant.EXTRA_TITLE, title);
        context.startActivity(i);
    }

    /**
     * 登录聊天
     */
    public static void loginChat(final Context context, final String username, final String password, final CommonCallback callback) {
        if (EMClient.getInstance().isLoggedInBefore() && username.equals(EMClient.getInstance().getCurrentUser())) {
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            callback.onSuccess();
            return;
        }
        //杀进程切换账号登录，需先退出原账号登出
        if (EMClient.getInstance().isLoggedInBefore()) {
            logoutChat(false, new CommonCallback() {
                @Override
                public void onSuccess() {
                    loginChat(context, username, password, callback);
                }

                @Override
                public void onFail(String message) {
                    callback.onFail("注销失败");
                }
            });
            return;
        }
        EMClient.getInstance().login(username, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                callback.onSuccess();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                callback.onFail(message);
            }
        });
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public static void logoutChat(boolean unbindDeviceToken, final CommonCallback callback) {
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String error) {
                callback.onFail(error);
            }
        });
    }
}
