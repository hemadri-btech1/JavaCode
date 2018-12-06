package com.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.core.jdbc.JdbcConnectionFactory;
import com.projo.Book;
import com.projo.Subject;

public class JdbcDAOImpl implements IJdbcDAO {

	private static final String INSERT_INTO_BOOK_TITLE_PRICE_VOLUME_PUBLISH_DATE_FK_SUBJECT_ID_VALUES = "INSERT INTO Book (title, price,volume,publishDate,Fk_Subject_Id) values (?, ?, ?, ?, ?)";
	private static final String SUBJECT_DETAILS_ARE_SAVED_SUCCESSFULLY_WITH_ID = "Subject details are saved successfully With ID : ";
	private static final String INSERT_INTO_SUBJECT_TITLE_DURATION_IN_HOURS_VALUES = " INSERT INTO Subject (title, durationInHours) values (?,?) ";

	@Override
	public String addBook(Subject subject) {

		Connection connection = JdbcConnectionFactory.getConnection();

		try {
			// Insert parent component - Subject
			int subjectId = insertSubject(subject, connection);

			// If Subject is non zero - the Subject is saved successfully
			if (subjectId > 0) {
				for(Book book : subject.getBookList()) {
					insertBook(book, connection, subjectId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing the connection.....");
				}
			}
		}

		return null;
	}

	private void insertBook(Book book, Connection connection, int subjectId) throws SQLException {
		PreparedStatement pstmt;
		ResultSet rs;
		pstmt = connection.prepareStatement(INSERT_INTO_BOOK_TITLE_PRICE_VOLUME_PUBLISH_DATE_FK_SUBJECT_ID_VALUES);
		pstmt.setString(1, book.getTitle());
		pstmt.setDouble(2, book.getPrice());
		pstmt.setInt(3, book.getVolume());
		java.sql.Date sqlDate = java.sql.Date.valueOf(book.getPublishDate());
		pstmt.setDate(4, sqlDate);
		pstmt.setInt(5, subjectId);
		int noOfBooks = pstmt.executeUpdate();
		System.out.println("noOfBooks : " + noOfBooks);
		rs = pstmt.getGeneratedKeys();
		while (rs.next()) {
			if (noOfBooks > 0) {
				System.out.println("Book details are saved successfully with ID : " + rs.getInt(1));
			}
		}
	}

	/**
	 * 
	 * @param subject
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private int insertSubject(Subject subject, Connection connection) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int subjectId = 0;
		try {
			pstmt = connection.prepareStatement(INSERT_INTO_SUBJECT_TITLE_DURATION_IN_HOURS_VALUES);

			pstmt.setString(1, subject.getTitle());
			pstmt.setInt(2, subject.getDurationInHours());

			int numRowsAffected = pstmt.executeUpdate();
			System.out.println("Number of rows affected..." + numRowsAffected);
			rs = pstmt.getGeneratedKeys();

			while (rs.next()) {
				subjectId = rs.getInt(1);
				System.out.println(SUBJECT_DETAILS_ARE_SAVED_SUCCESSFULLY_WITH_ID + subjectId);

			}
		} catch (SQLException e) {
			System.out.println("SQLException while inserting Subject details....." + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return subjectId;
	}

	@Override
	public String deleteSubject(Subject subject) {
		
		Connection connection = JdbcConnectionFactory.getConnection();

		//To Delete a subject - we should first delete child component BOOK
		//Retrieve BOOK ID for the given title
		
		
		
		
		return null;
	}

	@Override
	public String deleteBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book searchBook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject searchSubject() {
		// TODO Auto-generated method stub
		return null;
	}

}
