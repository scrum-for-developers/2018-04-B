package de.codecentric.psd.worblehat.domain;

import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity implementation class for Entity: Book
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String title;

    @Column(columnDefinition = "text")
    private String description;
    private String author;
    private String edition;

    // TODO: convert String to an ISBN class, that ensures a valid ISBN
    private String isbn;
    private int yearOfPublication;

    private String bookCoverImageURL;

    @OneToOne(mappedBy = "borrowedBook", orphanRemoval = true)
    private Borrowing borrowing;

    /**
     * Empty constructor needed by Hibernate.
     */
    private Book() {
        super();
    }

    /**
     * Creates a new book instance.
     *
     * @param title             the title
     * @param author            the author
     * @param edition           the edition
     * @param isbn              the isbn
     * @param yearOfPublication the yearOfPublication
     */
    public Book(@Nonnull String title,
                @Nonnull String author,
                @Nonnull String edition,
                @Nonnull String isbn,
                int yearOfPublication) {
        super();
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
    }

    public Book(@Nonnull String title, @Nonnull String author, @Nonnull String edition, @Nonnull String isbn, int yearOfPublication, String bookCoverImageURL, String description) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
        this.bookCoverImageURL = bookCoverImageURL;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Borrowing getBorrowing() {
        return borrowing;
    }

    public String getBookCoverImageURL() {
        return bookCoverImageURL;
    }

    public void setBookCoverImageURL(String bookCoverImageURL) {
        this.bookCoverImageURL = bookCoverImageURL;
    }

    public boolean hasBookCover() {
        return this.bookCoverImageURL != null && !this.bookCoverImageURL.isEmpty();
    }

    boolean isSameCopy(@Nonnull Book book) {
        return getTitle().equals(book.title) && getAuthor().equals(book.author) && getEdition().equals(book.edition);
    }

    public void borrowNowByBorrower(String borrowerEmailAddress) {
        if (borrowing == null) {
            this.borrowing = new Borrowing(this, borrowerEmailAddress);
        }
    }

    public String getDescription() {
        if (description == null) {
            return "";
        }
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReturnDate() {
        if (borrowing == null) {
            return null;
        }

        return new DateTime(borrowing.getBorrowDate()).plusDays(14).toDate();
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", isbn='" + isbn + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }

    public long getId() {
        return id;
    }
}
