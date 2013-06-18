package com.daxia.db;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.daxia.activity.MyApp;
import com.daxia.model.BookStoreInfo;
import com.daxia.util.ModelHelper;

public class BookStoreInfoDao {
	private SQLiteDatabase db;

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public BookStoreInfoDao(Context context) {
		// 打开/创建数据库
		db = context.openOrCreateDatabase(MyApp.DBNAME, Context.MODE_PRIVATE,
				null);
		if (ExistTable(db, SchemeCreator.BSITNAME) == false) {
			// 创建表
			db.execSQL(SchemeCreator.getBSITableSQL());
		}
	}

	/**
	 * 关闭数据库
	 */
	public void Close() {
		if (db != null && db.isOpen() == true) {
			db.close();
		}
	}

	/**
	 * 是否存在指定的表
	 * 
	 * @param database
	 * @param tableName
	 * @return
	 */
	public static boolean ExistTable(SQLiteDatabase database, String tableName) {
		Cursor c = database.query("sqlite_master", new String[] { "1" },
				"type=? and name=?", new String[] { "table", tableName }, null,
				null, null);
		try {
			return c.moveToFirst();
		} finally {
			c.close();
		}
	}

	/**
	 * 添加一行BookStoreInfo数据
	 * 
	 * @param model
	 * @return
	 */
	public boolean Insert(BookStoreInfo model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		long id = db.insert(SchemeCreator.BSITNAME, "", values);
		return id != -1;
	}

	/**
	 * 根据BookStoreInfoId修改
	 * 
	 * @param model
	 * @return
	 */
	public int Update(BookStoreInfo model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		int count = db.update(SchemeCreator.BSITNAME, values, "storeId=?",
				new String[] { model.getStoreId() });
		return count;
	}

	/**
	 * 根据ids 删除相应的行
	 * 
	 * @param ids
	 *            如超过一条，别忘了中间以,隔开
	 * @return 受影响的行数
	 */
	public int Delete(String ids) {
		Pattern p = Pattern.compile("^[\\w,-]+$");// 检查数据合法性
		Matcher m = p.matcher(ids);
		if (m.find() == false) {
			throw new IllegalArgumentException();
		}
		ids = ModelHelper.AddSingleMark(ids); // 加上单引号
		int count = db.delete(SchemeCreator.BSITNAME,
				String.format("storeId in (%s)", ids), null);
		return count;
	}

	/**
	 * 根据具体条件取得Activity实体列表
	 * 
	 * @return ArrayList<MdlActivity>
	 */
	public ArrayList<BookStoreInfo> GetList(String selection,
			String[] selectionArgs, String orderBy) {
		ArrayList<BookStoreInfo> list = new ArrayList<BookStoreInfo>();
		Cursor c = db.query(SchemeCreator.BSITNAME, null, selection,
				selectionArgs, null, null, orderBy);
		while (c.moveToNext()) {
			list.add(GetInfo(c));
		}
		c.close();
		return list;
	}

	/**
	 * 取得一个Activity实体
	 * 
	 * @param c
	 * @return MdlActivity
	 */
	public static BookStoreInfo GetInfo(Cursor c) {
		BookStoreInfo model = new BookStoreInfo();
		ModelHelper.Parse(c, model);
		return model;
	}

	/**
	 * 根据主键取得一个Activity实体
	 * 
	 * @param id
	 * @return MdlActivity
	 */
	public BookStoreInfo GetInfo(String id) {
		Cursor c = db.query(SchemeCreator.BSITNAME, null, "storeId=?",
				new String[] { id }, null, null, null);
		BookStoreInfo model = null;
		if (c.moveToFirst()) {
			model = GetInfo(c);
		}
		c.close();
		return model;
	}

	/**
	 * 初始化ContentValues值
	 * 
	 * @param model
	 * @param values
	 * @return ContentValues
	 */
	public ContentValues InitialContentValues(BookStoreInfo model,
			ContentValues values) {
		values.put("bookId", model.getBookId());
		values.put("storePlace", model.getStorePlace());
		values.put("loginNum", model.getLoginNum());
		values.put("getNum", model.getGetNum());
		values.put("volumnTime", model.getVolumnTime());
		values.put("year", model.getYear());
		values.put("bookType", model.getBookType());
		values.put("state", model.getState());
		values.put("storeCampus", model.getStoreCampus());
		return values;
	}

}
