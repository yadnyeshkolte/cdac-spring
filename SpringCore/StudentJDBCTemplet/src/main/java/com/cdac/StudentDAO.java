package com.cdac;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cdac.Student;

	public class StudentDAO {

	    private JdbcTemplate jdbcTemplate;

	    
	    public StudentDAO() {
	    
	    }
	    
	    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		// Register student
	    
	    public void registerStudent(Student student) {
	    	String sql = "INSERT INTO student(name, email, course) VALUES (?, ?, ?)";
	    	jdbcTemplate.update(sql,student.getName(),student.getEmail(),student.getCourse());
	    }

	    // View all students
	    public List<Student> viewAllStudents() {
	    	String sql = "SELECT * FROM student";
	    	return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Student.class));    
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
//	    // Defining the row mapper as a reusale object 
//	    private RowMapper<Student> studentRowMapper = (rs,rowNo) -> {
//	    	Student s = new Student();
//	    	s.setId(rs.getInt("id"));
//            s.setName(rs.getString("name"));
//            s.setEmail(rs.getString("email"));
//            s.setCourse(rs.getString("course"));
//	    	return s;
//	    };
	       
	}



