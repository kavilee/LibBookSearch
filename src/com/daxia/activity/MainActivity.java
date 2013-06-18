package com.daxia.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.daxia.handler.GDUFSHandler;
import com.daxia.handler.GDUTHandler;
import com.daxia.handler.GZHUHandler;
import com.daxia.handler.SCNUHandler;

public class MainActivity extends Activity {
	private LinearLayout llGdut;
	private LinearLayout llScnu;
	private LinearLayout llGzhu;
	private LinearLayout llGdufs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		addListeners();
	}

	private void init() {
		llGdut = (LinearLayout) findViewById(R.id.llGdut);
		llScnu = (LinearLayout) findViewById(R.id.llScnu);
		llGzhu = (LinearLayout) findViewById(R.id.llGzhu);
		llGdufs = (LinearLayout) findViewById(R.id.llGdufs);
	}

	private void addListeners() {
		llGdut.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyApp.LIBOFSCHOOL = "广工图书馆";
				BookSearchListActivity.handler = GDUTHandler.getInstance();
				BookDetailActivity.handler = GDUTHandler.getInstance();
				startAction(MainActivity.this, LibBookSearchActivity.class);
			}
		});

		llScnu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyApp.LIBOFSCHOOL = "华师图书馆";
				BookSearchListActivity.handler = SCNUHandler.getInstance();
				BookDetailActivity.handler = SCNUHandler.getInstance();
				startAction(MainActivity.this, LibBookSearchActivity.class);
			}
		});
		llGzhu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyApp.LIBOFSCHOOL = "广大图书馆";
				BookSearchListActivity.handler = GZHUHandler.getInstance();
				BookDetailActivity.handler = GZHUHandler.getInstance();
				startAction(MainActivity.this, LibBookSearchActivity.class);
			}
		});
		llGdufs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyApp.LIBOFSCHOOL = "广外图书馆";
				BookSearchListActivity.handler = GDUFSHandler.getInstance();
				BookDetailActivity.handler = GDUFSHandler.getInstance();
				startAction(MainActivity.this, LibBookSearchActivity.class);
			}
		});
	}

	private void startAction(Context packageContext, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(packageContext, cls);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(MainActivity.this).show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
