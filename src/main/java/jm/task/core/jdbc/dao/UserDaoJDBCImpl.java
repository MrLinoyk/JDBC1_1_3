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
    private Util util = Util.getInstance();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        util.getConnection();

        try {
            util.getConnection().createStatement()
            .executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(20), " +
                    "lastName VARCHAR(20), " +
                    "age TINYINT)");
            util.getConnection().close();
        } catch (SQLException sql) {
            System.out.println("Connection failed...");
        }

    }

    public void dropUsersTable() {
        try {
            util.getConnection().createStatement().execute("DROP TABLE IF EXISTS users");
            util.getConnection().close();
        } catch (SQLException e){
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
            System.out.println("User " + user.getName() + " is added");
            util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List <User> usersList = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id, name, lastName, age from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public void cleanUsersTable() {
        try {
            util.getConnection().createStatement().executeUpdate("TRUNCATE TABLE users");
            util.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
