package com.daxia.handler;

import java.util.List;

import com.daxia.model.Book;

public interface IHanlder {
	List<Book> handleBookList();

	Book handleBookInfo();
}
