
package gui;

import dao.ProjectDAO;
import dao.TaskDAO;
import dao.UserDAO;
import model.Project;
import model.Task;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminReportsFrame extends JFrame {

    public AdminReportsFrame() {

        setTitle("Admin - System Reports");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Arial", Font.PLAIN, 15));

        UserDAO userDAO = new UserDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        TaskDAO taskDAO = new TaskDAO();

        List<User> users = userDAO.getAllUsers();
        List<Project> projects = projectDAO.getAllProjects();
        List<Task> tasks = taskDAO.getAllTasks();

        int completedTasks = 0;
        int pendingTasks = 0;

        for (Task task : tasks) {

            if ("DONE".equalsIgnoreCase(task.getStatus())
                    || "COMPLETED".equalsIgnoreCase(task.getStatus())) {

                completedTasks++;

            } else {

                pendingTasks++;
            }
        }

        reportArea.append("===== SYSTEM REPORT =====\n\n");

        reportArea.append(
                "Total Users: " + users.size() + "\n"
        );

        reportArea.append(
                "Total Projects: " + projects.size() + "\n"
        );

        reportArea.append(
                "Total Tasks: " + tasks.size() + "\n"
        );

        reportArea.append(
                "Completed Tasks: " + completedTasks + "\n"
        );

        reportArea.append(
                "Pending Tasks: " + pendingTasks + "\n"
        );

        reportArea.append(
                "\n================================\n\n"
        );

        reportArea.append("USERS\n\n");

        for (User user : users) {

            reportArea.append(
                    "User ID: " + user.getUserId() + "\n" +
                    "Name: " + user.getName() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Role: " + user.getRole() + "\n" +
                    "--------------------------------\n"
            );
        }

        reportArea.append("\nPROJECTS\n\n");

        for (Project project : projects) {

            reportArea.append(
                    "Project ID: " + project.getProjectId() + "\n" +
                    "Title: " + project.getTitle() + "\n" +
                    "Status: " + project.getStatus() + "\n" +
                    "--------------------------------\n"
            );
        }

        add(
                new JScrollPane(reportArea),
                BorderLayout.CENTER
        );
    }
}