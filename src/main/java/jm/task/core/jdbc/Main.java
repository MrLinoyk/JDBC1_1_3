package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Dmitriy", "Vtagin", (byte) 20);
        userDao.saveUser("Pavel", "Luchnik", (byte) 25);
        userDao.saveUser("Ivan", "Ivanov", (byte) 31);
        userDao.saveUser("Petr", "Sidorov", (byte) 38);

        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
