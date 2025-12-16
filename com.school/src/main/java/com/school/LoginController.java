package com.school;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String showform(){
		return "login";
		
	}
	@PostMapping("/login")
	public String login(@RequestParam("username") String name, @RequestParam("password") String pass, Model model) {
		if(name.equals("admin") && pass.equals("admin123")) {
			model.addAttribute("user", name);
			return "welcome";
		}
		else {
			model.addAttribute("error","Invalid username and password");
			return "error";
		}
	
		
	}
}
