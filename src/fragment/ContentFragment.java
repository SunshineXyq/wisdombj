package fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.zhbj.MainActivity;
import com.example.zhbj.R;
import com.example.zhbj.base.BasePager;
import com.example.zhbj.base.impl.GovAffairsPager;
import com.example.zhbj.base.impl.HomePager;
import com.example.zhbj.base.impl.NewsCenterPager;
import com.example.zhbj.base.impl.SettingPager;
import com.example.zhbj.base.impl.SmartServicePager;
import com.example.zhbj.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	private NoScrollViewPager mViewPager;
	private List<BasePager> mPagers;
	private RadioGroup rgGroup;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_content);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new NewsCenterPager(mActivity));
		mPagers.add(new SmartServicePager(mActivity));
		mPagers.add(new GovAffairsPager(mActivity));
		mPagers.add(new SettingPager(mActivity));
		mViewPager.setAdapter(new ContentAdapter());
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.rb_home:
					mViewPager.setCurrentItem(0, false);
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_set:
					mViewPager.setCurrentItem(4, false);
					break;

				default:
					break;
				}
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				BasePager pager = mPagers.get(position);
				pager.initData();
				if (position == 0 || position == mPagers.size() - 1) {
					setSlidingMenuEnable(false);
				} else {
					setSlidingMenuEnable(true);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		mPagers.get(0).initData();
		setSlidingMenuEnable(false);
	}

	protected void setSlidingMenuEnable(boolean enable) {
		MainActivity activity = (MainActivity) getActivity();
		SlidingMenu slidingMenu = activity.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	public  NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) mPagers.get(1);
	}

}
