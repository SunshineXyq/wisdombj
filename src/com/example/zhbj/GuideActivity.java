package com.example.zhbj;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class GuideActivity extends Activity {

	private int[] mImageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	private List<ImageView> mImageViewList;
	private ViewPager mViewPager;
	private LinearLayout llContainer;
	private ImageView ivPointRed;
	private int mPointDis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		mViewPager = (ViewPager) findViewById(R.id.vp_guide);
		Button btnStart = (Button) findViewById(R.id.btn_start);
		ivPointRed = (ImageView) findViewById(R.id.iv_point_red);
		llContainer = (LinearLayout) findViewById(R.id.ll_container);

		initData();
		GuideAdapter adapter = new GuideAdapter();
		mViewPager.setAdapter(adapter);
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

		ivPointRed.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					
					@Override
					public void onGlobalLayout() {
						ivPointRed.getViewTreeObserver().removeGlobalOnLayoutListener(this);
						mPointDis = llContainer.getChildAt(1).getLeft()
								- llContainer.getChildAt(0).getLeft();
						System.out.println("间距"+mPointDis);
					}
				});
	}

	private void initData() {
		// TODO Auto-generated method stub
		mImageViewList = new ArrayList<ImageView>();
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(imageView);

			ImageView point = new ImageView(this);
			point.setImageResource(R.drawable.shape_point_gray);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (i > 0) {
				params.leftMargin = 20;
			}
			point.setLayoutParams(params);
			llContainer.addView(point);
		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = mImageViewList.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
