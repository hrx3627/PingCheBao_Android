package com.pingchebao.android;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	final int PAGE_COUNT = 3;
	private Context mContext;

	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.mContext = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {

		// Open FragmentTab1.java
		case 0:
		

			// Open FragmentTab2.java
		case 1:
			

			// Open FragmentTab3.java
		case 2:
		
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

}
