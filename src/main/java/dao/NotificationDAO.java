package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDAO {

    public void addNotification(int userId, String message, String status) {

        String sql = "INSERT INTO notifications (user_id, message, status) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, message);
            statement.setString(3, status);

            statement.executeUpdate();

            System.out.println("Notification added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding notification!");
            e.printStackTrace();
        }
    }

    public void showNotifications(int userId) {

        String sql = "SELECT * FROM notifications WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                System.out.println("Notification ID: "
                        + resultSet.getInt("notification_id"));

                System.out.println("Message: "
                        + resultSet.getString("message"));

                System.out.println("Status: "
                        + resultSet.getString("status"));

                System.out.println("------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching notifications!");
            e.printStackTrace();
        }
    }

public java.util.List<String[]> getNotificationsByUser(int userId) {

    java.util.List<String[]> notifications =
            new java.util.ArrayList<>();

    String sql =
            "SELECT notification_id, message, date, status " +
            "FROM notifications WHERE user_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            String[] notification = {
                    String.valueOf(
                            resultSet.getInt("notification_id")
                    ),
                    resultSet.getString("message"),
                    String.valueOf(
                            resultSet.getTimestamp("date")
                    ),
                    resultSet.getString("status")
            };

            notifications.add(notification);
        }

    } catch (SQLException e) {

        System.out.println(
                "Error fetching notifications!"
        );

        e.printStackTrace();
    }

    return notifications;
}
}