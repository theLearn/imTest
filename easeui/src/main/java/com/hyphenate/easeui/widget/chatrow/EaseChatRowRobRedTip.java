package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.hongcheng.common.util.SPUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;

public class EaseChatRowRobRedTip extends EaseChatRow{
    private TextView robRedTip;

    public EaseChatRowRobRedTip(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
		inflater.inflate( R.layout.ease_row_rob_red_tip, this);
	}

	@Override
	protected void onFindViewById() {
        robRedTip = findViewById(R.id.tv_rob_red_tip);
    }

    @Override
    public void onSetUpView() {
        String robRedName = message.getStringAttribute("robRedName", "");
        String sendName = message.getStringAttribute("sendName", "");
        String robRedId = message.getStringAttribute("robRedId", "");
        String sendRedId = message.getStringAttribute("sendRedId", "");
        String userId = SPUtils.getStringFromSP(context, "userId");
        StringBuilder sb = new StringBuilder();
        if(userId.equals(robRedId)) {
            sb.append("你");
        } else {
            sb.append(robRedName);
        }
        sb.append("领取了");
        if(sendRedId.equals(robRedId)) {
            sb.append("自己");
        } else if(userId.equals(sendRedId)) {
            sb.append("你");
        }else {
            sb.append(sendName);
        }
        sb.append("的");

        robRedTip.setText(sb.toString());
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    private void onMessageCreate() {
    }

    private void onMessageSuccess() {
    }

    private void onMessageError() {
    }

    private void onMessageInProgress() {
    }
}
