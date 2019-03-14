package com.project.hc.imtest.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hongcheng.common.util.ImageLoadUtils;
import com.example.hongcheng.common.util.ToastUtils;
import com.example.hongcheng.common.util.ViewUtils;
import com.example.hongcheng.data.retrofit.ActionException;
import com.example.hongcheng.data.retrofit.BaseSubscriber;
import com.example.hongcheng.data.retrofit.RetrofitClient;
import com.example.hongcheng.data.retrofit.RetrofitManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.project.hc.imtest.R;
import com.project.hc.imtest.activity.RedPackageDetailActivity;
import com.project.hc.imtest.api.ApiRetrofit;
import com.project.hc.imtest.application.BaseApplication;
import com.project.hc.imtest.model.RedDetailInfo;
import com.project.hc.imtest.model.RedPackageDetailInfo;

/**
 * Created by hongcheng on 17/8/21.
 */
public class OpenRedPackageFragment extends DialogFragment implements View.OnClickListener {
	protected Dialog dialogView;

	private ImageView open;
	private  TextView tip, viewDetail;
	private boolean isCl;
	private EMMessage message;
	private RedPackageDetailInfo redPackageDetailInfo;

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
		dialogView = new Dialog(getActivity(), R.style.Loading_Dialog);
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
		dialogView.setContentView(R.layout.fragment_open_red_package);
		dialogView.setCanceledOnTouchOutside(false); // 外部点击取消
		// 设置宽度为屏宽, 靠近屏幕底部。
		final Window window = dialogView.getWindow();
		if (window != null)
		{
			window.setWindowAnimations(R.style.AnimFade2);
			final WindowManager.LayoutParams lp = window.getAttributes();
			Point size = new Point();
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			display.getSize(size);
			lp.gravity = Gravity.CENTER_VERTICAL; // 紧贴底部
			lp.width = (int) (size.x * 0.75);
			lp.height = (int) (1.364 * size.x);
			window.setAttributes(lp);
		}

		initView();
		return dialogView;
	}
	
	private void initView()
	{
		message  = getArguments().getParcelable("message");
		redPackageDetailInfo  = getArguments().getParcelable("redDetail");
		isCl = getArguments().getBoolean("isCl", false);
		ImageView photo = dialogView.findViewById(R.id.iv_red_package_user_photo);
		open = dialogView.findViewById(R.id.iv_red_package_open);
		ImageView cancel = dialogView.findViewById(R.id.iv_red_package_cancel);

		TextView who = dialogView.findViewById(R.id.tv_red_package_who);
		tip = dialogView.findViewById(R.id.tv_red_package_tip);
		viewDetail = dialogView.findViewById(R.id.tv_red_package_view_detail);

		ImageLoadUtils.bindImageUrlForRound(photo, redPackageDetailInfo.getHb_data().getPic(), R.mipmap.icon_photo_default);
		who.setText(String.format(getString(R.string.who_red_package), redPackageDetailInfo.getHb_data().getNickname()));
		if("1".equals(redPackageDetailInfo.getIf_end())) {
			tip.setText("红包已过期");
			open.setVisibility(View.GONE);
			viewDetail.setVisibility(View.GONE);
		} else if(redPackageDetailInfo.getHb_data().getMoney().equals(redPackageDetailInfo.getTakeMoney())) {
			tip.setText("已抢完");
			open.setVisibility(View.INVISIBLE);
		} else {
			tip.setText("恭喜发财");
		}

		open.setOnClickListener(this);
		cancel.setOnClickListener(this);
		viewDetail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(ViewUtils.isFastClick()) {
			return;
		}
		switch (v.getId())
		{
			case R.id.iv_red_package_open :
				if(View.VISIBLE == open.getVisibility()) {
					openRed();
				}
				break;
			case R.id.iv_red_package_cancel :
				dismiss();
				break;
			case R.id.tv_red_package_view_detail :
				goToDetail ();
				break;
			default:
				break;
		}
	}

	private void goToDetail () {
		dismiss();
		Intent intent = new Intent(getActivity(), RedPackageDetailActivity.class);
		intent.putExtra("message", message);
		startActivity(intent);
	}

	private void openRed() {
		if(isCl) {
			openCl();
		} else {
			openJl();
		}
	}

	private void openCl() {
		RetrofitClient.getInstance().map(
				RetrofitManager.createRetrofit(BaseApplication.getInstance(), ApiRetrofit.class)
						.receiveClRed(message.getTo(), message.getStringAttribute("redCode", "")), new BaseSubscriber<RedDetailInfo>() {
					@Override
					public void onBaseNext(RedDetailInfo redDetailInfo) {
						message.setAttribute("rob", true);
						EMClient.getInstance().chatManager().updateMessage(message);
						goToDetail ();
					}

					@Override
					public void onError(ActionException e) {
						ToastUtils.show(BaseApplication.getInstance(), e.getErrorMessage());
					}
				});
	}

	private void openJl() {
		RetrofitClient.getInstance().map(
				RetrofitManager.createRetrofit(BaseApplication.getInstance(), ApiRetrofit.class)
						.receiveJlRed(message.getTo(), message.getStringAttribute("redCode", "")), new BaseSubscriber<RedDetailInfo>() {
					@Override
					public void onBaseNext(RedDetailInfo redDetailInfo) {
						message.setAttribute("rob", true);
						EMClient.getInstance().chatManager().updateMessage(message);
						goToDetail ();
					}

					@Override
					public void onError(ActionException e) {
						ToastUtils.show(BaseApplication.getInstance(), e.getErrorMessage());
					}
				});
	}
}