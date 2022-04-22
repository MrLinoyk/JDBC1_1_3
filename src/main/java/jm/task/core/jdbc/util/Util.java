package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Util {
    private static final String PASSWORD = "Demasik_2006";
    private static final String USER = "Admin";
    private static final String URL = "jdbc:mysql://localhost:3306/ppjdbc";
    private Connection connection;
    private Statement statement;

//   public static void getConnection () {
//       try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
//           Statement statement = connection.createStatement();
//           System.out.println("Successfully connection");
//       } catch (SQLException e) {
//           System.out.println("Connection failed");
//       }
//
//   }

//   public static Connection getConn () throws SQLException {
//      return DriverManager.getConnection(URL, USER, PASSWORD);
//   }


    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Statement getStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
