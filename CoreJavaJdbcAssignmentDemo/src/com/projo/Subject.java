package com.projo;

import java.util.HashSet;
import java.util.Set;

public class Subject {

	private String title;
	private int durationInHours;
	private Set<Book> bookList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDurationInHours() {
		return durationInHours;
	}

	public void setDurationInHours(int durationInHours) {
		this.durationInHours = durationInHours;
	}

	public Set<Book> getBookList() {
		if(bookList == null) {
			bookList = new HashSet<>();
		}
		return bookList;
	}

	public void setBookList(Set<Book> bookList) {
		this.bookList = bookList;
	}

	@Override
	public String toString() {
		StringBuilder suBuilder = new StringBuilder();
		suBuilder.append("Subject Title = ").append(title).append(", durationInHours = ").append(durationInHours)
		.append(", bookList = ").append(bookList.toString());
		return suBuilder.toString();
	}

}
