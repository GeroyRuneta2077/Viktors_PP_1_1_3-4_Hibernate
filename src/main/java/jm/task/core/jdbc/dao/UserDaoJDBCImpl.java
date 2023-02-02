package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users ("+
                "id BIGINT PRIMARY KEY auto_increment, " +
                "Name TINYTEXT NOT NULL, " +
                "LastName TINYTEXT NOT NULL, " +
                "Age TINYINT NOT NULL)";

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println("Table was not created, reason: " + e);
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println("Table was not dropped, reason: " + e);
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (Name, LastName, Age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            System.out.println("User was not saved, reason: " + e);
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id=?";

        try (Connection connection = Util.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("User was not deleted, reason: " + e);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User current = new User(rs.getString(2),
                        rs.getString(3), rs.getByte(4));
                current.setId(rs.getLong(1));
                result.add(current);
            }
        } catch (SQLException e) {
            System.out.println("Can't get all users, reason: " + e);
            e.printStackTrace();
        }

        return result;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM Users";

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Table was not cleaned, reason: " + e);
            e.printStackTrace();
        }
    }
}
