package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnBookFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controller class for the
 */
@Controller
@RequestMapping("/returnBook")
public class ReturnBookController {

    private BookService bookService;

    @Autowired
    public ReturnBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void prepareView(ModelMap modelMap) {
        modelMap.put("returnBookFormData", new ReturnBookFormData());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String returnBook(
            @ModelAttribute("returnBookFormData") @Valid ReturnBookFormData formData,
            BindingResult result) {
        if (result.hasErrors()) {
            return "returnBook";
        }
        return returnBorrowedBook(formData, result);

    }

    private String returnBorrowedBook(ReturnBookFormData formData, BindingResult result) {
        if (isNotEmptyOrNull(formData.getIsbn())) {
            return handleReturnByIsbn(formData, result);
        } else if (isNotEmptyOrNull(formData.getTitle())) {
            return handleReturnByTitle(formData, result);
        }
        String isbnOrTitleMustBeSetError = "Please enter an ISBN or a title";
        result.rejectValue("isbn", "error.user", isbnOrTitleMustBeSetError);
        result.rejectValue("title", "error.user", isbnOrTitleMustBeSetError);
        return "returnBook";
    }

    private String handleReturnByTitle(ReturnBookFormData formData, BindingResult result) {
        if (bookService.returnBookByBorrowerAndTitle(formData.getEmailAddress(), formData.getTitle().trim())) {
            return "home";
        }
        result.rejectValue("title", "error.user", "Cannot find a borrowed book with this title");
        return "returnBook";
    }

    private String handleReturnByIsbn(ReturnBookFormData formData, BindingResult result) {
        if (bookService.returnBookByBorrowerAndIsbn(formData.getEmailAddress(), formData.getIsbn().trim())) {
            return "home";
        }
        result.rejectValue("isbn", "error.user", "Cannot find a borrowed book with this isbn");
        return "returnBook";
    }

    private boolean isNotEmptyOrNull(String isbn) {
        return isbn != null && !isbn.trim().isEmpty();
    }
}
