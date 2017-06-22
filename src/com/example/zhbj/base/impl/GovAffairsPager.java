package com.example.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhbj.base.BasePager;

public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		TextView textView = new TextView(mActivity);
		textView.setText("政务");
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(22);
		textView.setTextColor(Color.RED);
		flContent.addView(textView);
		tvTitle.setText("人口管理");
		ibSlid.setVisibility(View.VISIBLE);
	}

}
