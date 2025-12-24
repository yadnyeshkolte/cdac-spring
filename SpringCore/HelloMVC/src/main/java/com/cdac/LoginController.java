
package com.cdac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	
	@GetMapping("/login")
	public String showForm() {
		return "login";
	}
	
	@PostMapping("/login")
	public String Login(@RequestParam("username") String username,@RequestParam("password") String password,Model model) {
		
		if(username.equals("ayush")&& password.equals("Ayush1234")) {
			model.addAttribute("user", username);
			return "welcome";
		}
		else {
			model.addAttribute("error","Invalid Username or Password!");
			return "error";
		} 

	}
}
