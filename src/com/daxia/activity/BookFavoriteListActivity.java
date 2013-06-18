package com.daxia.activity;

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
import com.daxia.db.BookStoreInfoDao;
import com.daxia.model.Book;
import com.daxia.model.BookStoreInfo;
import com.daxia.util.ModelListViewAdapter;
import com.daxia.util.ModelListViewAdapter.OnBindingListener;
import com.daxia.util.Utility;

public class BookFavoriteListActivity extends ActivityGroup implements
		OnItemClickListener {

	private ModelListViewAdapter<Book> adapter;
	private ListView listView;
	private static List<Book> data;

	private LinearLayout listFooter;
	private ImageView ivMore;
	private ProgressBar pbMore;

	private EditText etSsearch;
	private Button btnSsearch;
	private Button btnMark;

	private Button btnBack;

	private static Integer PAGE = 1; // 第几页

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
	private OnBindingListener<Book> listener = new OnBindingListener<Book>() {
		@Override
		public void OnBinding(final int position, final Book book,
				final View layout) {
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
			TextView tvschool = (TextView)layout.findViewById(R.id.tvschool);
			tvbookName.setText(book.getBookName());
			tvauthor.setText(book.getAuthor());
			tvpubHouse.setText(book.getPubHouse());
			tvpubYear.setText(book.getPubYear());
			tvgetNum.setText(book.getGetNum());
			tvstoreNum.setText(book.getStoreNum());
			tvcanBorrow.setText(book.getCanBorrow());
			tvschool.setText(book.getSchool());
			// 取消收藏
			ImageView ivAddFavorite = (ImageView) layout
					.findViewById(R.id.ivAddFavorite);
			ivAddFavorite.setImageResource(R.drawable.btn_cancle_fav_bg);

			ivAddFavorite.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BookDao bDao = new BookDao(BookFavoriteListActivity.this);
					BookStoreInfoDao bsDao = new BookStoreInfoDao(
							BookFavoriteListActivity.this);

					bDao.Delete(book.getBookId());
					data.remove(book);
					if (data.size() < 20) {
						data = bDao.GetList(20, 0, null);
						if (data.size() < 20) {
							listView.removeFooterView(listFooter);
						}
					} else {
						data.remove(position);
					}
					adapter = new ModelListViewAdapter<Book>(
							BookFavoriteListActivity.this, data,
							R.layout.fav_book_list_item, listener);
					listView.setSelection(position);
					listView.setAdapter(adapter);

					List<BookStoreInfo> bsList = bsDao.GetList("bookId=?",
							new String[] { book.getBookId() }, null);

					for (BookStoreInfo bsModel : bsList) {
						bsDao.Delete(bsModel.getBookId());
					}

					bDao.Close();
					bsDao.Close();
				}
			});

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApp.add(this);
		super.onCreate(savedInstanceState);
		MyHandler myHandler = new MyHandler();
		setContentView(R.layout.searchbook_list);
		MyApp.ShowWait(this);
		Message msg = new Message();
		msg.what = MyHandler.START;
		myHandler.sendMessageDelayed(msg, 100);
	}

	private void onCreate2() {
		init();
		controlViews();
		addListeners();
		MyApp.CancelWait(this);
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
		listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);
	}

	/**
	 * 控制数据的显示
	 */
	private void controlViews() {
		btnMark.setText("我的藏书阁");
		BookDao bDao = new BookDao(BookFavoriteListActivity.this);
		data = bDao.GetList(20, (PAGE - 1) * 20, null);
		bDao.Close();
		if (data.isEmpty()) {
			MyApp.toast(this, "%s", "没有找到相关记录");
		}
		int dataSize = data.size();
		if (data.size() != 0 && dataSize % 20 == 0) {
			listView.addFooterView(listFooter);
		}
		adapter = new ModelListViewAdapter<Book>(BookFavoriteListActivity.this,
				data, R.layout.fav_book_list_item, listener);
		listView.setAdapter(adapter);

	}

	private void addListeners() {
		// 回退到搜索页
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BookFavoriteListActivity.this,
						LibBookSearchActivity.class);
				startActivity(intent);
			}
		});
		listFooter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ivMore.setVisibility(View.GONE);
				pbMore.setVisibility(View.VISIBLE);
				BookDao bDao = new BookDao(BookFavoriteListActivity.this);
				List<Book> bookList = bDao.GetList(20, (PAGE - 1) * 20, null);
				bDao.Close();
				if (bookList != null) {
					if (bookList.size() < 20) {
						listView.removeFooterView(listFooter);
					} else {
						ivMore.setVisibility(View.VISIBLE);
						pbMore.setVisibility(View.GONE);
					}
					data.addAll(bookList);
					adapter = new ModelListViewAdapter<Book>(
							BookFavoriteListActivity.this, data,
							R.layout.fav_book_list_item, listener);
					listView.setAdapter(adapter);
					listView.setSelection((PAGE - 1) * 20);
				}
			}
		});
		btnSsearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Utility.InputHandler(BookFavoriteListActivity.this, etSsearch);
				MyApp.ShowWait(BookFavoriteListActivity.this);
				String sContent = etSsearch.getText().toString().trim();
				if (sContent.equals("")) {
					MyApp.toast(BookFavoriteListActivity.this, "%s", "输入不能为空");
				} else {
					PAGE = 1;
					BookDao bDao = new BookDao(BookFavoriteListActivity.this);
					List<Book> bookList = bDao.GetList("bookName like '%"
							+ sContent + "%'", null, null);
					bDao.Close();
					if (bookList.isEmpty()) {
						MyApp.toast(BookFavoriteListActivity.this, "%s",
								"没有找到相关记录");
					}
					listView.removeFooterView(listFooter);
					data.clear();
					data.addAll(bookList);
					adapter = new ModelListViewAdapter<Book>(
							BookFavoriteListActivity.this, data,
							R.layout.fav_book_list_item, listener);
					listView.setAdapter(adapter);
				}
				MyApp.CancelWait(BookFavoriteListActivity.this);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		if (position != data.size()) {
			BookDetailActivity.FROM = BookDetailActivity.FORMBOOKFAVORITE;
			Book book = data.get(position);
			BookDetailActivity.setBOOK(book);
			Intent intent = new Intent(this, BookDetailActivity.class);
			Utility.replaceActivity(this, intent, "1");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(this).show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
