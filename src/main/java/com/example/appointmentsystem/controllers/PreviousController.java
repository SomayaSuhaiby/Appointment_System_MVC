package com.example.appointmentsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/previous")
public class PreviousController {

	// Back to index.html page
	@GetMapping("/backToIndexPage")
	public String backToIndex() {
		return "index";
	}

	// Back to admin.html page
	@GetMapping("/backToAdminPage")
	public String backToAdmin() {
		return "admin";
	}

	// Back to login.html page
	@GetMapping("/backToLoginPage")
	public String backToLogin() {
		return "login";
	}

	// Back to user.html page
	@GetMapping("/backToUserPage")
	public String backToUserPage() {
		return "user";
	}

}
