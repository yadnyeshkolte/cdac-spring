package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {
    private final StudentRepository srepo;
    
    public StudentController(StudentRepository srepo) {
        this.srepo = srepo;
    }

    @GetMapping("/register")
    public String studentRegister(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/students")
    public String studentRegistration(@ModelAttribute Student student, Model model) {
        srepo.save(student);
        model.addAttribute("students", srepo.findAll());
        return "students";
    }

    @GetMapping("/students")  // Add this!
    public String viewStudents(Model model) {
        model.addAttribute("students", srepo.findAll());
        return "students";
    }
    
    @GetMapping("/delete/{sid}")
    public String deleteStudent(@PathVariable int sid) {
    	srepo.deleteById(sid);
    	return "redirect:/students";
    }
    
    @GetMapping("/update/{sid}")
    public String updateStudent(@PathVariable int sid, Model model) {
    	Student student = srepo.findById(sid).orElseThrow();
    	model.addAttribute("student", student);
    	return "update";
    }
    
    @PostMapping("/update/{sid}")
    public String updateStudentPost(@ModelAttribute Student student, @PathVariable int sid) {
    	student.setSid(sid);
    	srepo.save(student);
    	return "redirect:/students";
    }
}
