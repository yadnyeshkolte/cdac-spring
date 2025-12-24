package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.*;
@Repository
@Transactional
public class StudentDAO {
	@Autowired
    private DataSource dataSource;

	// Register student
    public void registerStudent(Student student) {
    	
    	Connection con = null;
	    PreparedStatement ps = null;
	    
	    try {
	        con = dataSource.getConnection();

	        String sql = "INSERT INTO student(name, email, course) VALUES (?, ?, ?)";
	        ps = con.prepareStatement(sql);

	        ps.setString(1, student.getName());
	        ps.setString(2, student.getEmail());
	        ps.setString(3, student.getCourse());

	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (Exception e) {}
	    }
	 }

	    // View all students
	 public List<Student> viewAllStudents() {

	        List<Student> list = new ArrayList<>();

	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            con = dataSource.getConnection();
	            ps = con.prepareStatement("SELECT * FROM student");
	            rs = ps.executeQuery();

	            while (rs.next()) {
	                Student s = new Student();
	                s.setId(rs.getInt("id"));
	                s.setName(rs.getString("name"));
	                s.setEmail(rs.getString("email"));
	                s.setCourse(rs.getString("course"));
	                list.add(s);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (con != null) con.close();
	            } catch (Exception e) {}
	        }

	        return list;
	    }
	}


