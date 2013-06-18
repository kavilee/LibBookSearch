package com.daxia.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daxia.db.BookDao;
import com.daxia.db.BookStoreInfoDao;
import com.daxia.handler.GenericHanlder;
import com.daxia.model.Book;
import com.daxia.model.BookStoreInfo;
import com.daxia.util.ModelListViewAdapter;
import com.daxia.util.ModelListViewAdapter.OnBindingListener;
import com.daxia.util.Utility;

public class BookDetailActivity extends ActivityGroup {

	private ModelListViewAdapter<BookStoreInfo> adapter;
	private ListView listView;
	private List<BookStoreInfo> data;

	private LinearLayout listHeader;
	private LinearLayout listFooter;
	private static Book BOOK;

	private TextView tvbookId;
	private TextView tvbookName;
	private TextView tvauthor;
	private TextView tvpubHouse;
	private TextView tvpubYear;
	private TextView tvIntro ;
	private Button btnSchool;
	private Button btnBack;
	private ImageView ivAddFavorite;
     
	public static GenericHanlder handler;

	public static int FORMBOOKSEARCH = 1;
	public static int FORMBOOKFAVORITE = 2;
	public static int FROM;

	class MyHandler extends Handler {
		public static final int START = 1;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START:
				onCreate2();
				break;
			}
		}
	}

	/**
	 * 绑定list item 上的数据
	 */
	private OnBindingListener<BookStoreInfo> listener = new OnBindingListener<BookStoreInfo>() {
		@Override
		public void OnBinding(int position, BookStoreInfo model, View layout) {
			TextView tvstorePlace = (TextView) layout
					.findViewById(R.id.tvstorePlace);
			TextView tvloginNum = (TextView) layout
					.findViewById(R.id.tvloginNum);
			TextView tvgetNum = (TextView) layout.findViewById(R.id.tvgetNum);
			TextView tvvolumnTime = (TextView) layout
					.findViewById(R.id.tvvolumnTime);
			TextView tvyear = (TextView) layout.findViewById(R.id.tvyear);
			TextView tvbookType = (TextView) layout
					.findViewById(R.id.tvbookType);
			TextView tvstate = (TextView) layout.findViewById(R.id.tvstate);
			TextView tvStoreCampus = (TextView) layout
					.findViewById(R.id.tvStoreCampus);
			tvstorePlace.setText(model.getStorePlace());
			tvloginNum.setText(model.getLoginNum());
			tvgetNum.setText(model.getGetNum());
			tvvolumnTime.setText(model.getVolumnTime());
			tvyear.setText(model.getYear());
			tvbookType.setText(model.getBookType());
			tvstate.setText(model.getState());
			tvStoreCampus.setText(model.getStoreCampus());
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		super.onCreate(savedInstanceState);
		MyHandler myHandler = new MyHandler();
		setContentView(R.layout.book_detail);
		MyApp.ShowWait(this);
		Message msg = new Message();
		msg.what = MyHandler.START;
		myHandler.sendMessageDelayed(msg, 100);
	}

	private void onCreate2() {
		init();
		controlViews();
		addListeners();
	}

	/**
	 * 初始化
	 */
	private void init() {
		btnSchool = (Button)findViewById(R.id.btnSchool);
		btnBack = (Button) findViewById(R.id.btnBack);
		listHeader = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.book_detail_header, null);
		ivAddFavorite = (ImageView) listHeader.findViewById(R.id.ivAddFavorite);

		tvbookId = (TextView) listHeader.findViewById(R.id.tvbookId);
		tvbookName = (TextView) listHeader.findViewById(R.id.tvbookName);
		tvauthor = (TextView) listHeader.findViewById(R.id.tvauthor);
		tvpubHouse = (TextView) listHeader.findViewById(R.id.tvpubHouse);
		tvpubYear = (TextView) listHeader.findViewById(R.id.tvpubYear);

		listFooter = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.book_detail_tail, null);
		tvIntro = (TextView) listFooter.findViewById(R.id.tvIntro);
		listView = (ListView) findViewById(android.R.id.list);
		listView.addHeaderView(listHeader);
		listView.addFooterView(listFooter);
	}

	private void addListeners() {
		// 回退到搜索列表页
		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (FROM == FORMBOOKFAVORITE) {
					Intent intent = new Intent(BookDetailActivity.this,
							BookFavoriteListActivity.class);
					Utility.replaceActivity(BookDetailActivity.this, intent,
							"1");
				} else {
					Intent intent = new Intent(BookDetailActivity.this,
							BookSearchListActivity.class);
					Utility.replaceActivity(BookDetailActivity.this, intent,
							"0");
				}
			}
		});

	}

	/**
	 * 控制数据的显示
	 */
	private void controlViews() {
		btnSchool.setText(MyApp.LIBOFSCHOOL);
		
		tvbookId.setText(BOOK.getBookId());
		tvbookName.setText(BOOK.getBookName());
		tvauthor.setText(BOOK.getAuthor());
		tvpubHouse.setText(BOOK.getPubHouse());
		tvpubYear.setText(BOOK.getPubYear());

		// 已经收藏了,但是书的详细存放信息还没加上
		BookStoreInfoDao bsDao = new BookStoreInfoDao(this);
		List<BookStoreInfo> bsList = bsDao.GetList("bookId=?",
				new String[] { BOOK.getBookId() }, null);
		if (bsList.isEmpty()) {
			handler.setBookDetailURL(BOOK.getBookDetailURL());
			if (BOOK.getStoreInfoList() == null) {
				Book bookInfo = handler.handleBookInfo();
				if (bookInfo != null) {
					bsList = bookInfo.getStoreInfoList();
					BOOK.setISBN(bookInfo.getISBN());
					BOOK.setIntro(bookInfo.getIntro());
					BOOK.setStoreInfoList(bsList);
					for (BookStoreInfo bsModel : bsList) {
						bsModel.setBookId(BOOK.getBookId());
						bsDao.Insert(bsModel);
					}
				} else {
					BOOK.setStoreInfoList(new ArrayList<BookStoreInfo>());
				}
			}
		} else {
			BOOK.setStoreInfoList(bsList);
		}
		bsDao.Close();

		// 判断用户是否已经收藏了该书,以及加上收藏的功能
		BookDao bDao = new BookDao(BookDetailActivity.this);
		ArrayList<Book> bList = bDao.GetList("bookId=?",
				new String[] { BOOK.getBookId() }, null);
		bDao.Close();

		if (bList != null && !bList.isEmpty()) {
			// 已收藏
			ivAddFavorite.setImageResource(R.drawable.btn_al_fav_bg);
			ivAddFavorite.setClickable(false);
		} else {
			ivAddFavorite.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ivAddFavorite.setClickable(false);
					BookDao bDao = new BookDao(BookDetailActivity.this);
					bDao.Insert(BOOK);
					BookStoreInfoDao bsDao = new BookStoreInfoDao(
							BookDetailActivity.this);
					for (BookStoreInfo bsModel : BOOK.getStoreInfoList()) {
						bsModel.setBookId(BOOK.getBookId());
						bsDao.Insert(bsModel);
					}
					bDao.Close();
					bsDao.Close();
					ivAddFavorite.setImageResource(R.drawable.btn_al_fav_bg);
					MyApp.toast(BookDetailActivity.this, "%s", "收藏成功");
				}
			});
		}
		//简介
		tvIntro.setText(BOOK.getIntro());
		
		data = BOOK.getStoreInfoList();
		adapter = new ModelListViewAdapter<BookStoreInfo>(
				BookDetailActivity.this, data, R.layout.book_store_list_item,
				listener);
		listView.setAdapter(adapter);
		MyApp.CancelWait(this);
	}

	public static Book getBOOK() {
		return BOOK;
	}

	public static void setBOOK(Book bOOK) {
		BOOK = bOOK;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(BookDetailActivity.this).show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
