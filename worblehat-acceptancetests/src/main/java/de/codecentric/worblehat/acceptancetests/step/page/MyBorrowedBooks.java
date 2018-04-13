package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Then;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Component("MyBorrowedBooks")
public class MyBorrowedBooks {

    private SeleniumAdapter seleniumAdapter;

    @Autowired
    public MyBorrowedBooks(SeleniumAdapter seleniumAdapter) {
        this.seleniumAdapter = seleniumAdapter;
    }

    @Then("the book list for user $borrower contains only one book with isbn $isbn and return date 14 days from today")
    public void bookListContainsRowWithValues(final String user,
                                              final String isbn) {
        seleniumAdapter.gotoPage(Page.VIEWMYBOOKS);
        seleniumAdapter.typeIntoField("email", user);
        seleniumAdapter.clickOnPageElement(PageElement.SHOWMYBOOKSBUTTON);

        waitForServerResponse();

        HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        assertThat(htmlBookList.size(), is(1));

        HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
        assertThat(isbn, is(htmlBook.getIsbn()));

        assertThat(DateTime.now().plusDays(14).toString("dd-MMM-yyyy"), is(htmlBook.getReturnDate()));
    }


    private void waitForServerResponse() {
        // normally you would have much better mechanisms for waiting for a
        // server response. We are choosing a simple solution for the sake of this
        // training
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // pass
        }
    }



    private HashMap<String, String> createRowMap(final String title, final String author, final String year,
                                                 final String edition, final String isbn, final String borrower) {
        return new HashMap<String, String>(){
            {
                put("Title", title);
                put("Author", author);
                put("Year", year);
                put("Edition", edition);
                put("ISBN", isbn);
                put("Borrower", borrower);
            }
        };
    }

}
