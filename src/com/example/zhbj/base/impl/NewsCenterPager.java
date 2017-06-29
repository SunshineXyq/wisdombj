package com.example.zhbj.base.impl;

import java.util.ArrayList;
import java.util.List;

import utils.CacheUtils;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhbj.MainActivity;
import com.example.zhbj.base.BaseMenuDetailPager;
import com.example.zhbj.base.BasePager;
import com.example.zhbj.base.impl.menu.InteractMenuDetailPager;
import com.example.zhbj.base.impl.menu.NewsMenuDetailPager;
import com.example.zhbj.base.impl.menu.PhotosMenuDetailPager;
import com.example.zhbj.base.impl.menu.TopicMenuDetailPager;
import com.example.zhbj.domain.NewsMenu;
import com.example.zhbj.global.GlobalConstants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import fragment.LeftFragment;

public class NewsCenterPager extends BasePager {
	
	private List<BaseMenuDetailPager> mMenuDetailPagers;
	private NewsMenu mNewsData;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}
	
	@Override
	public void initData() {
		ibSlid.setVisibility(View.VISIBLE);
		String cache = CacheUtils.getCache(GlobalConstants.CATEGORY_URL, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			processData(cache);
		} 
		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalConstants.CATEGORY_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						System.out.println("解析前" + result);
						processData(result);
						CacheUtils.setCache(GlobalConstants.CATEGORY_URL, result, mActivity);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println(msg);
					}
				});
	}

	protected void processData(String json) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(json, NewsMenu.class);
		System.out.println(mNewsData);
		MainActivity mainUI = (MainActivity) mActivity;
		LeftFragment leftFragment = mainUI.getLeftFragment();
		leftFragment.setMenuData(mNewsData.data);
		
		mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
		mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
		mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity));
		mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));
		setCurrentDetailPager(0);
		
	}
	
	public void setCurrentDetailPager(int position) {
		BaseMenuDetailPager pager = mMenuDetailPagers.get(position);
		flContent.removeAllViews();
		flContent.addView(pager.mRootView);
		pager.initData();
		tvTitle.setText(mNewsData.data.get(position).title);
	}
}
