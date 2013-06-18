package com.daxia.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.model.BookStoreInfo;

/**
 * 适用于广东外语外贸大学图书馆的图书检索
 * 
 * @author kavilee@gmail.com
 * 
 */
public class GDUFSservice extends AbstractHttpService implements IHttpService {
	// 列表关键字
	private static final String LISTKEY = "<div class=itemtitle>";
	// 藏书情况关键字
	private static final String STOREKEY = "全部馆藏";
	// 藏书详细信息关键字
	private static final String DETAILKEY = "<!--Loan status-->";
	// 下一条列表链接的关键字
	private static final String NEXTLISTURLKEY = "<form name=\"cclform\" method=\"get\" action=\"";

	private String nextListURL; // 下一条链接

	private GDUFSservice() {
	}

	private static final GDUFSservice INSTANCE = new GDUFSservice();

	public static GDUFSservice getInstance() {
		return INSTANCE;
	}

	public String getNextListURL() {
		return nextListURL;
	}

	public void setNextListURL(String nextListURL) {
		this.nextListURL = nextListURL;
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
				if (line.indexOf(NEXTLISTURLKEY) != -1) {
					nextListURL = cutString(line, NEXTLISTURLKEY,
							"\" onsubmit=\"return presearch(this);\">");
				} else if (line.indexOf(LISTKEY) != -1) {
					// 跳转连接
					String sKey = "<a href=";
					String eKey = ">";
					int sIndex = line.indexOf(sKey);
					int eIndex = line.indexOf(eKey, sIndex);
					String bookDetailURL = line.substring(
							sIndex + sKey.length(), eIndex);
					// 书名
					sIndex = eIndex;
					eIndex = line.indexOf("</a>", sIndex);
					String bookName = line.substring(sIndex + 1, eIndex);
					skipLine(dis, 1);
					// 作者:
					line = dis.readLine();
					sKey = "class=content valign=top>";
					eKey = "<td";
					sIndex = line.indexOf(sKey);
					eIndex = line.indexOf(eKey, sIndex);
					String author = line.substring(sIndex + sKey.length(),
							eIndex);
					author = brNbToBlank(author);//调用
					// 索书号
					sIndex = line.indexOf(sKey, eIndex);
					String getNum = line.substring(sIndex + sKey.length())
							.trim();
					getNum = brNbToBlank(getNum);
					// 出版社
					line = dis.readLine();
					sIndex = line.indexOf(sKey);
					eIndex = line.indexOf(eKey, sIndex);
					String pubHouse = line.substring(sIndex + sKey.length(),
							eIndex);

					// 年份
					sIndex = line.indexOf(sKey, eIndex);
					String pubYear = line.substring(sIndex + sKey.length())
							.trim();

					skipLine(dis, 3);
					// 馆藏复本
					line = dis.readLine();
					String storeNum = cutString(line, "馆藏复本:", "，").trim();
					// 已出借复本
					String borrowed = cutString(line, "已出借复本:", "</a>").trim();

					String canBorrow = getCanBorrow(storeNum, borrowed);// 调用getCanBorrow

					Book book = new Book();
					book.setBookId(String.valueOf(new Date().getTime()));
					book.setBookDetailURL(bookDetailURL);
					book.setBookName(bookName);
					book.setAuthor(author);
					book.setPubHouse(pubHouse);
					book.setPubYear(pubYear);
					book.setGetNum(getNum);
					book.setStoreNum(storeNum);
					book.setCanBorrow(canBorrow);
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

	private String getCanBorrow(String storeNum, String borNum) {
		int sNum = Integer.parseInt(storeNum);
		int bNum = Integer.parseInt(borNum);
		int cNum;
		cNum = sNum - bNum;
		return cNum + "";
	}

	@Override
	public InputStream downLoadFile(String remoteURL) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book downLoadBookInfo(String remoteURL) throws IOException {
		Book book = new Book();
		String storeDetailURL = null;
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
				if (line.indexOf(STOREKEY) != -1) {
					skipLine(dis, 2);
					// 跳转连接
					line = dis.readLine();
					storeDetailURL = cutString(line, "<A HREF=", ">");
				}
				line = dis.readLine();
			}
			conn.disconnect();
			if (storeDetailURL != null) {
				bsList = downLoadStoreInfo(storeDetailURL);
				book.setStoreInfoList(bsList);
			}
			return book;
		} else if (code == 302) {
			// 重定向

		}
		return null;
	}

	public List<BookStoreInfo> downLoadStoreInfo(String remoteURL)
			throws IOException {
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
				if (line.indexOf(DETAILKEY) != -1) {
					while (line != null) {
						if (line.indexOf(DETAILKEY) != -1) {
							// 状态
							line = dis.readLine();
							String state = cutString(line, "td1>", "</td>");
							if (state.equals("已借出")) {
								skipLine(dis, 1);
								line = dis.readLine();
								state = cutString(line, "td1>", "</td>");
								state = state + "应还";
								skipLine(dis, 3);
							} else {
								skipLine(dis, 5);
							}

							// 馆藏地
							line = dis.readLine();
							String storePlace = cutString(line, "nowrap>",
									"</td>");

							skipLine(dis, 3);
							// 索取号
							line = dis.readLine();
							String getNum = cutString(line, "td1>", "</td>");

							skipLine(dis, 7);
							// 登录号
							line = dis.readLine();
							String loginNum = cutString(line, "td1>", "</td>");

							BookStoreInfo bsInfo = new BookStoreInfo();
							bsInfo.setGetNum(getNum);
							bsInfo.setLoginNum(loginNum);
							bsInfo.setState(state);
							bsInfo.setStorePlace(storePlace);

							bsList.add(bsInfo);
						}
						line = dis.readLine();
						// 结束
						if (line.indexOf("</table>") != -1) {
							conn.disconnect();
							return bsList;
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

	private String brNbToBlank(String str) {
		if (str.contains("<br>")) {
			str = str.replaceAll("<br>", "");
		}
		if (str.contains("&nbsp;")) {
			str = str.replaceAll("&nbsp;", "");
		}
		if (str.contains("<BR>")) {
			str = str.replaceAll("<BR>", "");
		}
		return str;
	}

	/**
	 * 截取字符串
	 * 
	 * @param line
	 * @param startKey
	 * @param endKey
	 * @return
	 */
	public String cutString(String line, String startKey, String endKey) {
		int start = line.indexOf(startKey);
		int end = line.indexOf(endKey);
		String rs = line.substring(start + startKey.length(), end);
		rs = brNbToBlank(rs);
		return rs;
	}

}
