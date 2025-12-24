package com.cdac;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping("/views")
	public String Hello(Model model) {  
		
		// ref Model form spring framework  working same like Httpservlet 
		System.out.println("CONTROLLER HIT");
		String message = "Hello Ayush!";
		
		model.addAttribute("msg", message);
		
		return "view";     // returning the object and redirect to view.jsp  to display the data.
	}
}


