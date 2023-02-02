package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/first";
    private static final String user = "root";
    private static final String pswd = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pswd);
    }
}
