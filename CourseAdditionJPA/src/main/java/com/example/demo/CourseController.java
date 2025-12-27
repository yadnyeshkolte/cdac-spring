package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {
	
	private final CourseRepository crepo;
	
	public CourseController(CourseRepository crepo) {
		this.crepo = crepo;
	}
	
	@GetMapping("/add")
	public String addCourse(Model model) {
		model.addAttribute("course",new Course());
		return "add";
	}
	
	@PostMapping("/courses")
	public String postAdd(@ModelAttribute Course course, Model model) {
		crepo.save(course);
		model.addAttribute("courses", crepo.findAll());
		return "courses";
	}
	
	@GetMapping("/courses")
	public String postAdd(Model model) {
		model.addAttribute("courses", crepo.findAll());
		return "courses";
	}
	
	@GetMapping("/delete/{cid}")
	public String deleteCourse(@PathVariable int cid, Model model) {
		crepo.deleteById(cid);
		model.addAttribute("courses", crepo.findAll());
		return "redirect:/courses";
	}
	
	
	@GetMapping("/update/{cid}")
	public String updatePage(@PathVariable int cid, Model model) {
		Course course = crepo.findById(cid).orElseThrow();
		model.addAttribute("course", course);
		return "update";
	}
	
	@PostMapping("/update/{cid}")
	public String postUpdate(@PathVariable int cid, @ModelAttribute Course course) {
		course.setCid(cid);
		crepo.save(course);
		return "redirect:/courses";
	}
	
	
	
	
	
}
