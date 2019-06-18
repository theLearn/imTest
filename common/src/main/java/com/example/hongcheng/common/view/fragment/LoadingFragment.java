package com.example.hongcheng.common.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.view.spinkit.SpinKitView;
import com.example.hongcheng.common.view.spinkit.SpriteFactory;
import com.example.hongcheng.common.view.spinkit.Style;
import com.example.hongcheng.common.view.spinkit.sprite.SpriteContainer;

/**
 * Created by hongcheng on 17/8/21.
 */
public class LoadingFragment extends Dialog implements DialogInterface.OnKeyListener {

	public LoadingFragment( @NonNull Context context) {
		this(context, R.style.Loading_Dialog);
	}

	public LoadingFragment( @NonNull Context context, int themeResId) {
		super(context, themeResId);
	}

	public LoadingFragment(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
		setContentView(R.layout.loading_dialog);
		setCanceledOnTouchOutside(false); // 外部点击取消

		final Window window = getWindow();
		if (window != null)
		{
			window.setWindowAnimations(R.style.AnimFade2);
			final WindowManager.LayoutParams lp = window.getAttributes();
			DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
			lp.gravity = Gravity.CENTER_VERTICAL; // 紧贴底部
			lp.width = d.widthPixels * 1 / 2;
			lp.height = d.widthPixels * 1 / 2;
			window.setAttributes(lp);
		}

		setOnKeyListener(this);

		initView();
	}
	
	private void initView()
	{
	    SpinKitView spinKitView = findViewById(R.id.spin_kit_loading);
        Style style = Style.values()[7];
        SpriteContainer drawable = (SpriteContainer) SpriteFactory.create(style);
        drawable.setColor(getContext().getResources().getColor(R.color.white));
        spinKitView.setIndeterminateDrawable(drawable);
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(isShowing())
			{
				dismiss();
				Context context = getContext();
				if(context instanceof Activity) {
					Activity activity = (Activity) context;
					activity.finish();
				}
			}

			return true;
		}

		return false;
	}
}
