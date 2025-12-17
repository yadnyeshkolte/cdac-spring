package com.example;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.StudentDAO;
import com.example.Student;

@Controller
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;

    @GetMapping("/registerStudent")
    public String showForm() {
        return "register";
    }

    @PostMapping("/registerStudent")
    public String register(@ModelAttribute Student student) {
        studentDAO.registerStudent(student);
        return "redirect:/viewStudents";
    }

    @GetMapping("/viewStudents")
    public String viewStudents(Model model) {
        List<Student> students = studentDAO.viewAllStudents();
        model.addAttribute("students", students);
        return "viewStudents";
    }
}
