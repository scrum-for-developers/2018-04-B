package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.MyBookFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/viewMyBooks")
public class MyBookListController {

    public static final String MY_BOOK_LIST_TEMPLATE_NAME = "myBookList";

    private BookService bookService;

    @Autowired
    public MyBookListController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(final ModelMap model) {
        model.put("myBookData", new MyBookFormData());
        return MY_BOOK_LIST_TEMPLATE_NAME;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("myBookData") @Valid MyBookFormData myBookData,
                                BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            return MY_BOOK_LIST_TEMPLATE_NAME;
        }

        List<Book> books = bookService.findMyBooksAsBooks(myBookData.getEmail());

        if(books.isEmpty()) {
            result.rejectValue("email", "noBorrowedBookExists");
            return MY_BOOK_LIST_TEMPLATE_NAME;
        }

        modelMap.addAttribute("myBooks", books);
        return MY_BOOK_LIST_TEMPLATE_NAME;
    }

    @ExceptionHandler(Exception.class)
    public String handleErrors(Exception ex, HttpServletRequest request) {
        return "home";
    }

}
