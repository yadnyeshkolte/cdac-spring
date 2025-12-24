package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {
	private final JdbcTemplate jdbcTemplate;
	
	public StudentDAO (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void save(Student student) {
		String sql = "insert into students values(?,?,?,?,?)";
		jdbcTemplate.update(sql, student.getSid(), student.getName(), student.getCourse(), student.getEmail(), student.getPassword());
	}

	
}
