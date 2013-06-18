package com.daxia.db;

public class SchemeCreator {

	public static final String BTNAME = "book";
	public static final String BSITNAME = "bookStoreInfo";

	public static String getGetBTableSQL() {
		StringBuilder sb = new StringBuilder();
		// book
		sb.append("create table book");
		sb.append(" (");
		sb.append("bookId               text primary key,");
		sb.append("thumbnail            text,");
		sb.append("bookName             text,");
		sb.append("author               text,");
		sb.append("pubHouse             text,");
		sb.append("pubYear              text,");
		sb.append("getNum               text,");
		sb.append("storeNum             text,");
		sb.append("canBorrow            text,");
		sb.append("favorite             boolean,");
		sb.append("imageUrl             text,");
		sb.append("intro                text,");
		sb.append("ISBN                 text,");
		sb.append("school               text,");
		sb.append("bookType             text,");
		sb.append("bookDetailURL        text");
		sb.append("); ");
		return sb.toString();
	}

	public static String getBSITableSQL() {
		StringBuilder sb = new StringBuilder();
		// bookStoreInfo
		sb.append("create table bookStoreInfo");
		sb.append(" (");
		sb.append("storeId              integer PRIMARY KEY autoincrement,");
		sb.append("bookId               text,");
		sb.append("storeCampus          text,");
		sb.append("storePlace           text,");
		sb.append("loginNum             text,");
		sb.append("getNum               text,");
		sb.append("volumnTime           text,");
		sb.append("year                 text,");
		sb.append("bookType             text,");
		sb.append("state                text");
		sb.append("); ");

		return sb.toString();
	}
}
