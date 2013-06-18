package com.daxia.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LogoutDialog extends AlertDialog {

	public LogoutDialog(Context context) {
		super(context);
		setTitle("退出");
		setMessage("你确定要退出?");
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		setButton(BUTTON_POSITIVE, "退出", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				 MyApp.exit();
			}
		});
		setButton(BUTTON_NEGATIVE, "取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});
	}
}
