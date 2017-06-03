package com.example.zhbj;

import utils.PreUtils;
import android.R.animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

		// 旋转动画
		RotateAnimation animRotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5f);
		animRotate.setDuration(1000);
		animRotate.setFillAfter(true);

		//缩放动画
		ScaleAnimation animScale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animRotate.setDuration(1000);
		animRotate.setFillAfter(true);
		
		//渐变动画
		AlphaAnimation animAlpha = new AlphaAnimation(0, 1);
		animAlpha.setDuration(2000);
		animAlpha.setFillAfter(true);
		
		//动画集合
		AnimationSet set = new AnimationSet(true);
		set.addAnimation(animRotate);
		set.addAnimation(animScale);
		set.addAnimation(animAlpha);
		rlRoot.startAnimation(set);
		
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				boolean isFirstEnter = PreUtils.getBoolean(SplashActivity.this, "is_first_enter", true);
				Intent intent;
				if (isFirstEnter) {
					intent = new Intent(SplashActivity.this,GuideActivity.class);
				} else {
					intent = new Intent(SplashActivity.this,MainActivity.class);
				}
				startActivity(intent);
				finish();
			}
		});
	}
}
