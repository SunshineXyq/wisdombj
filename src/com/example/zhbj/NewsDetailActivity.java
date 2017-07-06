package com.example.zhbj;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class NewsDetailActivity extends Activity implements OnClickListener {

	@ViewInject(R.id.ib_slid)
	private ImageButton ibSlid;
	@ViewInject(R.id.ib_back)
	private ImageButton ibBack;
	@ViewInject(R.id.ll_content)
	private LinearLayout llContent;
	@ViewInject(R.id.ib_textsize)
	private ImageButton ibTextSize;
	@ViewInject(R.id.ib_share)
	private ImageButton ibShare;
	@ViewInject(R.id.webview)
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);
		ViewUtils.inject(this);
		String url = getIntent().getStringExtra("url");
		Log.d("新闻详情url", url);

		llContent.setVisibility(View.VISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibSlid.setVisibility(View.INVISIBLE);
		ibBack.setOnClickListener(this);
		ibTextSize.setOnClickListener(this);
		ibShare.setOnClickListener(this);
		mWebView.loadUrl("http://news.xinhuanet.com/world/2017-07/05/c_1121266559.htm");
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setUseWideViewPort(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			@Deprecated
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ib_back:
			finish();
			break;
		case R.id.ib_textsize:
			showChooseDialog();
			break;
		case R.id.ib_share:

			break;

		default:
			break;
		}
	}

	private int mTempWhich;
	private int mCurrentTempWhich = 2;

	private void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择字体大小");
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
				"超小号字体" };
		builder.setSingleChoiceItems(items, mCurrentTempWhich,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						mTempWhich = arg1;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				WebSettings settings = mWebView.getSettings();
				switch (mTempWhich) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;

				default:
					break;
				}
				mCurrentTempWhich = mTempWhich;
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		builder.show();
	}

}
