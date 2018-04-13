package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
import de.codecentric.psd.worblehat.web.formdata.ReturnBookFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

//	@Value("${empty.returnBookFormData.isbnOrTitleMustBeSet}")
//	private String isbnOrTitleMustBeSetError;

	@RequestMapping(method = RequestMethod.POST)
	public String returnAllBooks(
			@ModelAttribute("returnBookFormData") @Valid ReturnBookFormData formData,
			BindingResult result) {

		if (isIsbnAndTitleNotSet(formData)){
		}

		if (result.hasErrors()) {
			return "returnBook";
		} else {
			if (formData.getIsbn() != null && !formData.getIsbn().trim().isEmpty()){
				if(!bookService.returnBookByBorrowerAndIsbn(formData.getEmailAddress(), formData.getIsbn().trim())){
					result.rejectValue("isbn", "error.user", "Cannot find a borrowed book with this isbn");
					return "returnBook";
				}
				return "home";

			}else if (formData.getTitle() != null && !formData.getTitle().trim().isEmpty()){
				if(!bookService.returnBookByBorrowerAndTitle(formData.getEmailAddress(), formData.getTitle().trim())){
					result.rejectValue("title", "error.user", "Cannot find a borrowed book with this title");
					return "returnBook";
				}
				return "home";
			}else{
				String isbnOrTitleMustBeSetError = "Please enter an ISBN or a title";
				result.rejectValue("isbn", "error.user", isbnOrTitleMustBeSetError);
				result.rejectValue("title", "error.user", isbnOrTitleMustBeSetError);
				return "returnBook";
			}
		}
	}

	private boolean isIsbnAndTitleNotSet(ReturnBookFormData formData){
		return (formData.getIsbn() == null || formData.getIsbn().trim().isEmpty()) && (formData.getTitle() == null || formData.getTitle().trim().isEmpty());
	}

}
