package com.daxia.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MoreActivity extends ActivityGroup {

	private Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MoreActivity.this, LibBookSearchActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(this).show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
