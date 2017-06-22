package com.example.zhbj.base;

import com.example.zhbj.MainActivity;
import com.example.zhbj.R;
import com.example.zhbj.R.id;
import com.example.zhbj.R.layout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasePager {
	
	public Activity mActivity;
	public TextView tvTitle;
	public ImageButton ibSlid;
	public FrameLayout flContent;
	public View mRootView;
	
	public BasePager(Activity activity){
		mActivity = activity;
		mRootView = initView();
	} 
	
	public View initView(){
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		tvTitle = (TextView) view.findViewById(R.id.tv_title); 
		ibSlid = (ImageButton) view.findViewById(R.id.ib_slid);
		flContent = (FrameLayout) view.findViewById(R.id.fl_content);
		ibSlid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				toggle();
			}
		});
		return view;
	}
	
	protected void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
	
	public void initData(){
		
	}
}
