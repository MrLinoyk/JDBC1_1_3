package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class Util {
    public static Util instance;
    private Util () {

    }
    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }
    private static final String PASSWORD = "Demasik_2006";
    private static final String USER = "Admin";
    private static final String URL = "jdbc:mysql://localhost:3306/jdbcpp";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection connection;
    private static SessionFactory sessionFactory;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        try {
            Configuration config = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, DRIVER);
            properties.put(Environment.URL, URL);
            properties.put(Environment.USER, USER);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            config.setProperties(properties);
            config.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();

            sessionFactory = config.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.out.println("Problem creating session factory");
            e.printStackTrace();
        }
        return sessionFactory;
    }
}

