package com.daxia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daxia.util.Utility;

public class LibBookSearchActivity extends Activity {

	private TextView tvSchoolLib;
	private EditText etSearch;

	private Button btnSearch;
	private Button btnBack;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_search);
		init();
		addListeners();
	}

	private void init() {
		btnBack = (Button) findViewById(R.id.btnBack);
		tvSchoolLib = (TextView) findViewById(R.id.tvSchoolLib);
		tvSchoolLib.setText(MyApp.LIBOFSCHOOL);
		etSearch = (EditText) findViewById(R.id.etSearch);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

	private void addListeners() {
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Utility.InputHandler(LibBookSearchActivity.this, etSearch);
				String sContent = etSearch.getText().toString().trim();
				if (!sContent.equals("")) {
					BookSearchListActivity.FROM = BookSearchListActivity.FROMSEARCH;
					Intent intent = new Intent();
					intent.setClass(LibBookSearchActivity.this,
							HomeTabActivity.class);
					BookSearchListActivity.KEYWORD = sContent;
					startActivity(intent);
				} else {
					MyApp.toast(LibBookSearchActivity.this, "%s", "输入不能为空!");
				}
			}
		});
		// 回退到选择学校页
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LibBookSearchActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(LibBookSearchActivity.this).show();
		}
		return super.onKeyDown(keyCode, event);
	}

}