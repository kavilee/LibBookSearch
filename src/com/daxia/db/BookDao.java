package com.daxia.db;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.daxia.activity.MyApp;
import com.daxia.model.Book;
import com.daxia.util.ModelHelper;

public class BookDao {

	private SQLiteDatabase db;

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public BookDao(Context context) {
		// 打开/创建数据库
		db = context.openOrCreateDatabase(MyApp.DBNAME, Context.MODE_PRIVATE,
				null);
		if (ExistTable(db, SchemeCreator.BTNAME) == false) {
			// 创建表
			db.execSQL(SchemeCreator.getGetBTableSQL());
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
	 * 添加一行Book数据
	 * 
	 * @param model
	 * @return
	 */
	public boolean Insert(Book model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		long id = db.insert(SchemeCreator.BTNAME, "", values);
		return id != -1;
	}

	/**
	 * 根据bookId修改
	 * 
	 * @param model
	 * @return
	 */
	public int Update(Book model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		int count = db.update(SchemeCreator.BTNAME, values, "bookId=?",
				new String[] { model.getBookId() });
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
		int count = db.delete(SchemeCreator.BTNAME,
				String.format("bookId in (%s)", ids), null);
		return count;
	}

	/**
	 * 根据具体条件取得Activity实体列表
	 * 
	 * @return ArrayList<MdlActivity>
	 */
	public ArrayList<Book> GetList(String selection, String[] selectionArgs,
			String orderBy) {
		ArrayList<Book> list = new ArrayList<Book>();
		Cursor c = db.query(SchemeCreator.BTNAME, null, selection,
				selectionArgs, null, null, orderBy);
		while (c.moveToNext()) {
			list.add(GetInfo(c));
		}
		c.close();
		return list;
	}

	/**
	 * 顺序获取从offset条记录开始的limit条记录
	 * 
	 * @param sql
	 * @return
	 */
	public ArrayList<Book> GetList(int limit, int offset, String orderBy) {
		if(orderBy==null){
			orderBy = "bookId ASC";
		} 
		ArrayList<Book> list = new ArrayList<Book>();
		Cursor c = db.rawQuery("select * from " + SchemeCreator.BTNAME
				+ " order by " + orderBy + " limit " + limit + " offset "
				+ offset + ";", null);
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
	public static Book GetInfo(Cursor c) {
		Book model = new Book();
		ModelHelper.Parse(c, model);
		return model;
	}

	/**
	 * 根据主键取得一个Activity实体
	 * 
	 * @param id
	 * @return MdlActivity
	 */
	public Book GetInfo(String id) {
		Cursor c = db.query(SchemeCreator.BTNAME, null, "bookId=?",
				new String[] { id }, null, null, null);
		Book model = null;
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
	public ContentValues InitialContentValues(Book model, ContentValues values) {
		values.put("bookId", model.getBookId());
		values.put("thumbnail", model.getThumbnail());
		values.put("bookName", model.getBookName());
		values.put("author", model.getAuthor());
		values.put("pubHouse", model.getPubHouse());
		values.put("pubYear", model.getPubYear());
		values.put("getNum", model.getGetNum());
		values.put("storeNum", model.getStoreNum());
		values.put("canBorrow", model.getCanBorrow());
		values.put("favorite", model.getFavorite());
		values.put("imageUrl", model.getImageUrl());
		values.put("ISBN", model.getISBN());
		values.put("school", model.getSchool());
		values.put("bookType", model.getBookType());
		values.put("bookDetailURL", model.getBookDetailURL());
		return values;
	}

}
