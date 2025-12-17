package com.StudentWVCJDBC;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {
	
	@Autowired StudentDAO dao;
	@GetMapping("/register")
	public String showRegister(){
		return "register";
	}
	
	@PostMapping("/register")
	public String doRegister( @ModelAttribute Student s) throws SQLException {
		dao.createStudent(s);
		return "redirect:/view";
	}
	
	@GetMapping("/view")
	public String viewStudent(Model model) throws SQLException {
		ArrayList<Student> list = dao.viewStudent();
		model.addAttribute("Slist",list);
		return "view"; 
	}

}