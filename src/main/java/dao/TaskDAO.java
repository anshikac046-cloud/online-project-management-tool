package dao;

import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Task task = new Task(
                        resultSet.getInt("task_id"),
                        resultSet.getInt("project_id"),
                        resultSet.getInt("assigned_to"),
                        resultSet.getString("task_name"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getString("priority"),
                        resultSet.getString("status")
                );

                tasks.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching tasks!");
            e.printStackTrace();
        }

        return tasks;
    }
    public List<Task> getTasksByUser(int userId) {

    List<Task> tasks = new ArrayList<>();

    String sql = "SELECT * FROM tasks WHERE assigned_to = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, userId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            Task task = new Task(
                    resultSet.getInt("task_id"),
                    resultSet.getInt("project_id"),
                    resultSet.getInt("assigned_to"),
                    resultSet.getString("task_name"),
                    resultSet.getString("description"),
                    resultSet.getDate("deadline").toLocalDate(),
                    resultSet.getString("priority"),
                    resultSet.getString("status")
            );

            tasks.add(task);
        }

    } catch (SQLException e) {
        System.out.println("Error fetching user's tasks!");
        e.printStackTrace();
    }

    return tasks;
}
}