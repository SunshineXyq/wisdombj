package com.example.zhbj.base.impl.menu;

import java.util.ArrayList;

import utils.CacheUtils;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhbj.R;
import com.example.zhbj.base.BaseMenuDetailPager;
import com.example.zhbj.domain.NewsMenu.NewsTabData;
import com.example.zhbj.domain.NewsTabBean;
import com.example.zhbj.domain.NewsTabBean.NewsData;
import com.example.zhbj.domain.NewsTabBean.TopNews;
import com.example.zhbj.global.GlobalConstants;
import com.example.zhbj.view.TopNewsViewPager;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

public class TabDetailPager extends BaseMenuDetailPager {
	
	private NewsTabData mTabData;
	@ViewInject(R.id.vp_top_news)
	private TopNewsViewPager vpTopNews;
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;
	private String mUrl;
	private NewsTabBean newsTabBean;
	@ViewInject(R.id.indicator)
	private CirclePageIndicator indicator;
	@ViewInject(R.id.lv_list)
	private ListView lvList;
	private ArrayList<NewsData> mNewsList;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mTabData = newsTabData;
		mUrl = GlobalConstants.SERVER_URL + mTabData.url;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_tab_detail,null);
		ViewUtils.inject(this,view);
		View header = View.inflate(mActivity, R.layout.list_item_header, null);
		ViewUtils.inject(this,header);
		lvList.addHeaderView(header);
		return view;
	}
	
	@Override
	public void initData() {
		String cache = CacheUtils.getCache(mUrl, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			processData(cache);
		}
		getDataFromServer();
	}
	
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println(result);
				CacheUtils.setCache(mUrl, result, mActivity);
				processData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void processData(String result) {
		Gson gson = new Gson();
		newsTabBean = gson.fromJson(result, NewsTabBean.class);
		if (newsTabBean.data.topnews != null) {
			vpTopNews.setAdapter(new TopNewsAdapter());
			indicator.setViewPager(vpTopNews);
			indicator.setSnap(true);
			indicator.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					TopNews topNews = newsTabBean.data.topnews.get(position);
					tvTitle.setText(topNews.title);
				}
				
				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
					
				}
				
				@Override
				public void onPageScrollStateChanged(int state) {
					
				}
			});
			tvTitle.setText(newsTabBean.data.topnews.get(0).title);
			indicator.onPageSelected(0);
		}
		mNewsList = newsTabBean.data.news;
		if (mNewsList != null) {
			lvList.setAdapter(new NewsAdapter());
		}
	}
	
	class NewsAdapter extends BaseAdapter{

		private ViewHolder holder;
		private BitmapUtils bitmapUtils;
		
		public NewsAdapter(){
			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public NewsData getItem(int arg0) {
			return mNewsList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if (arg1 == null) {
				arg1 = View.inflate(mActivity, R.layout.list_item_news, null);
				holder = new ViewHolder();
				holder.ivNews = (ImageView) arg1.findViewById(R.id.iv_news);
				holder.tvTitle = (TextView) arg1.findViewById(R.id.tv_title);
				holder.tvDate = (TextView) arg1.findViewById(R.id.tv_date);
				arg1.setTag(holder);
			} else {
				arg1.getTag();
			}
			NewsData newsData = getItem(arg0);
			holder.tvTitle.setText(newsData.title);
			holder.tvDate.setText(newsData.pubdate);
			bitmapUtils.display(holder.ivNews, newsData.listimage);
			return arg1;
		}
	}
	static class ViewHolder {
		public ImageView ivNews;
		public TextView tvTitle;
		public TextView tvDate;
	}

	class TopNewsAdapter extends PagerAdapter{
		
		private BitmapUtils bitmapUtils;
		
		public TopNewsAdapter(){
			bitmapUtils = new BitmapUtils(mActivity);
		}

		@Override
		public int getCount() {
			return newsTabBean.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(mActivity);
			imageView.setImageResource(R.drawable.topnews_item_default);
			imageView.setScaleType(ScaleType.FIT_XY);
//			String imageUrl = newsTabBean.data.topnews.get(position).topimage;
//			bitmapUtils.display(imageView, imageUrl);
			container.addView(imageView);
			return imageView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}

}
