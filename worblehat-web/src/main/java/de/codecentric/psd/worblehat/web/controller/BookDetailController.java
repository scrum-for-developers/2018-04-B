package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/bookDetail")
public class BookDetailController {

	private BookService bookService;

	@Autowired
	public BookDetailController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET, params = {"id"})
	public String setupForm(ModelMap modelMap, @RequestParam(value = "id") Long id) {
		Book book = bookService.findBookById(id);
		modelMap.addAttribute("book", book);
		return "bookDetail";
	}

}
