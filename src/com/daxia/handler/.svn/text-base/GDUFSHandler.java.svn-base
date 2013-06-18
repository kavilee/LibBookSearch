package com.daxia.handler;

import java.io.IOException;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.remote.GDUFSservice;

public class GDUFSHandler extends GenericHanlder {
	private GDUFSservice service = GDUFSservice.getInstance();

	private GDUFSHandler() {
	}

	private static final GDUFSHandler INSTANCE = new GDUFSHandler();

	public static GDUFSHandler getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Book> handleBookList() {
		Integer page = getPage();
		String keyWord = getKeyWord();
		if (page == null || keyWord == null) {
			return null;
		}
		String nextListURL = "";
		if (page == 1) {
			nextListURL = "http://opac.gdufs.edu.cn:8991/F/?find_code=WRD&request="
					+ keyWord
					+ "&x=37&y=9&func=find-b&filter_code_1=WLN&filter_request_1=&filter_code_2=WYR&filter_request_2=&filter_code_3=WYR&filter_request_3=&filter_code_4=WFM&filter_request_4=&filter_code_5=WSL&filter_request_5=";
		} else {
			nextListURL = service.getNextListURL() + "?func=short-jump&jump="
					+ ((page - 1) * 10 + 1);
		}
		try {
			return service.downLoadBookList(nextListURL);
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