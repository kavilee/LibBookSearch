package com.daxia.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.daxia.util.Utility;

public class HomeTabActivity extends TabActivity {
	private TabHost tabHost;

	private static final int[] HANDLERS = { R.drawable.tab_home_handle,
			R.drawable.tab_favorite_handle, R.drawable.tab_more_handle,
			R.drawable.tab_about_handle };
	/**
	 * 对应tab与其跳转的Activity
	 */
	private static Class<?>[] ACTIVITYARRAY = { BookSearchListActivity.class,
			BookFavoriteListActivity.class, MoreActivity.class,
			AboutActivity.class };

	class MyHandler extends Handler {
		public static final int START = 1;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START:
				onCreate2();
				break;
			}
		}
	}

	private void onCreate2() {
		init();
	}

	protected void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		MyApp.ShowWait(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_tab);
		MyHandler myHandler = new MyHandler();
		Message msg = new Message();
		msg.what = MyHandler.START;
		myHandler.sendMessageDelayed(msg, 100);
	}

	/**
	 * 初始化
	 */
	private void init() {
		tabHost = getTabHost();
		for (int tabNum = 0; tabNum < ACTIVITYARRAY.length; tabNum++) {
			addTabView(HANDLERS[tabNum], ACTIVITYARRAY[tabNum], tabNum);
		}
	}

	/**
	 * 为每一个tab添加事件
	 * 
	 * @param view
	 * @param cls
	 */
	private void addTabListeners(View view, final Class<?> cls,
			final int whichTab) {
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String tag = tabHost.getCurrentTabTag();

				if (event.getAction() == MotionEvent.ACTION_UP
						&& tabHost.getCurrentTab() == whichTab) {
					LocalActivityManager am = HomeTabActivity.this
							.getLocalActivityManager();
					Activity activity = am.getActivity(tag);
					Intent intent = new Intent(activity, cls);
					Utility.replaceActivity(activity, intent, tag);
				}
				return false;
			}
		});
	}

	/**
	 * 添加tab和初始化其外观
	 * 
	 * @param title
	 * @param handler
	 * @param activity
	 * @param whichTab
	 */
	public void addTabView(int handler, Class<?> activity, int whichTab) {
		View view = View.inflate(HomeTabActivity.this, R.layout.home_tab_item,
				null);
		TextView tabText = (TextView) view
				.findViewById(R.id.tab_textview_title);
		tabText.setBackgroundResource(handler);
		TabHost.TabSpec spec = tabHost.newTabSpec("" + whichTab);
		spec.setIndicator(view);
		Intent intent = new Intent(HomeTabActivity.this, activity);
		spec.setContent(intent);
		tabHost.addTab(spec);
		//
		addTabListeners(view, activity, whichTab);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(HomeTabActivity.this).show();
		}
		return super.onKeyDown(keyCode, event);
	}

}
