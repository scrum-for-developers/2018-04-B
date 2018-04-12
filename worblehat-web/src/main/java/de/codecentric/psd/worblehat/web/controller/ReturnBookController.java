package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
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
	public String returnAllBooks(
			@ModelAttribute("returnBookFormData") @Valid ReturnBookFormData formData,
			BindingResult result) {
		if (result.hasErrors()) {
			return "returnBook";
		} else {
			bookService.returnBookByBorrowerAndIsbn(formData.getEmailAddress(), formData.getIsbn());
			return "home";
		}
	}

}
