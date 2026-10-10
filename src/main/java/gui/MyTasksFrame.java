package gui;

import dao.TaskDAO;
import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MyTasksFrame extends JFrame {

    // Color theme
    private final Color NAVY_BLUE = new Color(0, 31, 84);
    private final Color HOVER_BLUE = new Color(30, 90, 160);
    private final Color LIGHT_BLUE = new Color(225, 237, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_GRAY = new Color(75, 95, 125);

    private JTable taskTable;
    private DefaultTableModel tableModel;
    private int userId;

    public MyTasksFrame(int userId) {

        this.userId = userId;

        setTitle("My Assigned Tasks");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(850, 500));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(LIGHT_BLUE);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(LIGHT_BLUE);

        JLabel titleLabel = new JLabel(
                "My Assigned Tasks",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(NAVY_BLUE);

        JLabel subtitleLabel = new JLabel(
                "View and manage your assigned work",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Table columns
        String[] columns = {
                "Task ID",
                "Task Name",
                "Description",
                "Deadline",
                "Priority",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);

        // Table appearance
        taskTable.setFont(new Font("Arial", Font.PLAIN, 13));
        taskTable.setRowHeight(35);
        taskTable.setBackground(WHITE);
        taskTable.setForeground(NAVY_BLUE);
        taskTable.setSelectionBackground(new Color(190, 215, 245));
        taskTable.setSelectionForeground(NAVY_BLUE);
        taskTable.setGridColor(new Color(210, 220, 235));
        taskTable.setShowGrid(true);
        taskTable.setFillsViewportHeight(true);
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Table header
        JTableHeader tableHeader = taskTable.getTableHeader();

        tableHeader.setBackground(NAVY_BLUE);
        tableHeader.setForeground(WHITE);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setPreferredSize(new Dimension(100, 42));
        tableHeader.setReorderingAllowed(false);

        // Column widths
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(280);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        taskTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        taskTable.getColumnModel().getColumn(5).setPreferredWidth(120);

        // Center-align selected columns
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        taskTable.getColumnModel().getColumn(0)
                .setCellRenderer(centerRenderer);

        taskTable.getColumnModel().getColumn(3)
                .setCellRenderer(centerRenderer);

        // Priority colors
        taskTable.getColumnModel().getColumn(4)
                .setCellRenderer(new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column
                                );

                        setHorizontalAlignment(SwingConstants.CENTER);
                        setFont(new Font("Arial", Font.BOLD, 13));

                        if (!isSelected && value != null) {

                            String priority = value.toString()
                                    .toUpperCase();

                            if (priority.equals("HIGH")) {
                                setForeground(new Color(200, 40, 40));
                            } else if (priority.equals("MEDIUM")) {
                                setForeground(new Color(220, 130, 0));
                            } else if (priority.equals("LOW")) {
                                setForeground(new Color(0, 130, 70));
                            } else {
                                setForeground(NAVY_BLUE);
                            }

                        } else if (isSelected) {
                            setForeground(NAVY_BLUE);
                        }

                        return component;
                    }
                });

        // Status colors
        taskTable.getColumnModel().getColumn(5)
                .setCellRenderer(new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column
                                );

                        setHorizontalAlignment(SwingConstants.CENTER);
                        setFont(new Font("Arial", Font.BOLD, 12));

                        if (!isSelected && value != null) {

                            String status = value.toString()
                                    .toUpperCase()
                                    .replace(' ', '_');

                            if (status.equals("COMPLETED")
                                    || status.equals("DONE")) {

                                setForeground(new Color(0, 130, 70));

                            } else if (status.equals("IN_PROGRESS")
                                    || status.equals("IN PROGRESS")) {

                                setForeground(new Color(30, 90, 180));

                            } else if (status.equals("TODO")
                                    || status.equals("PENDING")) {

                                setForeground(new Color(200, 100, 0));

                            } else {
                                setForeground(NAVY_BLUE);
                            }

                        } else if (isSelected) {
                            setForeground(NAVY_BLUE);
                        }

                        return component;
                    }
                });

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(taskTable);

        scrollPane.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new Color(170, 190, 215)
                )
        );

        scrollPane.getViewport().setBackground(WHITE);

        // Button panel
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 5)
        );

        buttonPanel.setBackground(LIGHT_BLUE);

        JButton refreshButton = createButton("Refresh");
        JButton closeButton = createButton("Close");

        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // Footer
        JLabel footerLabel = new JLabel(
                "TEAM MEMBER PORTAL",
                SwingConstants.CENTER
        );

        footerLabel.setFont(new Font("Arial", Font.BOLD, 11));
        footerLabel.setForeground(TEXT_GRAY);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setBackground(LIGHT_BLUE);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(footerLabel, BorderLayout.SOUTH);

        // Add components
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Button actions
        refreshButton.addActionListener(e -> refreshTasks());

        closeButton.addActionListener(e -> dispose());

        // Load tasks
        loadTasks(userId);
    }

    // Create stylish buttons
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(WHITE);
        button.setBackground(NAVY_BLUE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(
                new EmptyBorder(10, 25, 10, 25)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(HOVER_BLUE);
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(NAVY_BLUE);
                    }
                }
        );

        return button;
    }

    // Load tasks from database
    private void loadTasks(int userId) {

        tableModel.setRowCount(0);

        TaskDAO taskDAO = new TaskDAO();

        List<Task> tasks = taskDAO.getTasksByUser(userId);

        for (Task task : tasks) {

            tableModel.addRow(new Object[]{
                    task.getTaskId(),
                    task.getTaskName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getPriority(),
                    task.getStatus()
            });
        }
    }

    // Refresh task table
    private void refreshTasks() {

        try {
            loadTasks(userId);

            JOptionPane.showMessageDialog(
                    this,
                    "Tasks refreshed successfully!",
                    "Refresh",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to refresh tasks: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}