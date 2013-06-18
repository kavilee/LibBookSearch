package com.daxia.model;

import java.util.List;

public class Book {
	private String bookId;
	private String thumbnail;
	private String bookName;
	private String author;
	private String pubHouse;
	private String pubYear;
	private String getNum;
	private String storeNum;
	private String canBorrow;
	private boolean favorite;
	private String imageUrl;
	private List<BookStoreInfo> storeInfoList;
	private String intro;
	private String school;
    private String ISBN;
    private String bookType;
    private String bookDetailURL;
    
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getBookDetailURL() {
		return bookDetailURL;
	}

	public void setBookDetailURL(String bookDetailURL) {
		this.bookDetailURL = bookDetailURL;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubHouse() {
		return pubHouse;
	}

	public void setPubHouse(String pubHouse) {
		this.pubHouse = pubHouse;
	}

	public String getPubYear() {
		return pubYear;
	}

	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	public String getGetNum() {
		return getNum;
	}

	public void setGetNum(String getNum) {
		this.getNum = getNum;
	}

	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	public String getCanBorrow() {
		return canBorrow;
	}

	public void setCanBorrow(String canBorrow) {
		this.canBorrow = canBorrow;
	}

	public boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<BookStoreInfo> getStoreInfoList() {
		return storeInfoList;
	}

	public void setStoreInfoList(List<BookStoreInfo> storeInfoList) {
		this.storeInfoList = storeInfoList;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		return true;
	}

}
