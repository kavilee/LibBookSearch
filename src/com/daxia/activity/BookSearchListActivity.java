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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daxia.db.BookDao;
import com.daxia.handler.GenericHanlder;
import com.daxia.model.Book;
import com.daxia.util.ModelListViewAdapter;
import com.daxia.util.ModelListViewAdapter.OnBindingListener;
import com.daxia.util.Utility;

public class BookSearchListActivity extends ActivityGroup implements
		OnItemClickListener {

	public static GenericHanlder handler;

	private ModelListViewAdapter<Book> adapter;
	private ListView listView;
	private static List<Book> data;
	private static List<Book> bookList; // 每请求的20条记录

	private LinearLayout listFooter;
	private ImageView ivMore;
	private ProgressBar pbMore;

	private EditText etSsearch;
	private Button btnSsearch;
	private Button btnMark;

	private Button btnBack;

	public static String KEYWORD; // 搜索关键字
	private static Integer PAGE = 1; // 第几页
    private static Integer PAGESIZE = 20; //默认每页20条记录
	
	public static int FROM = 0;
	public static int FROMSEARCH = 1;
	public static int FROMBOOKDETIL = 2;

	class MyHandler extends Handler {
		public static final int START = 1;
		public static final int SEARCH = 2;
		public static final int NEXT = 3;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case START:
				onCreate2();
				break;
			case SEARCH:
				search();
				break;
			case NEXT:
				showNext();
				break;
			}
		}
	}

	/**
	 * 请求服务器数据线程
	 * 
	 * @author Administrator
	 * 
	 */
	class MyThread implements Runnable {

		@Override
		public void run() {
			bookList = handler.handleBookList();
			Message msg = new Message();
			msg.what = MyHandler.NEXT;
			myHandler.sendMessage(msg);
		}

	}

	/**
	 * 绑定list item 上的数据
	 */
	private OnBindingListener<Book> listener = new OnBindingListener<Book>() {
		@Override
		public void OnBinding(int position, final Book book, View layout) {
			TextView tvbookName = (TextView) layout
					.findViewById(R.id.tvbookName);
			TextView tvauthor = (TextView) layout.findViewById(R.id.tvauthor);
			TextView tvpubHouse = (TextView) layout
					.findViewById(R.id.tvpubHouse);
			TextView tvpubYear = (TextView) layout.findViewById(R.id.tvpubYear);
			TextView tvgetNum = (TextView) layout.findViewById(R.id.tvgetNum);
			TextView tvstoreNum = (TextView) layout
					.findViewById(R.id.tvstoreNum);
			TextView tvcanBorrow = (TextView) layout
					.findViewById(R.id.tvcanBorrow);
			tvbookName.setText(book.getBookName());
			tvauthor.setText(book.getAuthor());
			tvpubHouse.setText(book.getPubHouse());
			tvpubYear.setText(book.getPubYear());
			tvgetNum.setText(book.getGetNum());
			tvstoreNum.setText(book.getStoreNum());
			tvcanBorrow.setText(book.getCanBorrow());
			// 添加收藏

			final ImageView ivAddFavorite = (ImageView) layout
					.findViewById(R.id.ivAddFavorite);
			BookDao bDao = new BookDao(BookSearchListActivity.this);
			ArrayList<Book> bList = bDao.GetList("bookId=?",
					new String[] { book.getBookId() }, null);
			bDao.Close();
			if (bList != null && !bList.isEmpty()) {
				// 已收藏
				ivAddFavorite.setImageResource(R.drawable.btn_al_fav_bg);
				ivAddFavorite.setClickable(false);
			} else {
				ivAddFavorite.setImageResource(R.drawable.btn_fav_bg);
				ivAddFavorite.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ivAddFavorite.setClickable(false);
						BookDao bDao = new BookDao(BookSearchListActivity.this);
						bDao.Insert(book);
						bDao.Close();
						ivAddFavorite
								.setImageResource(R.drawable.btn_al_fav_bg);
						MyApp.toast(BookSearchListActivity.this, "%s", "收藏成功");
					}
				});
			}

		}

	};

	private MyHandler myHandler = new MyHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		super.onCreate(savedInstanceState);
		if (FROM != FROMSEARCH) {
			MyApp.ShowWait(this);
			FROM = 0;
		}
		setContentView(R.layout.searchbook_list);
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
		btnBack = (Button) findViewById(R.id.btnBack);
		btnMark = (Button) findViewById(R.id.btnMark);
		etSsearch = (EditText) findViewById(R.id.etSsearch);
		btnSsearch = (Button) findViewById(R.id.btnSsearch);
		listFooter = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.book_list_tail, null);
		ivMore = (ImageView) listFooter.findViewById(R.id.ivMore);
		pbMore = (ProgressBar) listFooter.findViewById(R.id.pbMore);
		ivMore.setVisibility(View.VISIBLE);
		pbMore.setVisibility(View.GONE);
		listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);
	}

	/**
	 * 控制数据的显示
	 */
	private void controlViews() {
		btnMark.setText(MyApp.LIBOFSCHOOL);
		handler.setPage(PAGE);
		handler.setKeyWord(KEYWORD);
		if (data == null || FROM == FROMSEARCH) {
			data = handler.handleBookList();
			if (data == null) {
				data = new ArrayList<Book>(0);
			}
			if (data.isEmpty()) {
				MyApp.toast(this, "%s", "没有找到相关记录");
			}
		}
		int dataSize = data.size();
		if (dataSize >= PAGESIZE && dataSize % PAGESIZE == 0) {
			listView.addFooterView(listFooter);
		}
		adapter = new ModelListViewAdapter<Book>(BookSearchListActivity.this,
				data, R.layout.book_list_item, listener);
		listView.setAdapter(adapter);
		if (FROM == FROMSEARCH) {
			MyApp.CancelWait(getParent());
			FROM = 0;
		} else {
			MyApp.CancelWait(this);
		}
	}

	private void addListeners() {
		// 回退到搜索页
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BookSearchListActivity.this,
						LibBookSearchActivity.class);
				startActivity(intent);
			}
		});
		listFooter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ivMore.setVisibility(View.GONE);
				pbMore.setVisibility(View.VISIBLE);
				handler.setPage(PAGE++);
				handler.setKeyWord(KEYWORD);
				Thread myThread = new Thread(new MyThread());
				myThread.start();
			}
		});
		btnSsearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Utility.InputHandler(BookSearchListActivity.this, etSsearch);
				MyApp.ShowWait(BookSearchListActivity.this);
				Message msg = new Message();
				msg.what = MyHandler.SEARCH;
				myHandler.sendMessageDelayed(msg, 500);
			}
		});
	}

	/**
	 * 显示下20条记录
	 */
	private void showNext() {
		if (bookList != null) {
			if (bookList.size() < PAGESIZE) {
				listView.removeFooterView(listFooter);
			} else {
				ivMore.setVisibility(View.VISIBLE);
				pbMore.setVisibility(View.GONE);
			}
			data.addAll(bookList);
			adapter = new ModelListViewAdapter<Book>(
					BookSearchListActivity.this, data, R.layout.book_list_item,
					listener);
			listView.setAdapter(adapter);
			listView.setSelection((PAGE - 1) * PAGESIZE);
		}
	}

	/**
	 * 搜索
	 */
	private void search() {
		String sContent = etSsearch.getText().toString().trim();
		if (sContent.equals("")) {
			MyApp.toast(BookSearchListActivity.this, "%s", "输入不能为空");
		} else {
			PAGE = 1;
			handler.setPage(PAGE);
			handler.setKeyWord(sContent);
			List<Book> bookList = handler.handleBookList();
			if (bookList != null) {
				if (bookList.isEmpty()) {
					MyApp.toast(BookSearchListActivity.this, "%s", "没有找到相关记录");
				}
				if (bookList.size() < PAGESIZE) {
					listView.removeFooterView(listFooter);
				}
				data.clear();
				data.addAll(bookList);
				adapter = new ModelListViewAdapter<Book>(
						BookSearchListActivity.this, data,
						R.layout.book_list_item, listener);
				listView.setAdapter(adapter);
			}
		}
		MyApp.CancelWait(BookSearchListActivity.this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long arg3) {
		if (position != data.size()) {
			BookDetailActivity.FROM = BookDetailActivity.FORMBOOKSEARCH;
			Book book = data.get(position);
			BookDetailActivity.setBOOK(book);
			Intent intent = new Intent(this, BookDetailActivity.class);
			Utility.replaceActivity(this, intent, "0");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(this).show();
		}
		return super.onKeyDown(keyCode, event);
	}

	public static void setPAGESIZE(Integer pAGESIZE) {
		PAGESIZE = pAGESIZE;
	}
}
