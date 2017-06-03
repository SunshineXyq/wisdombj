package com.example.zhbj;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends Activity {

	private int[] mImageIds = new int[] { R.drawable.guide_1, R.drawable.guide_2,
			R.drawable.guide_3 };
	private List<ImageView> mImageViewList;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		mViewPager = (ViewPager) findViewById(R.id.vp_guide);
		Button btnStart = (Button) findViewById(R.id.btn_start);
		initData();
		GuideAdapter adapter = new GuideAdapter();
		mViewPager.setAdapter(adapter);
	}

	private void initData() {
		// TODO Auto-generated method stub
		mImageViewList = new ArrayList<ImageView>();
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(imageView);
		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = mImageViewList.get(position);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			
		}
	}

}
