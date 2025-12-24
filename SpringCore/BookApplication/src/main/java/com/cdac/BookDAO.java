package com.cdac;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class BookDAO {
	 
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdb","root","Ayush@2000");
	}
	
	public static int addBook(Books b) throws Exception {
		
		Connection con = BookDAO.getConnection();
		String query = "INSERT INTO books (title,author,price) VALUES(?,?,?);";
	
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setString(1,b.getTitle());
		ps.setString(2,b.getAuthor());
		ps.setDouble(3,b.getPrice());
		
		return ps.executeUpdate();
		 
	}

	public static List<Books> viewBook() throws Exception {
		
		List<Books> list = new ArrayList<>();
		Connection con = BookDAO.getConnection();
		String query = "SELECT * from books";
	
		PreparedStatement ps=con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
	
		while(rs.next()) {
			Books book = new Books();
			book.setId(rs.getInt(1));
			book.setTitle(rs.getString(2));
			book.setAuthor(rs.getString(3));
			book.setPrice(rs.getDouble(4));
			list.add(book);
		}
		
		return list;
	}
	
	public static int updateBook(Books b) throws Exception {
		
		Connection con = BookDAO.getConnection();
		
		String query = "UPDATE books SET title=?, author=?, price=? WHERE id=?;";
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setString(1,b.getTitle());
		ps.setString(2,b.getAuthor());
		ps.setDouble(3,b.getPrice());
		ps.setInt(4,b.getId());
		
		return ps.executeUpdate();
		 
	}
	
	public static int deleteBook(int id) throws Exception {
		
		Connection con = BookDAO.getConnection();
		
		String query = "DELETE from books WHERE id=?;";
		PreparedStatement ps = con.prepareStatement(query);
	
		ps.setInt(1,id);
		
        return ps.executeUpdate();
		 
	}
	
	public static Books getBook(int id) throws Exception {

	    String query = "SELECT * FROM books WHERE id = ?";

	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setInt(1, id);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Books b = new Books();
	                b.setId(rs.getInt("id"));
	                b.setTitle(rs.getString("title"));
	                b.setAuthor(rs.getString("author"));
	                b.setPrice(rs.getDouble("price"));
	                return b;
	            }
	        }
	    }

	    return null;  
	}

	
	

}