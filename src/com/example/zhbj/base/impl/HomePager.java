package com.example.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhbj.base.BasePager;

public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		TextView textView = new TextView(mActivity);
		textView.setText("首页");
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(22);
		textView.setTextColor(Color.RED);
		flContent.addView(textView);
		tvTitle.setText("智慧北京");
		ibSlid.setVisibility(View.INVISIBLE);
	}

}
