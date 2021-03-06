package de.codecentric.psd.worblehat.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
public class StandardBookService implements BookService {

    private BorrowingRepository borrowingRepository;
    private BookRepository bookRepository;

    @SuppressWarnings("unused")
    public StandardBookService() {
        // Empty public constructor for injection
    }

    @Autowired
    public StandardBookService(BorrowingRepository borrowingRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void returnAllBooksByBorrower(String borrowerEmailAddress) {
        List<Borrowing> borrowingsByUser = borrowingRepository
                .findBorrowingsByBorrower(borrowerEmailAddress);
        for (Borrowing borrowing : borrowingsByUser) {
            borrowingRepository.delete(borrowing);
        }
    }

    @Override
    public Optional<Borrowing> borrowBook(String isbn, String borrower) {
        Set<Book> books = bookRepository.findByIsbn(isbn);

        Optional<Book> unborrowedBook = books.stream()
                .filter(book -> book.getBorrowing() == null)
                .findFirst();

        return unborrowedBook.map(book -> {
            book.borrowNowByBorrower(borrower);
            borrowingRepository.save(book.getBorrowing());
            return book.getBorrowing();
        });
    }

    @Override
    public Set<Book> findBooksByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn); //null if not found
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllByOrderByTitle();
    }

    @Override
    public List<Borrowing> findMyBooksAsBorrowings(String borrower) {
        return borrowingRepository.findBorrowingsByBorrower(borrower);
    }

    @Override
    public List<Book> findMyBooksAsBooks(String borrower) {
        List<Borrowing> borrowings = findMyBooksAsBorrowings(borrower);
        List<Book> bookList = new ArrayList<>();
        for (Borrowing borrowing : borrowings) {
            bookList.add(borrowing.getBorrowedBook());
        }
        return bookList;
    }

    @Override
    public Optional<Book> createBook(@Nonnull String title,
                                     @Nonnull String author,
                                     @Nonnull String edition,
                                     @Nonnull String isbn,
                                     int yearOfPublication,
                                     String bookCoverImageURL,
                                     String description) {
        Book book = new Book(title, author, edition, isbn, yearOfPublication, bookCoverImageURL, description);

        Optional<Book> bookFromRepo = bookRepository.findTopByIsbn(isbn);

        if (!bookFromRepo.isPresent() || book.isSameCopy(bookFromRepo.get())) {
            return Optional.of(bookRepository.save(book));
        } else
            return Optional.empty();
    }

    @Override
    public boolean bookExists(String isbn) {
        Set<Book> books = bookRepository.findByIsbn(isbn);
        return !books.isEmpty();
    }

    @Override
    public void deleteAllBooks() {
        borrowingRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public boolean returnBookByBorrowerAndIsbn(String borrowerEmailAddress, String isbn) {


        List<Borrowing> borrowingsByUser = borrowingRepository
                .findBorrowingsByBorrower(borrowerEmailAddress);

        Optional<Borrowing> found = borrowingsByUser.stream()
                .filter(borrowing -> isbn != null && isbn.equalsIgnoreCase(borrowing.getBorrowedBook().getIsbn()))
                .findFirst();

        if (found.isPresent()) {
            borrowingRepository.delete(found.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean returnBookByBorrowerAndTitle(String borrowerEmailAddress, String title) {

        List<Borrowing> borrowingsByUser = borrowingRepository
                .findBorrowingsByBorrower(borrowerEmailAddress);


        Optional<Borrowing> found = borrowingsByUser.stream()
                .filter(borrowing -> title != null && title.equalsIgnoreCase(borrowing.getBorrowedBook().getTitle()))
                .findFirst();

        if (found.isPresent()) {
            borrowingRepository.delete(found.get());
            return true;
        }
        return false;
    }


}
