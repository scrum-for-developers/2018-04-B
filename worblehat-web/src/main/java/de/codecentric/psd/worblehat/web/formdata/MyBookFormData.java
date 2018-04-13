package de.codecentric.psd.worblehat.web.formdata;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Form data object from the borrow view.
 */
public class MyBookFormData {

	@NotEmpty(message = "{empty.borrowCmd.email}")
	@Email(message = "{notvalid.borrowCmd.email}")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
