package com.example.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhbj.base.BasePager;

public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		TextView textView = new TextView(mActivity);
		textView.setText("智慧服务");
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(22);
		textView.setTextColor(Color.RED);
		flContent.addView(textView);
		tvTitle.setText("生活");
		ibSlid.setVisibility(View.VISIBLE);
	}

}
