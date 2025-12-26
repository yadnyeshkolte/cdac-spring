package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class RegistrationController {
	
	private List<Student> slist = new ArrayList<>();
	
	@PostMapping
	public String registerStudent(@RequestBody Student stud) {
		slist.add(stud);
		return "Student Added Successfully";
	}
	
	@GetMapping
	public List<Student> getStudentList(){
		return slist;
	}
	
	@GetMapping("/{id}")
	public Student studentById(@PathVariable int id) {
		for(Student s: slist) {
			if(s.studentId==id) {
				return s;
			}
		}
		return null;
	}
	
	@PutMapping("/{id}")
	public String updateStudent(@PathVariable int id, @RequestBody Student stud) {
		
		for(Student s: slist) {
			if(s.studentId==id) {
				s.setName(stud.name);
				s.setCourse(stud.course);
				s.setEmail(stud.email);
				s.setMarks(stud.marks);
				
				return "Student updated Successfully";
			}
		}
		
		return "Student not found";
	}
	
	@DeleteMapping("/{id}")
	public String deleteStudent(@PathVariable int id) {
		
		for(Student s: slist) {
			if(s.getStudentId()==id) {
				slist.remove(s);
				return "Student Deleted Successfully";
			}
		}
		
		return "Not delete Succesfully";
	}
}
