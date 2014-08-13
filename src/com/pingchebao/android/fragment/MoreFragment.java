package com.pingchebao.android.fragment;

import com.hengtong.library.utils.LogControl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class MoreFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogControl.e("hrx", "MoreFragment==>onCreate()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogControl.e("hrx", "MoreFragment==>onCreateView()");
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
		v.setText("¡ƒÃÏΩÁ√Ê");
		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
		fl.addView(v);
		return fl;
	}
}
