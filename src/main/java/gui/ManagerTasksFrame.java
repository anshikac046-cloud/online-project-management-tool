package gui;

import dao.TaskDAO;
import model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagerTasksFrame extends JFrame {

    private JTable taskTable;

    public ManagerTasksFrame() {

        setTitle("Manage Tasks");
        setSize(850, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel(
                "Task Management",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        String[] columns = {
                "Task ID",
                "Project ID",
                "Assigned To",
                "Task Name",
                "Description",
                "Deadline",
                "Priority",
                "Status"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        taskTable = new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(taskTable);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadTasks();
    }

    private void loadTasks() {

        TaskDAO taskDAO = new TaskDAO();

        List<Task> tasks =
                taskDAO.getAllTasks();

        DefaultTableModel model =
                (DefaultTableModel) taskTable.getModel();

        for (Task task : tasks) {

            model.addRow(new Object[]{
                    task.getTaskId(),
                    task.getProjectId(),
                    task.getAssignedTo(),
                    task.getTaskName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getPriority(),
                    task.getStatus()
            });
        }
    }
}