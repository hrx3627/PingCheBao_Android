package com.pingchebao.android.fragment;

import android.content.Intent;
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

import com.hengtong.library.utils.LogControl;
import com.pingchebao.android.PictureMainActivity;

public class LongDistanceFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogControl.e("hrx", "LongDistanceFragment==>onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogControl.e("hrx", "LongDistanceFragment==>onCreateView()");
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
		v.setText("�������");
		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
		fl.addView(v);
		fl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), PictureMainActivity.class));
			}
		});
		return fl;
	}
}
