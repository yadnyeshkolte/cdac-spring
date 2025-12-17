package com.StudentWVCJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentDAO {
	
	@Autowired DataSource dataSource;
	public void createStudent(Student s) throws SQLException {
		Connection con = dataSource.getConnection();
		
		String q = "insert into Student(id, name, email, course) values(?, ?, ?, ?)";
		PreparedStatement pmt = con.prepareStatement(q);
		
		pmt.setInt(1, s.getId());
		pmt.setString(2, s.getName());
		pmt.setString(3, s.getEmail());
		pmt.setString(4, s.getCourse());
		
		
		pmt.executeUpdate();
	}
	
	public ArrayList<Student> viewStudent() throws SQLException{
	
		ArrayList<Student> list = new ArrayList<Student>();
		
		Connection con = dataSource.getConnection();
		
		String q = "select * from Student";
		PreparedStatement pmt = con.prepareStatement(q);
		
		ResultSet rs = pmt.executeQuery();
		
		while(rs.next()) {
			Student s = new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setEmail(rs.getString("email"));
			s.setCourse(rs.getString("course"));
			
			list.add(s);
		}
		
		return list;
	}
	
}