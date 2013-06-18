package com.daxia.util;

import java.util.UUID;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Utility {
	/**
	 * 对输入框的后处理
	 * 
	 * @param input
	 */
	public static void InputHandler(Context context, EditText input) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0); // 隐藏软键盘
		input.clearFocus(); // 清除焦点
		// input.setText("");
	}

	/**
	 * 在选项卡中启动新活动
	 * 
	 * @param activity
	 * @param intent
	 * @param tag
	 */

	public static void replaceActivity(Activity activity, Intent intent,
			String tag) {
		ActivityGroup ag = (ActivityGroup) activity.getParent();
		LocalActivityManager am = ag.getLocalActivityManager();
		activity = am.getActivity(tag);
		String id = UUID.randomUUID().toString();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View view = am.startActivity(id, intent).getDecorView();
		activity.setContentView(view);
	}

	/**
	 * 把Unicode十六进制码转化成字符
	 * 
	 * @param code
	 * @return
	 */
	public static String HexUniToStr(String code) {
		StringBuilder sb = new StringBuilder();
		code = code.substring(0, code.length() - 1);
		String[] units = code.split(";");
		for (String unit : units) {
			unit = unit.substring(3);
			long num = Long.parseLong(unit, 16);
			char ch = (char) num;
			sb.append(ch);
		}
		return sb.toString();
	}

	/**
	 * 把字符串转换成十六进制数 格式:$$25968;$$25454;$$24211;
	 * 
	 * @param str
	 * @return
	 */
	public static String StrToHexUni(String str) {
		StringBuilder sb = new StringBuilder();
		char[] strChars = str.toCharArray();
		for (int i = 0; i < strChars.length; i++) {
			Integer a = Integer.valueOf(Integer.toHexString(strChars[i]), 16);
			sb.append("$$");
			sb.append(a);
			sb.append(";");
		}
		return sb.toString();
	}
}
