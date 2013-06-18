package com.daxia.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class MyApp extends Application {
	public static String LIBOFSCHOOL;
	
	public static HashMap<Context, Stack<ProgressDialog>> dicWait;
	// 数据库名
	public static final String DBNAME = "LISB.db";

	private static List<Activity> allActivities;

	public static void add(Activity ac) {
		allActivities.add(ac);
	}

	/**
	 * 退出
	 */
	public static void exit() {
		for (Activity ac : allActivities) {
			ac.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 创建
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		dicWait = new HashMap<Context, Stack<ProgressDialog>>();
		allActivities = new ArrayList<Activity>();
	}

	/**
	 * 弹出提示语
	 * 
	 * @param c
	 * @param s
	 */
	public static void toast(Context c, String s, Object... args) {
		s = String.format(s, args);
		Toast t = Toast.makeText(c, s, 1000);
		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();

	}

	public static ProgressDialog ShowWait(Context context) {
		return ShowWait(context, null);
	}

	public static ProgressDialog ShowWait(Context context, String message) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		if (TextUtils.isEmpty(message)) {
			message = "正在载入...";
		}
		pd.setMessage(message);
		pd.setIndeterminate(true);
		pd.setCancelable(true);
		pd.show();
		Stack<ProgressDialog> stack = dicWait.get(context);
		if (stack == null) {
			stack = new Stack<ProgressDialog>();
			dicWait.put(context, stack);
		}
		stack.push(pd);
		return pd;
	}

	public static void CancelWait(Context context) {
		Stack<ProgressDialog> stack = dicWait.get(context);
		if (stack != null && stack.size() > 0) {
			ProgressDialog pd = stack.pop();
			if (pd.isShowing()) {
				pd.cancel();
			}
		}
	}

}
