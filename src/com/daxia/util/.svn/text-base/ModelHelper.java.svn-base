package com.daxia.util;

import java.lang.reflect.Field;

import android.database.Cursor;

public class ModelHelper {
	public static String GetString(Object model) {
		Class<?> c1 = model.getClass();
		Field[] fields = c1.getFields();
		Object value;
		String name;
		StringBuilder sb = new StringBuilder();
		for (Field field : fields) {
			name = field.getName();
			try {
				value = field.get(model);
			} catch (Exception e) { 
				value = "";
			} 
			if (value == null) {
				value = "";
			}
			sb.append(String.format("%s = %s\r\n", name, value));
		}
		return sb.toString();
	} 

	/**
	 * 将sqlite中的数据转化成合适的数据类型
	 * 
	 * @param cursor
	 * @param model
	 */
	public static void Parse(Cursor cursor, Object model) {
		Class<?> c1 = model.getClass();
		Field[] fields = c1.getDeclaredFields();
		Class<?> c2;
		Object value = null;
		String key;
		for (Field field : fields) {
			field.setAccessible(true);
			c2 = field.getType();
			key = field.getName();
			int columnIndex = cursor.getColumnIndex(key);
			if (columnIndex < 0) {
				continue;
			}
			if (c2.equals(String.class)) {
				value = cursor.getString(columnIndex);
			} else if (c2.equals(boolean.class)) {
				value = cursor.getInt(columnIndex) != 0;
			} else {
				continue;
			}
			try {
				field.set(model, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 另以逗号为分隔符的字符串各自带上单引号，以符合数据库的字符类型
	 * 
	 * @param str
	 *            如(abc or abc,efg)
	 * @return 带单引号的字符串，如：'abc','edf'
	 */
	public static String AddSingleMark(String str) {
		if (str == null)
			return null;
		if (str.indexOf(",") == -1) {
			return "'" + str + "'";
		}
		str = str.replaceAll(",", "','");
		return "'" + str + "'";
	}
}
