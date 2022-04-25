package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
//    Session session = Util.getSessionFactory().openSession();
    private Session session = null;
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        System.out.println("User " + user.getName() + " is added");
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "DELETE FROM users WHERE id = " + id;
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();


    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "SELECT a from User a";
        List <User> userList = session.createQuery(sql, User.class).getResultList();
        session.getTransaction().commit();
        session.close();
        System.out.println(userList);
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
