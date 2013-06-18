package com.daxia.remote;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.daxia.model.Book;

/**
 * 
 * @author yeguozhong@yeah.net
 * 
 */
public interface IHttpService {
	/**
	 * 下载一个搜索结果网页
	 * 
	 * @param remoteURL
	 * @return
	 * @throws IOException
	 */
	List<Book> downLoadBookList(String remoteURL) throws IOException;
     
	/**
	 * 下载一本书的藏书信息
	 * @param remoteURL
	 * @return
	 * @throws IOException
	 */
	Book downLoadBookInfo(String remoteURL) throws IOException;
	/**
	 * 下载文件
	 * 
	 * @param remoteURL
	 * @return
	 * @throws IOException
	 */
	InputStream downLoadFile(String remoteURL) throws IOException;
}
