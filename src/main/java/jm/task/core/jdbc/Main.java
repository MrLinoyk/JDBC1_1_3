package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;


public class Main {
    public static void main(String[] args) {

        UserDao userDao1 = new UserDaoJDBCImpl();

        userDao1.createUsersTable();
        userDao1.saveUser("Dmitriy", "Vtagin", (byte) 20);
        userDao1.saveUser("Pavel", "Luchnik", (byte) 25);
        userDao1.saveUser("Ivan", "Ivanov", (byte) 31);
        userDao1.saveUser("Petr", "Sidorov", (byte) 38);
        userDao1.removeUserById(2);
        for (User user : userDao1.getAllUsers()) {
            System.out.println(user);
        }
        userDao1.cleanUsersTable();
        userDao1.dropUsersTable();

    }
}
