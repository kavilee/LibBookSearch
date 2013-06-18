package com.daxia.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.daxia.model.Book;
import com.daxia.model.BookStoreInfo;
/**
 * 适用于广工图书馆的检索
 * @author yeguozhong@yeah.net
 *
 */
public class GDUTService extends AbstractHttpService implements IHttpService{
	//列表关键字
    private static final String LISTKEY = "<td><span class=\"title\">";
	//藏书情况关键字
    private static final String STOREKEY = "title=\"点击可查看此地点的详细介绍\"";
    
    private static final String ISNBKEY = "<span id=\"ctl00_ContentPlaceHolder1_bookcardinfolbl\">";
	
    private GDUTService(){}
    
    private static final GDUTService INSTANCE = new GDUTService();
    
    public static GDUTService getInstance(){
    	return INSTANCE;
    }
    
    @Override
	public List<Book> downLoadBookList(String remoteURL) throws IOException {
		setUrl(remoteURL);
		initGetConn();
		conn.connect();
		int code = conn.getResponseCode();
		if(code==200){
			//成功
			List<Book> bList = new ArrayList<Book>();
			InputStream in = conn.getInputStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(in));
			String line = dis.readLine();
			while(line!=null){
				if(line.indexOf(LISTKEY)!=-1){
				   //bookId
				   String bookId = cutString(line, "bookinfo.aspx?ctrlno=", "\" target=\"_blank\"");
				   //书名
				   String bookName = cutString(line, "target=\"_blank\">", "</a>");
				   //作者:
				   line = dis.readLine();
				   String author = cutString(line, "<td>", "</td>");
				   //出版社
				   line = dis.readLine();
				   String pubHouse = cutString(line, "<td>", "</td>");
				   //出版年份
				   line = dis.readLine();
				   String pubYear = cutString(line, "<td>", "</td>");
				   //索取号
				   line = dis.readLine();
				   String getNum = cutString(line, "<td class=\"tbr\">", "</td>");
				   //馆藏数量
				   line = dis.readLine();
				   String storeNum =  cutString(line, "<td class=\"tbr\">", "</td>");
				   //可借数量
				   line = dis.readLine();
				   String canBorrow =  cutString(line, "<td class=\"tbr\">", "</td>");
				   
				   //去书本详细信息的链接
				   String bookDetailURL = "http://222.200.98.171:81/bookinfo.aspx?ctrlno="+bookId;
				   
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
				   book.setSchool("广工");
				   bList.add(book);
				}
				line =dis.readLine();
			}
			conn.disconnect();
			return bList;
		}else if(code==302){
			//重定向
			
		}
		return null;
	}

	@Override
	public InputStream downLoadFile(String remoteURL) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
    
	@Override
	public Book downLoadBookInfo(String remoteURL)
			throws IOException {
		Book book = new Book();
		setUrl(remoteURL);
		initGetConn();
		conn.connect();
		int code = conn.getResponseCode();
		if(code==200){
			//成功
			List<BookStoreInfo> bsList = new ArrayList<BookStoreInfo>();
			InputStream in = conn.getInputStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(in));
			String line = dis.readLine();
			while(line!=null){
				if(line.indexOf(ISNBKEY)!=-1){
				   int start = line.indexOf("<br/><br/>")+10;
				   int end = line.indexOf("<br/>", start);
				   String ISBN = line.substring(start, end);
				   book.setISBN(ISBN);
				}else if(line.indexOf(STOREKEY)!=-1){
				   //馆藏地
				   String storePlace = cutString(line, "'>", "</a>");
				   //无效的三行
				   dis.readLine();
				   dis.readLine();
				   dis.readLine();
				   //索取号
				   line = dis.readLine();
				   String getNum = cutString(line, "<td>", "</td>");
				   //登录号
				   line = dis.readLine();
				   String loginNum = cutString(line, "<td>", "</td>");
				   //卷期
				   line = dis.readLine();
				   String volumnTime = cutString(line, "<td class=\"tbr\">", "</td>");
				   //年代
				   line = dis.readLine();
				   String year = cutString(line, "<td>", "</td>");
				   
				   //状态
				   dis.readLine();
				   line = dis.readLine();
				   String state =  line.trim();
				   dis.readLine();
				   dis.readLine();
				   //借阅类型
				   line = dis.readLine();
				   String bookType = cutString(line, "<td>", "</td>");
				 
				   BookStoreInfo bsInfo = new BookStoreInfo();
				   bsInfo.setBookType(bookType);
				   bsInfo.setGetNum(getNum);
				   bsInfo.setLoginNum(loginNum);
				   bsInfo.setState(state);
				   bsInfo.setStorePlace(storePlace);
				   bsInfo.setVolumnTime(volumnTime);
				   bsInfo.setYear(year);
				   bsList.add(bsInfo);
				}
				line =dis.readLine();
			}
			conn.disconnect();
			book.setStoreInfoList(bsList);
			return book;
		}else if(code==302){
			//重定向
			
		}
		return null;
	}
	

}
