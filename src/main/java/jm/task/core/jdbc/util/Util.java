package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String url = "jdbc:mysql://localhost:3306/first";
    private static final String user = "root";
    private static final String pswd = "root";
    private static final Properties properties;

    static {
        properties = new Properties();
        properties.setProperty("hibernate.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", url);
        properties.setProperty("hibernate.connection.username", user);
        properties.setProperty("hibernate.connection.password", pswd);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.current_session_context_class", "thread");
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            return new Configuration().addProperties(properties).addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pswd);
    }
}
