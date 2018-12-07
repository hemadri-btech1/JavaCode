package com.core.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.jdbc.dao.IJdbcDAO;
import com.core.jdbc.dao.JdbcDAOImpl;
import com.projo.Book;
import com.projo.Subject;

public class CoreJdbcDemo {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		readInputFromUser();
	}

	/**
	 * Menu option to read a input from USER.....
	 */
	private static void readInputFromUser() {

		System.out.println("-----Select any option in the below list------");

		System.out.println("a. Add a Book");
		System.out.println("b. Delete a Subject");
		System.out.println("c. Delete a book");
		System.out.println("d. Search for a book");
		System.out.println("e. Search for a subject");
		System.out.println("f. Exit");

		System.out.println("---------------------------------------------");

		BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));

		try {
			String userInput = br.readLine();
			if (isStrEmpty(userInput)) {
				System.out.println("The input option shouldn't be empty - please enter valid option...");
				readInputFromUser();
			}
			switch (userInput.toLowerCase()) {

			case "a":
				Subject subject = addBook();
				IJdbcDAO jdbcDAO = new JdbcDAOImpl();
				jdbcDAO.addBook(subject);

				break;
			case "b":
				System.out.println("Enter the subject title to be deleted...");
				String subjectForDelete = br.readLine();
				if (isStrEmpty(subjectForDelete)) {
					System.out.println("subjectForDelete shouldn't be empty...");
				} else {
					Subject subject1 = new Subject();
					IJdbcDAO jdbcDAO1 = new JdbcDAOImpl();
					jdbcDAO1.deleteSubject(subject1);
				}

				break;
			case "c":
				System.out.println("Userinput - Delete a book ");
				System.out.println("Input a bookid to be deleted......\n");
				String bookIdStr = br.readLine();
				Book book = new Book();
				int bookid = Integer.parseInt(bookIdStr);
				IJdbcDAO jdbcDAO1 = new JdbcDAOImpl();
				book.setBookid(bookid);
				jdbcDAO1.deleteBook(book);
				break;
			case "d":
				System.out.println("Userinput - Search for a book ");
				System.out.println("Input a bookid to be searched......\n");
				String bookIdForSearch = br.readLine();
				Book bookForSearch = new Book();
				int bookid1 = Integer.parseInt(bookIdForSearch);
				IJdbcDAO jdbcDAO2= new JdbcDAOImpl();
				bookForSearch.setBookid(bookid1);
				Book bookresult = jdbcDAO2.searchBook(bookForSearch);
				System.out.println("Result Book is : " + bookresult);
				break;
			case "e":
				System.out.println("Userinput - Search for a subject ");
				break;
			case "f":
				System.out.println("Userinput - Exit ");
				System.out.println("Exiting....");
				return;
			default:
				System.out.println(
						"No option selected in the list..Select any of the options like a or b or c or d or e or f ");
				readInputFromUser();
				break;

			}

		} catch (IOException e) {
			System.out.println("Exception while reading an input from user..." + e.getMessage());
		}
	}

	private static boolean isStrEmpty(String userInput) {
		return userInput == null || userInput.length() == 0;
	}

	private static Subject addBook() {

		Subject subject = new Subject();

		BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));
		try {

			readSubjectFromUser(subject, br);

			String noOfBooks = br.readLine();
			int noOfBooksInt = Integer.parseInt(noOfBooks);

			Set<Book> bookList = readBookListFromUser(br, noOfBooksInt);

			subject.setBookList(bookList);

			System.out.println("The input data from User is : " + subject);

		} catch (IOException e) {
			System.out.println("Exception while reading a data from input/output system.....");
		}

		return subject;
	}

	private static Set<Book> readBookListFromUser(BufferedReader br, int noOfBooksInt) throws IOException {
		int i = 1;

		Set<Book> bookList = new HashSet<>();

		while (noOfBooksInt >= 1) {

			System.out.println("Enter the Book Number : " + i);
			Book book = new Book();
			System.out.println("Input a Book Title....");

			String bookTitle = br.readLine();

			if (bookTitle == null || bookTitle.trim().length() == 0) {
				throw new RuntimeException("Title should not be empty/null...");
			}
			System.out.println("Input a Book price in (double)....");

			String priceInStr = br.readLine();
			if (priceInStr == null || priceInStr.trim().length() == 0) {
				throw new RuntimeException("Price should not be empty/null.. It should be double number...");
			}
			double price = Double.parseDouble(priceInStr);

			System.out.println("Input a Book volume in integer....");

			String volumeStr = br.readLine();
			if (volumeStr == null || volumeStr.trim().length() == 0) {
				throw new RuntimeException("Volume should not be empty/null.. It should be integer number...");
			}
			int volume = Integer.parseInt(volumeStr);

			Date date = readAndParseDate(br);

			System.out.println("Date is : " + date);
			LocalDate publishDate = convertToLocalDateViaInstant(date);

			book.setPrice(price);
			book.setPublishDate(publishDate);
			book.setTitle(bookTitle);
			book.setVolume(volume);
			bookList.add(book);

			noOfBooksInt--;
			i++;
		}
		return bookList;
	}

	private static Date readAndParseDate(BufferedReader br) throws IOException {
		System.out.println("Input a Book published date in the format dd/mm/yyyy....");
		String publishedDateStr = br.readLine();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		Date date = null;
		try {
			date = sdf.parse(publishedDateStr);
		} catch (ParseException e) {
			System.out.println("Exception in the date format....");
			readAndParseDate(br);
			// throw new RuntimeException("Exception in the date format....");
		}
		return date;
	}

	private static void readSubjectFromUser(Subject subject, BufferedReader br) throws IOException {
		System.out.println("Input a Subject Title....");

		String subjectTile = br.readLine();

		System.out.println("Input a durationInHours in number....");

		String durationInHoursStr = br.readLine();
		int durationInHours = Integer.parseInt(durationInHoursStr);

		subject.setDurationInHours(durationInHours);
		subject.setTitle(subjectTile);

		System.out.println("How many books you wanted to add for the Subject...." + subjectTile);
	}

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
