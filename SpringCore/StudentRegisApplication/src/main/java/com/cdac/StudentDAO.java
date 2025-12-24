package com.cdac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentDAO {
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName("mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Ayush@2000");
	}
	
	
}
