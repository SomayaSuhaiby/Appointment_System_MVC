package com.example.appointmentsystem.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;
import com.example.appointmentsystem.model.Role;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repositories.RoleRepository;
import com.example.appointmentsystem.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register"; // register.html
	}

	// Registeration
	@PostMapping("/register")
	public String register(@ModelAttribute User user, @RequestParam("roleName") String roleName, Model model) {

		if (userRepository.findByEmail(user.getEmail()) != null) {
			model.addAttribute("error", "User already exists");
			return "register";// register.html
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role role = roleRepository.findByName(roleName);
		if (role == null) {
			model.addAttribute("error", "Invalid role selected");
			return "register";// register.html
		}
		// bind role with user
		user.setRoles(Set.of(role));

		userRepository.save(user);

		return "redirect:/users/login";

	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; // login.html in templates
	}

	// login
	@PostMapping("/login")
	public String login(@ModelAttribute User user, Model model,HttpSession session) {

		User foundUser = userRepository.findByEmail(user.getEmail());
		// Set user ID in session
		session.setAttribute("userId", foundUser.getId());

		if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
			for (Role role : foundUser.getRoles()) {
				if (role.getName().equalsIgnoreCase("Admin")) {
					return "admin";// admin.html
				}
			}
			return "user";// user.html

		}

		model.addAttribute("error", "Invalid email or password");
		return "login";// login.html

	}

}
