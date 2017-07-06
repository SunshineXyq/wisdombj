package com.example.zhbj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.zhbj.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PullToRefreshListView extends ListView {

	private int startY = -1;
	private int mHeaderViewHeight;
	private View mHeaderView;
	public static final int STATE_PULL_TO_REFRESH = 1; // 下拉刷新
	public static final int STATE_RELEASE_TO_REFRESH = 2;// 松开刷新
	public static final int STATE_REFRESHING = 3; // 正在刷新

	public int mCurrentState = STATE_PULL_TO_REFRESH;
	private TextView tvTitle;
	private TextView tvDate;
	private ProgressBar pbProgressBar;
	private RotateAnimation animUp;
	private RotateAnimation animDown;
	private ImageView ivArrow;
	public OnRefreshListener listener;
	private View mFooterView;

	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initHeaderView();
		initFooterView();
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public PullToRefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}

	public void initHeaderView() {
		mHeaderView = View
				.inflate(getContext(), R.layout.pull_to_refresh_header, null);
		this.addHeaderView(mHeaderView);
		tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tvDate = (TextView) mHeaderView.findViewById(R.id.tv_date);
	    ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
		pbProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pb_loading);

		mHeaderView.measure(0, 0);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
		initAnim();
		setCurrentTime();
	}
	
	private boolean loadMore = false;
	private int mFooterViewHeight;
	
	public void initFooterView() {
		mFooterView = View.inflate(getContext(), R.layout.pull_to_refresh_footer, null);
		this.addFooterView(mFooterView);
		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
		this.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				if (arg1 == SCROLL_STATE_IDLE) {
					int lastVisiblePosition = getLastVisiblePosition();
					if (lastVisiblePosition == getCount() - 1 && !loadMore) {
						mFooterView.setPadding(0, 0, 0, 0);
						setSelection(getCount()-1);
						if (listener != null) {
							loadMore = true;
							listener.onloadMore();
						}
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY == -1) {
				startY = (int) ev.getY();
			}
			
			if (mCurrentState == STATE_REFRESHING) {
				break;
			}
			int endY = (int) ev.getY();
			int dy = endY - startY;
			int firstVisiblePosition = getFirstVisiblePosition();
			if (dy > 0 && firstVisiblePosition == 0) {
				int padding = dy - mHeaderViewHeight;
				mHeaderView.setPadding(0, padding, 0, 0);
				if (padding > 0 && mCurrentState != STATE_RELEASE_TO_REFRESH) { // 松开刷新
					mCurrentState = STATE_RELEASE_TO_REFRESH;
					refreshData();
				} else if (padding < 0
						&& mCurrentState != STATE_PULL_TO_REFRESH) {
					mCurrentState = STATE_PULL_TO_REFRESH;
					refreshData();
				}
				return true;
			}
			break;

		case MotionEvent.ACTION_UP:
			startY = -1;
			if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
				mCurrentState = STATE_REFRESHING;
				mHeaderView.setPadding(0, 0, 0, 0);
				refreshData();
				if (listener != null) {
					listener.onRefresh();
				}
			} else if(mCurrentState == STATE_PULL_TO_REFRESH) {
				mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void refreshData() {
		switch (mCurrentState) {
		case STATE_PULL_TO_REFRESH:
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgressBar.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animDown);
			break;
		case STATE_RELEASE_TO_REFRESH:
			tvTitle.setText("松开刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgressBar.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animUp);
			break;
		case STATE_REFRESHING:
			tvTitle.setText("正在刷新...");
			ivArrow.clearAnimation();
			ivArrow.setVisibility(View.INVISIBLE);
			pbProgressBar.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
	
	public void onRefreshComplete(boolean success){
		if (!loadMore) {
			if (success) {
				setCurrentTime();
			}
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgressBar.setVisibility(View.INVISIBLE);
			mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			mCurrentState = STATE_PULL_TO_REFRESH;
		} else {
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
			loadMore = false;
		}
	}
	
	@SuppressLint("NewApi") 
	private void setCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		tvDate.setText(date);
	}
	
	public void setOnRefreshListener(OnRefreshListener listener){
		this.listener = listener;
	}
	
	public interface OnRefreshListener{
		public void onRefresh();
		public void onloadMore();
	}

	private void initAnim() {
		animUp = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);
		
		animDown = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);
	}
}
