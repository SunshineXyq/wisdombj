package fragment;


import java.util.ArrayList;

import com.example.zhbj.MainActivity;
import com.example.zhbj.R;
import com.example.zhbj.base.impl.NewsCenterPager;
import com.example.zhbj.domain.NewsMenu.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LeftFragment extends BaseFragment {
    
	@ViewInject(R.id.lv_list)
	private ListView lvList;
	private ArrayList<NewsMenuData> mNewsMenuDatas;
	private int mCurrentPos;
	private LeftMenuAdapter adapter;
	
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void initData() {
		
	}
	
	public void setMenuData(ArrayList<NewsMenuData> data){
		mCurrentPos = 0;
		mNewsMenuDatas = data;
		adapter = new LeftMenuAdapter();
		lvList.setAdapter(adapter);
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mCurrentPos = position;
				adapter.notifyDataSetChanged();
				toggle();
				setCurrentDetailPager(position);
			}
		});
	}
	protected void setCurrentDetailPager(int position) {
		MainActivity mainUI = (MainActivity) mActivity;
		ContentFragment contentFragment = mainUI.getContentFragment();
		NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
		newsCenterPager.setCurrentDetailPager(position);
	}

	protected void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
	class LeftMenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mNewsMenuDatas.size();
		}

		@Override
		public  NewsMenuData getItem(int arg0) {
			return mNewsMenuDatas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
			TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);
			tvMenu.setText(getItem(position).title);
			
			if (position == mCurrentPos) {
				tvMenu.setEnabled(true);
			} else {
				tvMenu.setEnabled(false);
			}
			return view;
		}
		
	}

}
