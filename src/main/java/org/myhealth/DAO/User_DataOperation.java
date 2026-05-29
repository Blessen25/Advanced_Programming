package org.myhealth.DAO;

import org.myhealth.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_DataOperation {

    public class ConsoleColor {
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
        public static final String RESET = "\u001B[0m";
    }

    // Checks if a username already exists, also username must be unique.
    public boolean usernameExists(String userName) {

        String sql = "SELECT user_Name FROM users WHERE user_Name = ?";

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement preparedStatment_1 = connect.prepareStatement(sql)) {

            // Prevents SQL injection by safely inserting username
            preparedStatment_1.setString(1, userName);
            ResultSet rs = preparedStatment_1.executeQuery();

            // If true, the username already exists
            return rs.next();

        } catch (SQLException e) {

            System.out.println(ConsoleColor.RED + "There has been some error in usernameExists " + e + ConsoleColor.RESET);
            return true;
        }
    }

    // Registers a new user into the database
    public boolean registerUser(User user) {

        String sql = """
                INSERT INTO users (user_Name, password, first_Name, last_Name)
                VALUES(?, ?, ?, ?)
                """;

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement preparedStatment_1 = connect.prepareStatement(sql)) {

            preparedStatment_1.setString(1, user.getUser_Name());
            preparedStatment_1.setString(2, user.getPassword());
            preparedStatment_1.setString(3, user.getFirst_Name());
            preparedStatment_1.setString(4, user.getLast_Name());

            preparedStatment_1.executeUpdate();
            return true;
        } catch (SQLException e) {

            System.out.println(ConsoleColor.RED + "There has been some error in the registerUser" + e + ConsoleColor.RESET);
            return false;
        }
    }

    // Gets full user details after successful login -> For Dashboard.
    public User getUserByLogin(String username, String password) {

        String sql = "SELECT * FROM users WHERE user_Name = ? AND password = ?";

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new User(

                        rs.getInt("id"),
                        rs.getString("user_Name"),
                        rs.getString("password"),
                        rs.getString("first_Name"),
                        rs.getString("last_Name")
                );
            }

        } catch (SQLException e) {

            System.out.println(ConsoleColor.RED + "Error getting user: " + e + ConsoleColor.RESET);
        }

        return null;
    }

    // To Update Profile
    public boolean updateProfile(
            int userId,
            String firstName,
            String lastName) {

        String sql = "UPDATE users SET first_Name = ?, last_Name = ? WHERE id = ?";
        try (Connection connect =
                     DB_connections.getConnnection();

             PreparedStatement preparedStatement1 =
                     connect.prepareStatement(sql)) {

            preparedStatement1.setString(1, firstName);
            preparedStatement1.setString(2, lastName);

            // Specifies which user to update
            preparedStatement1.setInt(3, userId);

            preparedStatement1.executeUpdate();

            return true;
        } catch (SQLException e) {

            System.out.println(ConsoleColor.RED + "Error in Updating Profile:" + e + ConsoleColor.RESET);
            return false;
        }
    }

    // Updates the user's password
    public boolean updatePassword(
            int userId,
            String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement PreparedStatement1 = connect.prepareStatement(sql)) {

            PreparedStatement1.setString(1, newPassword);
            PreparedStatement1.setInt(2, userId);

            int rowsUpdated = PreparedStatement1.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.out.println(" There has been some error while updating the Password:" + e);

            return false;
        }

    }
}
