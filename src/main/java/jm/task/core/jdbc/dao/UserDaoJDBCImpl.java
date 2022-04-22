package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();


    public UserDaoJDBCImpl() {
        util.getConnection();

    }

    public void createUsersTable() {
        try {
            util.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(20), " +
                    "lastName VARCHAR(20), " +
                    "age TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            util.getStatement().execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);


        String sql = "INSERT INTO users (name, lastName, age) Values (?, ?, ?)";
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        /**       Try with printf (working)          */
//        try (ResultSet resultSet = util.getStatement().executeQuery("SELECT * FROM users")) {
//            while (resultSet.next()) {
//                long id  = resultSet.getLong(1);
//                String name = resultSet.getString(2);
//                String lastName = resultSet.getString(3);
//                byte age = resultSet.getByte(4);
//                System.out.printf("%d. %s %s - %d \n", id, name, lastName, age);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;


        /**       Try with List<>       */
        List <User> usersList = new ArrayList<>();
        try (ResultSet resultSet = util.getConnection().createStatement().executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Connection failed...");
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try {
            util.getStatement().executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
