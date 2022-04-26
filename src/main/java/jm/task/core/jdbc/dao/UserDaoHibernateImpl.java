package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        // или же лучше использовать getCurrentSession, чтобы не закрывать сессию?!
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            try {
                session.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            try {
                session.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            System.out.println("User " + name + " is added");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            try {
                session.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            try {
                session.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List <User> userList = new ArrayList<>();
        try {
            session.beginTransaction();
            userList = session.createQuery("SELECT a from User a", User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            try {
                session.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            try {
                session.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
