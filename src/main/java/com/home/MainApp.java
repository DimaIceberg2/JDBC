package com.home;

import java.sql.*;

public class MainApp {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            connect();
            statement = connection.createStatement();

            //insert
            statement.executeUpdate("INSERT INTO books (title, user_id) VALUES ('Book#2', 1);");

            //select all
            //via Statement
            ResultSet rs = statement.executeQuery("SELECT * FROM books");
            while(rs.next()) {
                System.out.println(
                        rs.getInt(1) + " "
                        + rs.getString("title") + " "
                        + rs.getInt("user_id"));
            }

            //select where id = 1
            //via PrepareStatement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books where id = ?");
            preparedStatement.setInt(1, 1);

            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                System.out.println(
                        rs.getInt(1) + " "
                                + rs.getString("title") + " "
                                + rs.getInt("user_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }

    private static void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=digital&currentSchema=library");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void disconnect() {
        try {
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
