package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;


public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoHibernateImpl();
//        UserDao userDao1 = new UserDaoJDBCImpl();

//        userDao.createUsersTable();

//        userDao.saveUser("Dmitriy", "Vtagin", (byte) 20);
//        userDao.saveUser("Pavel", "Luchnik", (byte) 25);
//        userDao.saveUser("Ivan", "Ivanov", (byte) 31);
//        userDao.saveUser("Petr", "Sidorov", (byte) 38);
//
//        userDao.removeUserById(2);
//        userDao1.getAllUsers();
//        userDao.getAllUsers();
//        userDao.cleanUsersTable();
//        userDao.dropUsersTable();
    }
}
