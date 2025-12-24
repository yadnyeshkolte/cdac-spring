package com.cdac;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cdac.StudentDAO;
import com.cdac.Student;

@Controller
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;

    @GetMapping("/register")
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
