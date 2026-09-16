package com.example.appointmentsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.appointmentsystem.DTOs.LoginRequestDTO;
import com.example.appointmentsystem.DTOs.UserDTO;
import com.example.appointmentsystem.exceptions.InvalidCredentialsException;
import com.example.appointmentsystem.exceptions.RoleNotFoundException;
import com.example.appointmentsystem.exceptions.UserAlreadyExistsException;
import com.example.appointmentsystem.model.Role;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	public final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register"; // register.html
	}

	// Registeration
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute UserDTO dto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register";
		}

		try {
			userService.register(dto);
			return "redirect:/users/login";
		} catch (UserAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}

		catch (RoleNotFoundException e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; // login.html
	}

	// login
	@PostMapping("/login")
	public String login(@ModelAttribute LoginRequestDTO dto, Model model, HttpSession session) {

		try {
			User foundUser = userService.login(dto);
			// Set user ID in session
			session.setAttribute("userId", foundUser.getId());

			for (Role role : foundUser.getRoles()) {
				if (role.getName().equalsIgnoreCase("Admin")) {
					return "admin";// admin.html
				}
			}
			return "user";// user.html

		} catch (InvalidCredentialsException e) {
			model.addAttribute("error", e.getMessage());
			return "login";// login.html
		}

	}

}
