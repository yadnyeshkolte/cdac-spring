package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String validateLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if (username.equals("admin") && password.equals("admin123")) {
            model.addAttribute("user", username);
            return "success";
        } else {
            model.addAttribute("error", "Invalid Username or Password");
            return "login";
        }
    }
}

