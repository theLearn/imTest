package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowRobRedTip;

/**
 * Created by zhangsong on 17-10-12.
 */

public class EaseChatRobRedTipPresenter extends EaseChatRowPresenter {

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowRobRedTip(cxt, message, position, adapter);
    }

    @Override
    public void onBubbleClick(EMMessage message) {
    }
}
