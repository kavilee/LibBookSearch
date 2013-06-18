package com.daxia.handler;

import java.io.IOException;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.remote.GZHUService;
import com.daxia.util.Utility;

public class GZHUHandler extends GenericHanlder {
	private GZHUService service = GZHUService.getInstance();

	private final static GZHUHandler instance = new GZHUHandler();

	private GZHUHandler() {
	}

	public static GZHUHandler getInstance() {
		return instance;
	}

	@Override
	public List<Book> handleBookList() {
		Integer page = getPage();
		String keyWord = getKeyWord();
		keyWord = Utility.StrToHexUni(keyWord);
		if (page == null || keyWord == null) {
			return null;
		}
		try {
			String remoteURL = "http://lib.gzhu.edu.cn:8080/bookle/search/index/searchForm?query="
					+ keyWord
					+ "&matchesPerPage=20&displayPages=15&index=default&searchPage="
					+ page;
			return service.downLoadBookList(remoteURL);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Book handleBookInfo() {
		String remoteURL = getBookDetailURL();
		try {
			return service.downLoadBookInfo(remoteURL);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
