package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;

public class EaseChatRowRedPackage extends EaseChatRow{
    private ImageView redImg;
    private TextView redDesc;

    public EaseChatRowRedPackage(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
				R.layout.ease_row_received_red_package : R.layout.ease_row_sent_red_package, this);
	}

	@Override
	protected void onFindViewById() {
        redImg = findViewById(R.id.iv_red_package);
        redDesc = findViewById(R.id.tv_red_desc);
    }

    @Override
    public void onSetUpView() {
        boolean hasRead = message.getBooleanAttribute("rob", false) || message.getBooleanAttribute("end", false) || message.getBooleanAttribute("overdue", false);
        if(message.direct() == EMMessage.Direct.RECEIVE) {
            redImg.setImageResource(hasRead ? R.drawable.ease_receive_red_package_rob : R.drawable.ease_receive_red_package);
        } else {
            redImg.setImageResource(hasRead ? R.drawable.ease_send_red_package_rob : R.drawable.ease_send_red_package);
        }

        redDesc.setTextColor(context.getResources().getColor(hasRead ? R.color.text_second_default : R.color.text_gray_99));
        String redType = message.getStringAttribute("redType", "jielong");
        StringBuilder sb = new StringBuilder();
        if("cailei".equals(redType)) {
            sb.append("踩雷红包  ");
            sb.append(message.getStringAttribute("money", "") + "-");
            sb.append(message.getStringAttribute("thunder", ""));
        } else if("jielong".equals(redType)) {
            sb.append(message.getStringAttribute("money", "") + "-");
            sb.append(message.getStringAttribute("jlDes", ""));
        } else {
            sb.append("聊吧红包");
        }

        redDesc.setText(sb.toString());
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
