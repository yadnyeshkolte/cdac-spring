package com.cdac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class StudentDAO 
{

	
	
	public void writeStudent(Student s) throws Exception 
	{
	
	       
		Configuration cfg = new Configuration();
     cfg.configure("hibernate.cfg.xml");
     SessionFactory factory = cfg.buildSessionFactory();
		       
     Session session = factory.openSession();               // Step 2

     Transaction tx = session.beginTransaction();

     session.persist(s);

     tx.commit();
		    
		
		
	}

	public List<Student> getAllStudents()
	
	{
		
		
		Configuration cfg = new Configuration();
	     cfg.configure("hibernate.cfg.xml");
	     SessionFactory factory = cfg.buildSessionFactory();
			       
	     Session session = factory.openSession();               // Step 2

	     Transaction tx = session.beginTransaction();

	       String q = "from students";
	       List<Student> list = session.createQuery("from Student", Student.class).list();

	        session.close();
	        return list;

	     
	}





	
         
         
		
	}

	
	
	
	
	
	

