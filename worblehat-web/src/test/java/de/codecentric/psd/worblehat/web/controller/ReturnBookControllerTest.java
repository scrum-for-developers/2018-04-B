package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnBookFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReturnBookControllerTest {

    private ReturnBookController returnBookController;

    private BookService bookService;

    private ReturnBookFormData returnBookFormData;

    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        returnBookController = new ReturnBookController(bookService);
        returnBookFormData = new ReturnBookFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    public void shouldSetupForm() throws Exception {
        ModelMap modelMap = new ModelMap();

        returnBookController.prepareView(modelMap);

        assertThat(modelMap.get("returnBookFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectErrors() throws Exception {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = returnBookController.returnBook(returnBookFormData, bindingResult);

        assertThat(navigateTo, is("returnBook"));
    }

    @Test
    public void shouldReturnBooksByIsbnAndNavigateHome() throws Exception {
        String borrower = "someone@codecentric.de";
        returnBookFormData.setEmailAddress(borrower);
        returnBookFormData.setIsbn("123456789X");

        when(bookService.returnBookByBorrowerAndIsbn(anyString(),anyString())).thenReturn(true);

        String navigateTo = returnBookController.returnBook(returnBookFormData, bindingResult);

        verify(bookService).returnBookByBorrowerAndIsbn(borrower,"123456789X");
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldReturnBooksByTitleAndNavigateHome() throws Exception {
        String borrower = "someone@codecentric.de";
        returnBookFormData.setEmailAddress(borrower);
        returnBookFormData.setTitle("Blabla");

        when(bookService.returnBookByBorrowerAndTitle(anyString(),anyString())).thenReturn(true);

        String navigateTo = returnBookController.returnBook(returnBookFormData, bindingResult);

        verify(bookService).returnBookByBorrowerAndTitle(borrower,"Blabla");
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldReturnBooksByTitleAndNavigateToReturnBookWhenTitleIsNotBorrowes() throws Exception {
        String borrower = "someone@codecentric.de";
        returnBookFormData.setEmailAddress(borrower);
        returnBookFormData.setTitle("Blabla");

        when(bookService.returnBookByBorrowerAndTitle(anyString(),anyString())).thenReturn(false);

        String navigateTo = returnBookController.returnBook(returnBookFormData, bindingResult);

        verify(bookService).returnBookByBorrowerAndTitle(borrower,"Blabla");
        assertThat(navigateTo, is("returnBook"));
    }

    @Test
    public void shouldReturnBooksByTitleAndNavigateToReturnBookWhenIsbnIsNotBorrowes() throws Exception {
        String borrower = "someone@codecentric.de";
        returnBookFormData.setEmailAddress(borrower);
        returnBookFormData.setIsbn("123456789X");

        when(bookService.returnBookByBorrowerAndIsbn(anyString(),anyString())).thenReturn(false);

        String navigateTo = returnBookController.returnBook(returnBookFormData, bindingResult);

        verify(bookService).returnBookByBorrowerAndIsbn(borrower,"123456789X");
        assertThat(navigateTo, is("returnBook"));
    }
}
