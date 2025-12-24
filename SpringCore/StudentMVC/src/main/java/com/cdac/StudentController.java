package com.cdac;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.cdac.StudentDAO;
import com.cdac.Student;

@Controller
public class StudentController {

    private StudentDAO studentDAO;

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerStudent(
            String name,
            String email,
            String mobile,
            Model model) {

        Student s = new Student(name, email, mobile);
        studentDAO.saveStudent(s);

        model.addAttribute("msg", "Student Registered Successfully");
        return "success";
    }
}

