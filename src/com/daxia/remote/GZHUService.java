package com.daxia.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.model.BookStoreInfo;

public class GZHUService extends AbstractHttpService implements IHttpService {
	// 列表关键字
	private static final String LISTKEY = "<div class=book_info>";
	// 藏书情况关键字
	private static final String STOREKEY = "<th>条码号</th>";
	// 索书号:
	private static final String GETNUMKEY = "索书号：</td><td>";

	private GZHUService() {
	}

	private static final GZHUService INSTANCE = new GZHUService();

	public static GZHUService getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Book> downLoadBookList(String remoteURL) throws IOException {
		setUrl(remoteURL);
		initGetConn();
		conn.connect();
		int code = conn.getResponseCode();
		if (code == 200) {
			// 成功
			List<Book> bList = new ArrayList<Book>();
			InputStream in = conn.getInputStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(in));
			String line = dis.readLine();
			while (line != null) {
				if (line.indexOf(LISTKEY) != -1) {
					// bookId
					line = dis.readLine();
					String bookId = cutString(line, "/bookle/search/detail/",
							"?");
					skipLine(dis, 1);
					// 书名
					line = dis.readLine();
					int endIndex = line.lastIndexOf("</a>");
					String bookName = line.substring(0, endIndex).trim();
					// 作者:
					line = dis.readLine();
					String author = cutString(line, "<span>", "</span>");
					// 出版社
					skipLine(dis, 1);
					line = dis.readLine();
					String pubHouse = cutString(line, "出版发行：", ", ");
					// 出版年份
					String sKeyWord = ", ";
					int startIndex = line.indexOf(sKeyWord);
					String pubYear = line.substring(
							startIndex + sKeyWord.length()).trim();
					// 索取号
					skipLine(dis, 5);
					line = dis.readLine();
					sKeyWord = "索书号：";
					startIndex = line.indexOf(sKeyWord);
					String getNum = line.substring(
							startIndex + sKeyWord.length()).trim();
					// 馆藏数量
					line = dis.readLine();
					sKeyWord = "复本数：";
					startIndex = line.indexOf(sKeyWord);
					String storeNum = line.substring(
							startIndex + sKeyWord.length()).trim();
					// 可借数量
					line = dis.readLine();
					sKeyWord = "在馆数：";
					startIndex = line.indexOf(sKeyWord);
					String canBorrow = line.substring(
							startIndex + sKeyWord.length()).trim();

					// 去书本详细信息的链接
					String bookDetailURL = "http://lib.gzhu.edu.cn:8080/bookle/search/detail/"
							+ bookId + "?index=default&source=biblios";

					Book book = new Book();
					book.setBookId(bookId);
					book.setBookName(bookName);
					book.setAuthor(author);
					book.setPubHouse(pubHouse);
					book.setPubYear(pubYear);
					book.setGetNum(getNum);
					book.setStoreNum(storeNum);
					book.setCanBorrow(canBorrow);
					book.setBookDetailURL(bookDetailURL);
					book.setSchool("广大");
					bList.add(book);
				}
				line = dis.readLine();
			}
			conn.disconnect();
			return bList;
		} else if (code == 302) {
			// 重定向

		}
		return null;
	}

	@Override
	public Book downLoadBookInfo(String remoteURL) throws IOException {
		Book book = new Book();
		setUrl(remoteURL);
		initGetConn();
		conn.connect();
		int code = conn.getResponseCode();
		if (code == 200) {
			// 成功
			List<BookStoreInfo> bsList = new ArrayList<BookStoreInfo>();
			InputStream in = conn.getInputStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(in));
			String line = dis.readLine();
			String getNum = null;
			while (line != null) {
				if (line.indexOf(GETNUMKEY) != -1) {
					getNum = cutString(line, GETNUMKEY, "</td></tr>");
				} else if (line.indexOf(STOREKEY) != -1) {
					while (line != null) {
						if (line.indexOf("<tr>") != -1) {
							String cStart = "<td>";
							String cEnd = "</td>";
							// 条码号
							line = dis.readLine();
							String loginNum = nbspToBlank(line, cStart, cEnd);
							// 书刊状态
							line = dis.readLine();
							String state = nbspToBlank(line, cStart, cEnd);
							if (state.equals("借出")) {
								// 借出日期
								line = dis.readLine();
								String borDay = nbspToBlank(line, cStart, cEnd);
								// 应还日期
								line = dis.readLine();
								String retDay = nbspToBlank(line, cStart, cEnd);
								state = "应还日期:" + retDay;
							} else {
								skipLine(dis, 2);
							}
							// 馆藏地
							line = dis.readLine();
							String storePlace = nbspToBlank(line, cStart, cEnd);
							// 流通类型
							line = dis.readLine();
							String bookType = nbspToBlank(line, cStart, cEnd);
							// 年卷期
							line = dis.readLine();
							String volumnTime = nbspToBlank(line, cStart, cEnd);

							BookStoreInfo bsModel = new BookStoreInfo();
							bsModel.setGetNum(getNum);
							bsModel.setLoginNum(loginNum);
							bsModel.setState(state);
							bsModel.setStorePlace(storePlace);
							bsModel.setVolumnTime(volumnTime);
							bsModel.setState(state);
							bsModel.setBookType(bookType);

							bsList.add(bsModel);
						}

						line = dis.readLine();
						// 结束
						if (line.indexOf("</table>") != -1) {
							conn.disconnect();
							book.setStoreInfoList(bsList);
							return book;
						}
					}
				}
				line = dis.readLine();
			}
		} else if (code == 302) {
			// 重定向

		}
		return null;
	}

	private String nbspToBlank(String line, String startKey, String endKey) {
		String rs = cutString(line, startKey, endKey);
		if (rs.contains("&nbsp;")) {
			rs = rs.replaceAll("&nbsp;", " ");
		}
		return rs;
	}

	@Override
	public InputStream downLoadFile(String remoteURL) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
