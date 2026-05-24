package org.myhealth.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_connections {

    public static class ConsoleColor {
        public static final String GREEN = "\u001B[32m";
        public static final String RED = "\u001B[31m";
        public static final String RESET = "\u001B[0m";
    }

    // JDBC driver, sqlite tells java the database type.
    public static final String DB_URL = "jdbc:sqlite:myhealth.db";

    // Creates connection between Java app and database
    public static Connection getConnnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Creates users table if it does not exist
    public static void createUserTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_Name TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    first_Name TEXT NOT NULL,
                    last_Name TEXT NOT NULL
                );
                """;

        try (Connection connect = getConnnection();
             Statement statement_1 = connect.createStatement()) {

            // Executes SQL query
            statement_1.execute(sql);
            System.out.println(ConsoleColor.GREEN + "Users table is ready" + ConsoleColor.RESET);

        } catch (SQLException e) {

            // Displays database error
            System.out.println(ConsoleColor.RED + "Users table got some errors " + e + ConsoleColor.RESET);
        }
    }
}