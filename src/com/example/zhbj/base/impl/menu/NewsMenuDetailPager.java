package com.example.zhbj.base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhbj.MainActivity;
import com.example.zhbj.R;
import com.example.zhbj.base.BaseMenuDetailPager;
import com.example.zhbj.domain.NewsMenu.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuDetailPager extends BaseMenuDetailPager implements OnPageChangeListener{

	private ArrayList<NewsTabData> mNewsTabDatas;
	private ArrayList<TabDetailPager> mPagers;
	@ViewInject(R.id.indicator)
	private TabPageIndicator mIndicator;
	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;

	public NewsMenuDetailPager(Activity activity, ArrayList<NewsTabData> children) {
		super(activity);
		mNewsTabDatas = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
		ViewUtils.inject(this,view);
		return view;
	}
	
	@Override
	public void initData() {
		mPagers = new ArrayList<TabDetailPager>();
		for (int i = 0; i < mNewsTabDatas.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,mNewsTabDatas.get(i));
			mPagers.add(pager);
		}
		mViewPager.setAdapter(new NewsMenuDetailAdapter()); 
		mIndicator.setViewPager(mViewPager);
		mIndicator.setOnPageChangeListener(this);
	}
	
	class NewsMenuDetailAdapter extends PagerAdapter {
		
		@Override
		public CharSequence getPageTitle(int position) {
			String title = mNewsTabDatas.get(position).title;
			return title;
		}

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagers.get(position);
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == 0) {
			setSlidingMenuEnable(true);
		} else {
			setSlidingMenuEnable(false);
		}
	}
	
	protected void setSlidingMenuEnable(boolean enable) {
		MainActivity activity = (MainActivity) mActivity;
		SlidingMenu slidingMenu = activity.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	@OnClick(R.id.ib_next)
	private void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		currentItem++;
		mViewPager.setCurrentItem(currentItem);
	}
}
