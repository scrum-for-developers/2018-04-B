package de.codecentric.worblehat.acceptancetests.adapter.wrapper;

public class HtmlBook {

	private String title;
    private String author;
    private String edition;
    private String isbn;
    private String yearOfPublication;
    private String borrower;
    private String returnDate;

    public HtmlBook() {
        title = author = edition = isbn = yearOfPublication = borrower = "";
    }

    public HtmlBook(String title, String author, String yearOfPublication, String edition, String isbn, String borrower) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
        this.borrower = borrower;
    }

    public HtmlBook(String title, String author, String edition, String isbn, String yearOfPublication, String borrower, String returnDate) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
        this.borrower = borrower;
        this.returnDate = returnDate;
    }

    public String getTitle() {
        return title;
    }

	public void setTitle(String title) {
		this.title = title;
	}

    public String getAuthor() {
        return author;
    }

	public void setAuthor(String author) {
		this.author = author;
	}

    public String getEdition() {
        return edition;
    }

	public void setEdition(String edition) {
		this.edition = edition;
	}

    public String getIsbn() {
        return isbn;
    }

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

    public String getYearOfPublication() {
        return yearOfPublication;
    }

	public void setYearOfPublication(String yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

    public String getBorrower() {
        return borrower;
    }

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
