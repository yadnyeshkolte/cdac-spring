package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {
	
	private final StudentDAO dao;
	
	public StudentController(StudentDAO dao) {
		this.dao = dao;
	}
	
	@GetMapping("/")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerStudent(@ModelAttribute Student student, Model model) {
		dao.save(student);
		
		return "login";
	}
}
