package com.pingchebao.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 功能：
 * @ahthor：黄荣星
 * @date:2014年8月8日
 * @version::V1.0
 */
public class SpashActivity extends Activity implements OnPageChangeListener {
	private static final int[] welcomeImgs = { R.drawable.android1, R.drawable.android2, R.drawable.android3, R.drawable.android4 };

	private ViewPager mViewPager;
	private LinearLayout mPointsContainer;
	private List<View> mlistViews;
	private List<ImageView> mPointViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_guide_layout);
		initView();
		initPoints();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mPointsContainer = (LinearLayout) findViewById(R.id.point_cainter);

		mViewPager.setAdapter(new MyPagerAdapter());
		mViewPager.setPadding(0, 0, 0, 0);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initPoints() {
		for (int i = 0, len = welcomeImgs.length; i < len; i++) {
			ImageView point = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 0, 0, 0);
			point.setLayoutParams(params);
			if (i == 0) {
				point.setImageResource(R.drawable.act_welcome_focuse);
			} else {
				point.setImageResource(R.drawable.act_welcome_normal);
			}
			mPointsContainer.addView(point);
			if (mPointViews == null)
				mPointViews = new ArrayList<ImageView>();
			mPointViews.add(point);
		}

	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return welcomeImgs.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView img = new ImageView(SpashActivity.this);
			LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			img.setLayoutParams(mParams);
			img.setScaleType(ScaleType.FIT_XY);
			img.setImageResource(welcomeImgs[position]);
			container.addView(img);
			if (mlistViews == null)
				mlistViews = new ArrayList<View>();
			mlistViews.add(img);
			return img;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mlistViews.get(position));
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0, len = mPointViews.size(); i < len; i++) {
			ImageView img = mPointViews.get(i);
			if (arg0 == i) {
				img.setImageResource(R.drawable.act_welcome_focuse);
			} else {
				img.setImageResource(R.drawable.act_welcome_normal);
			}
		}
		Button nextBtn = (Button) findViewById(R.id.next_btn);
		if (arg0 == 3) {
			nextBtn.setVisibility(View.VISIBLE);
			nextBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
		} else {
			nextBtn.setVisibility(View.GONE);
		}
	}
}
