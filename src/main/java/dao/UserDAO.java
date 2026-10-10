package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Login - find user by email
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                );
            }

        } catch (SQLException e) {

            System.out.println("Error finding user!");
            e.printStackTrace();
        }

        return null;
    }


    // Get all users - Admin
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql =
                "SELECT user_id, name, email, role FROM users";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        "",
                        resultSet.getString("role")
                );

                users.add(user);
            }

        } catch (SQLException e) {

            System.out.println("Error fetching users!");
            e.printStackTrace();
        }

        return users;
    }


    // Add a new user - Admin
    public boolean addUser(
            String name,
            String email,
            String password,
            String role) {

        String sql =
                "INSERT INTO users " +
                "(name, email, password, role) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, role);

            int rows =
                    statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Error adding user!");
            e.printStackTrace();

            return false;
        }
    }
    public User getUserById(int userId) {

    String sql = "SELECT * FROM users WHERE user_id = ?";

    try (Connection connection =
                 DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        ResultSet resultSet =
                statement.executeQuery();

        if (resultSet.next()) {

            return new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("role")
            );
        }

    } catch (SQLException e) {

        System.out.println("Error finding user by ID!");
        e.printStackTrace();
    }

    return null;
}

}