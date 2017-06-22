package com.example.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhbj.base.BaseMenuDetailPager;

public class TopicMenuDetailPager extends BaseMenuDetailPager{

	public TopicMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView textView = new TextView(mActivity);
		textView.setText("专题");
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(22);
		textView.setTextColor(Color.RED);
		return textView;
	}

}
