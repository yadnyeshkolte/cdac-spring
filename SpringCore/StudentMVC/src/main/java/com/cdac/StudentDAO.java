package com.cdac;

import java.sql.DriverManager;

import org.springframework.jdbc.core.JdbcTemplate;
import com.cdac.Student;

public class StudentDAO {
	
	

    private JdbcTemplate jdbcTemplate;

    public StudentDAO() {
        DriverManager ds = new DriverManager();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/studentdb");
        ds.setUsername("root");
        ds.setPassword("Ayush@2000");

        jdbcTemplate = new JdbcTemplate(ds);
    }

    public int saveStudent(Student s) {
        String sql = "INSERT INTO student(name,email,mobile) VALUES(?,?,?)";
        return jdbcTemplate.update(
                sql,
                s.getName(),
                s.getEmail(),
                s.getMobile()
        );
    }
}
