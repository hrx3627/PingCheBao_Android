package com.pingchebao.android.adapter;

import java.util.ArrayList;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pingchebao.android.enty.BaseOrder;
import com.pingchebao.android.enty.CityOrder;

/**
 * 功能：同城适配器
 * @ahthor：黄荣星
 * @date:2014年6月3日
 * @version::V1.0
 */
public class CityAdapter extends BaseAdapter {
	private ArrayList<BaseOrder> mArrayList;
	private Context mContext;

	static class ViewHolder {
		private TextView startAdrTv;
		private TextView endAdrTv;

	}

	public CityAdapter(Context context, ArrayList<BaseOrder> mArrayList) {
		super();
		this.mContext = context;
		this.mArrayList = mArrayList;
	}

	@Override
	public int getCount() {
		if (mArrayList == null)
			mArrayList = new ArrayList<BaseOrder>();
		return mArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		CityOrder cityOrder = (CityOrder) mArrayList.get(position);
		viewHolder.startAdrTv.setText(cityOrder.mStartAdr);
		viewHolder.endAdrTv.setText(cityOrder.mEndAdr);

		return convertView;
	}
}
