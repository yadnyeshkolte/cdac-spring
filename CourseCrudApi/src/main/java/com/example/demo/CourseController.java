package com.example.demo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
	List<Course> courseList = new ArrayList<>();
	
	
	@PostMapping
	public String addCourse(@RequestBody Course course) {
		courseList.add(course);
		return "Course Added Successfully";
	}
	
}
