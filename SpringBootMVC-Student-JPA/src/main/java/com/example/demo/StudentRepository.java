package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>
{
}
