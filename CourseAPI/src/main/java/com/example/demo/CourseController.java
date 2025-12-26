package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private List<Course> courseList = new ArrayList<>();

    // ADD Course
    @PostMapping
    public String addCourse(@RequestBody Course course) {
        courseList.add(course);
        return "Course added successfully";
    }

    // VIEW All Courses
    @GetMapping
    public List<Course> viewCourses() {
        return courseList;
    }

    // SEARCH Course by ID
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        for (Course course : courseList) {
            if (course.getCourseId() == id) {
                return course;
            }
        }
        return null;
    }

    // UPDATE Course
    @PutMapping("/{id}")
    public String updateCourse(@PathVariable int id,
                               @RequestBody Course updatedCourse) {

        for (Course course : courseList) {
            if (course.getCourseId() == id) {
                course.setCourseName(updatedCourse.getCourseName());
                course.setDuration(updatedCourse.getDuration());
                return "Course updated successfully";
            }
        }
        return "Course not found";
    }

    // DELETE Course
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {

        Course courseToRemove = null;

        for (Course course : courseList) {
            if (course.getCourseId() == id) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove != null) {
            courseList.remove(courseToRemove);
            return "Course deleted successfully";
        }

        return "Course not found";
    }
}