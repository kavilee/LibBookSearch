package com.daxia.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.model.BookStoreInfo;
import com.daxia.util.Utility;

public class SCNUService extends AbstractHttpService implements IHttpService {
	// 列表关键字
	private static final String LISTKEY = "<div class=\"list_books\" id=\"list_books\">";

	// 藏书列表关键字
	private static final String STOREKEY = "<td align=\"center\" bgcolor=\"#eeeeee\" class=\"greytext1\">书刊状态</td>";

	// 摘要
	private static final String BOOKINTRO = "提要文摘附注:";

	private String recordNum;

	private SCNUService() {
	}

	private static final SCNUService INSTANCE = new SCNUService();

	public static SCNUService getInstance() {
		return INSTANCE;
	}

	public String getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(String recordNum) {
		this.recordNum = recordNum;
	}

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
				if (line.indexOf(" 结果数：") != -1) {
					this.recordNum = cutString(line, "<strong class=\"red\">",
							"</strong>");
				} else if (line.indexOf(LISTKEY) != -1) {
					skipLine(dis, 2);
					// 获取图书类型
					line = dis.readLine();
					String startKey = "<span class=\"doc_type_class\">";
					String endKey = "</span>";
					int startIndex = line.indexOf(startKey);
					int endIndex = line.indexOf(endKey, startIndex);
					String bookType = line.substring(
							startIndex + startKey.length(), endIndex);
					// BookId
					startKey = "item.php?marc_no=";
					endKey = "\">";
					startIndex = line.indexOf(startKey, endIndex);
					endIndex = line.indexOf(endKey, startIndex);
					String bookId = line.substring(
							startIndex + startKey.length(), endIndex);
					// bookName
					startKey = ".";
					endKey = "</a>";
					startIndex = line.indexOf(startKey, endIndex);
					endIndex = line.indexOf(endKey, startIndex);
					String codeBName = line.substring(
							startIndex + startKey.length() + 2, endIndex);
					String bookName = Utility.HexUniToStr(codeBName);

					skipLine(dis, 1);
					// 馆藏复本
					line = dis.readLine();
					startKey = "</strong>";
					endKey = " <br";
					String storeNum = cutString(line, startKey, endKey);

					// 可借复本
					line = dis.readLine();
					startKey = "</strong>";
					endKey = " </span>";
					String canBorrow = cutString(line, startKey, endKey);

					// 编者
					line = dis.readLine();
					line = line.substring(0, line.lastIndexOf("<br />"));
					String author = Utility.HexUniToStr(line.trim());

					// 出版社
					line = dis.readLine();
					if (line.indexOf("&nbsp;") != -1) {
						line = line.replace("&nbsp;", "");
					}
					String pubHouse = Utility.HexUniToStr(line.trim());

					String bookDetailURL = "http://202.116.41.246:8080/opac/item.php?marc_no="
							+ bookId;

					Book book = new Book();
					book.setBookId(bookId);
					book.setBookName(bookName);
					book.setAuthor(author);
					book.setPubHouse(pubHouse);
					book.setCanBorrow(canBorrow);
					book.setStoreNum(storeNum);
					book.setBookType(bookType);
					book.setBookDetailURL(bookDetailURL);
					book.setSchool("华师");
					bList.add(book);
				}
				line = dis.readLine();
			}
			conn.disconnect();
			return bList;
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
			while (line != null) {
				if (line.indexOf(BOOKINTRO) != -1) {
					line = dis.readLine();
					String intro = cutString(line, "<dd>", "</dd>");
					book.setIntro(Utility.HexUniToStr(intro));
				} else if (line.indexOf(STOREKEY) != -1) {
					while (line != null) {
						if (line.indexOf("<tr>") != -1) {
							String cStart = "bgcolor=\"#FFFFFF\"  >";
							String cEnd = "</td>";
							// 索书号
							line = dis.readLine();
							String getNum = cutString(line, cStart, cEnd);
							// 条码号
							line = dis.readLine();
							String loginNum = cutString(line, cStart, cEnd);
							// 年卷期
							line = dis.readLine();
							String volumnTime = cutString(line, cStart, cEnd);
							volumnTime = volumnTime.replaceAll("&nbsp;", "");

							// 校区
							line = dis.readLine();
							String storeCampus = cutString(line, cStart, cEnd);
							// 馆藏地
							line = dis.readLine();
							String storePlace = cutString(line, cStart, cEnd);
							// 书刊状态
							line = dis.readLine();
							String state = cutString(line, cStart, cEnd);
							if (line.indexOf("<font color=green>") != -1) {
								state = cutString(state, "<font color=green>",
										"</font>");
							}

							BookStoreInfo bsModel = new BookStoreInfo();
							bsModel.setGetNum(getNum);
							bsModel.setLoginNum(loginNum);
							bsModel.setState(state);
							bsModel.setStorePlace(storePlace);
							bsModel.setVolumnTime(volumnTime);
							bsModel.setStoreCampus(storeCampus);
							bsModel.setState(state);

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

	@Override
	public InputStream downLoadFile(String remoteURL) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
