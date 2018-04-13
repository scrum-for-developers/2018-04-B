package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReturnBook {
	private final SeleniumAdapter seleniumAdapter;

	@Autowired
	public ReturnBook(SeleniumAdapter seleniumAdapter) {
		this.seleniumAdapter = seleniumAdapter;
	}
	
	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****
	// *****************
//	Given an empty library
//	And borrower <borrower> has borrowed books <isbns1>
//	When borrower <borrower> returns the book with isbn <returnisbn>
//	Then the book <returnisbn> are not borrowed anymore by borrower <borrower>


	@When("borrower $borrower returns the book with isbn $returnisbn")
	public void whenUseruserReturnsAllHisBooks(String borrower, String isbn) {
		seleniumAdapter.gotoPage(Page.RETURNBOOK);
		seleniumAdapter.typeIntoField("emailAddress", borrower);
		seleniumAdapter.typeIntoField("isbn", isbn);
		seleniumAdapter.clickOnPageElement(PageElement.RETURNBOOKBUTTON);
	}

}
