package com.core.jdbc.dao;

import com.projo.Book;
import com.projo.Subject;

public interface IJdbcDAO {

	
	public String addBook(Subject subject);

	public String deleteSubject(Subject subject);

	public String deleteBook(Book book);

	public Book searchBook(Book book);

	public Subject searchSubject(Subject subjectForSearch);

}
