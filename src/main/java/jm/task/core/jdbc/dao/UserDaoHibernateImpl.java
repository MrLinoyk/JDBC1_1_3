package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Session session = Util.getSessionFactory().openSession();

    String sql;

    public UserDaoHibernateImpl() {
        Transaction transaction = session.beginTransaction();
    }

    @Override
    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
//        transaction.commit();

    }

    @Override
    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        session.save(user);
        System.out.println("User " + user.getName() + " is added");
    }

    @Override
    public void removeUserById(long id) {
        sql = "DELETE FROM users WHERE id = " + id;
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();


    }

    @Override
    public List<User> getAllUsers() {
        System.out.println(session.createQuery("SELECT a from User a", User.class).getResultList());
        return session.createQuery("SELECT a from User a", User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        sql = "DELETE FROM User";
        session.createQuery(sql).executeUpdate();

    }
}
