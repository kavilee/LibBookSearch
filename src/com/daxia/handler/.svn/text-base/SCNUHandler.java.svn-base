package com.daxia.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.remote.SCNUService;

public class SCNUHandler extends GenericHanlder {
	private SCNUService service = SCNUService.getInstance();
	private final static SCNUHandler instance = new SCNUHandler();

	private SCNUHandler() {
	}

	public static SCNUHandler getInstance() {
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
			String remoteURL = "";
			keyWord = URLEncoder.encode(keyWord, "UTF-8");
			if (page.equals(1)) {
				remoteURL = "http://202.116.41.246:8080/opac/openlink.php?strSearchType=title&historyCount=1&strText="
						+ keyWord
						+ "&doctype=ALL&match_flag=forward&displaypg=20&sort=M_TITLE&orderby=asc&showmode=list&dept=ALL&x=0&y=0";
			} else {
				remoteURL = "http://202.116.41.246:8080/opac/openlink.php?dept=ALL&title="
						+ keyWord
						+ "&doctype=ALL&lang_code=ALL&match_flag=forward&displaypg=20&showmode=list&orderby=ASC&sort=M_TITLE&onlylendable=no&count=1142&page="
						+ page;
			}
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
