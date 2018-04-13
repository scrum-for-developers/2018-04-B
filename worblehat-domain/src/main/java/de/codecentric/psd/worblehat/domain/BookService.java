package de.codecentric.psd.worblehat.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface of the domain service for books.
 */
public interface BookService {

	void returnAllBooksByBorrower(String string);

	Optional<Borrowing> borrowBook(String isbn, String borrower);

	Set<Book> findBooksByIsbn(String isbn);

	List<Book> findAllBooks();

	Book findBookById(Long id);

	Optional<Book> createBook(String title, String author, String edition, String isbn, int yearOfPublication, String bookCoverImageURL, String description);

	boolean bookExists(String isbn);

	void deleteAllBooks();


	/**
	 * Return true is book was deleted
	 * @param emailAddress
	 * @param isbn
	 * @return
	 */
    boolean returnBookByBorrowerAndIsbn(String emailAddress, String isbn);

	/**
	 * Return true is book was deleted
	 * @param emailAddress
	 * @param title
	 * @return
	 */
    boolean returnBookByBorrowerAndTitle(String emailAddress, String title);

	List<Borrowing> findMyBooksAsBorrowings(String borrower);

	List<Book> findMyBooksAsBooks(String borrower);
}
