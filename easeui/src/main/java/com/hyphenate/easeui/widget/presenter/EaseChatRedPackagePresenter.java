package com.hyphenate.easeui.widget.presenter;

import android.content.Context;
import android.widget.BaseAdapter;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowRedPackage;

/**
 * Created by zhangsong on 17-10-12.
 */

public class EaseChatRedPackagePresenter extends EaseChatRowPresenter {

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowRedPackage(cxt, message, position, adapter);
    }

    @Override
    public void onBubbleClick(EMMessage message) {
    }
}
