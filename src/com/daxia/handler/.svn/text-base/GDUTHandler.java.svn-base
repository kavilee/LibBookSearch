package com.daxia.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.remote.GDUTService;

public class GDUTHandler extends GenericHanlder {
	private GDUTService service = GDUTService.getInstance();

	private final static GDUTHandler instance = new GDUTHandler();

	private GDUTHandler() {
	}

	public static GDUTHandler getInstance() {
		return instance;
	}

	/**
	 * 成功返回结果集，失败返回null
	 * 
	 * @return
	 */
	public List<Book> handleBookList() {
		Integer page = getPage();
		String keyWord = getKeyWord();
		if (page == null || keyWord == null) {
			return null;
		}
		try {
			keyWord = URLEncoder.encode(keyWord, "GBK");
			String remoteURL = "http://222.200.98.171:81/searchresult.aspx?anywords="
					+ keyWord
					+ "&dt=ALL&cl=ALL&dp=20&sf=M_PUB_YEAR&ob=DESC&sm=table&dept=ALL&page="
					+ page;
			return service.downLoadBookList(remoteURL);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 成功返回书本的信息
	 * 
	 * @return
	 */
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
