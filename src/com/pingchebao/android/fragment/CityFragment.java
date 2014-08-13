package com.pingchebao.android.fragment;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTRequestObject;
import com.hengtong.library.manager.HTAsyncNetManager;
import com.hengtong.library.utils.LogControl;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

public class CityFragment extends Fragment {
	private ProgressDialog mProgressDialog;
	private Context mContext;
	private UMSocialService mController;

	public CityFragment(Context mContext) {
		super();
		this.mContext = mContext;
	}

	private void initShare() {
		// 首先在您的Activity中添加如下成员变量
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		// 设置分享内容
		mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogControl.e("hrx", "CityFragment==>onCreate()");
		mProgressDialog = new ProgressDialog(mContext);
		initShare();
		doHttpGetNotices();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogControl.e("hrx", "CityFragment==>onCreateView()");
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
		TextView v = new TextView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		v.setLayoutParams(params);
		v.setLayoutParams(params);
		v.setGravity(Gravity.CENTER);
		v.setText("聊天界面");
		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
		fl.addView(v);
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		fl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// startActivity(new Intent(mContext, SpashActivity.class));
				mController.openShare(getActivity(), false);
			}
		});
		return fl;
	}

	private void doHttpGetNotices() {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetAnns");
		aRequestObject.setmToken("MUYyMzIwQzcxRUM2Qzk2QTk4MkYxNjdEMUMwN0IxNjVFMURDNTFEQzY4QjMwNDgzREJBNzE3RkIzQ0U0Q0U1N0RDREQxMTRBRkQ0NzE2QzE3OUYyMUU3Q0ExODE4QkMyQUQxQkZFMDlEN0U4QzA3MkM4MkVCOTVENDQ0QTU0NkQzM0RERUVEMEU5Mjk5NDU3QzJCNTEyMjZGQzc1RTgzNDhFRTU0MTc2REY1OUQ1NUE2OTQzOUFFRDVGNjkyMUE3RDU0OEVGNDUyNUNENUFBOTg4NEQ3MkYyRkE2MzZDQzJDQzkzNDgzQkIzMDlDRTJB");
		aRequestObject.setmTypeName("App");

		aRequestObject.setmParms("platform", 22);
		HTAsyncNetManager.post("http://192.168.18.171:8083/Android/Post", aRequestObject, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				super.onStart();
				mProgressDialog.show();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				mProgressDialog.dismiss();

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				LogControl.e("hrx", new String(responseBody).toString());

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

			}
		});
	}

}
