package com.project.hc.imtest.chat;

import android.view.View;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.project.hc.imtest.R;

/**
 * Created by zhangzhao on 2017/3/1.
 */

public class CustomConversationListFragment extends EaseConversationListFragment {

    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
        getView().findViewById(R.id.il_search_bar).setVisibility(View.GONE);
    }
}
