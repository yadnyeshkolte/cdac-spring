package com.cdac;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class UserDAO {

    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User validateUser(String username, String password) {

        Session session = factory.openSession();

        Query<User> q = session.createQuery(
                "from User where username=:u and password=:p",
                User.class);

        q.setParameter("u", username);
        q.setParameter("p", password);

        User user = q.uniqueResult();

        session.close();
        return user;
    }
}
