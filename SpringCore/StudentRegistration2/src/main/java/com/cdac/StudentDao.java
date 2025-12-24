package com.cdac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentDao {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "Ayush@2000"; 

   
    public int save(Student s) {
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO student (Std_ID,Std_Name,emailId) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getStd_ID());
            ps.setString(2, s.getStd_Name());
            ps.setString(3, s.getEmailId());

            result = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
