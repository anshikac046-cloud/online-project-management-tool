package gui;

import dao.NotificationDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.List;

public class NotificationsFrame extends JFrame {

    // Color theme
    private final Color NAVY_BLUE = new Color(0, 31, 84);
    private final Color HOVER_BLUE = new Color(30, 90, 160);
    private final Color LIGHT_BLUE = new Color(225, 237, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_GRAY = new Color(75, 95, 125);

    private JTable notificationTable;
    private DefaultTableModel tableModel;
    private final int userId;

    public NotificationsFrame(int userId) {

        this.userId = userId;

        setTitle("My Notifications");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(750, 450));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(LIGHT_BLUE);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(LIGHT_BLUE);

        JLabel titleLabel = new JLabel(
                "My Notifications",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(NAVY_BLUE);

        JLabel subtitleLabel = new JLabel(
                "Stay updated with your latest notifications",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Table columns
        String[] columns = {
                "Notification ID",
                "Message",
                "Date",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        notificationTable = new JTable(tableModel);

        // Table styling
        notificationTable.setFont(new Font("Arial", Font.PLAIN, 13));
        notificationTable.setRowHeight(38);
        notificationTable.setBackground(WHITE);
        notificationTable.setForeground(NAVY_BLUE);
        notificationTable.setGridColor(new Color(210, 220, 235));
        notificationTable.setSelectionBackground(new Color(190, 215, 245));
        notificationTable.setSelectionForeground(NAVY_BLUE);
        notificationTable.setShowGrid(true);
        notificationTable.setFillsViewportHeight(true);

        // Table header styling
        JTableHeader tableHeader = notificationTable.getTableHeader();

        tableHeader.setBackground(NAVY_BLUE);
        tableHeader.setForeground(WHITE);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));
        tableHeader.setPreferredSize(new Dimension(100, 42));
        tableHeader.setReorderingAllowed(false);

        // Column widths
        notificationTable.getColumnModel()
                .getColumn(0).setPreferredWidth(130);

        notificationTable.getColumnModel()
                .getColumn(1).setPreferredWidth(420);

        notificationTable.getColumnModel()
                .getColumn(2).setPreferredWidth(150);

        notificationTable.getColumnModel()
                .getColumn(3).setPreferredWidth(120);

        // Center-align Notification ID and Date
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        notificationTable.getColumnModel()
                .getColumn(0).setCellRenderer(centerRenderer);

        notificationTable.getColumnModel()
                .getColumn(2).setCellRenderer(centerRenderer);

        // Status column styling
        notificationTable.getColumnModel()
                .getColumn(3)
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
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        setHorizontalAlignment(SwingConstants.CENTER);
                        setFont(new Font("Arial", Font.BOLD, 12));

                        if (!isSelected && value != null) {

                            String status = value.toString()
                                    .trim()
                                    .toUpperCase()
                                    .replace(' ', '_');

                            if (status.equals("READ")
                                    || status.equals("COMPLETED")) {

                                setForeground(new Color(0, 130, 70));

                            } else if (status.equals("UNREAD")
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
        JScrollPane scrollPane = new JScrollPane(notificationTable);

        scrollPane.setBorder(
                new LineBorder(new Color(170, 190, 215), 1)
        );

        scrollPane.getViewport().setBackground(WHITE);

        // Buttons
        JButton refreshButton = createButton("Refresh");
        JButton closeButton = createButton("Close");

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 5)
        );

        buttonPanel.setBackground(LIGHT_BLUE);
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
        refreshButton.addActionListener(e -> refreshNotifications());

        closeButton.addActionListener(e -> dispose());

        // Load notifications
        loadNotifications(userId);
    }

    // Create styled buttons
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

    // Load notifications from database
    private void loadNotifications(int userId) {

        tableModel.setRowCount(0);

        NotificationDAO notificationDAO = new NotificationDAO();

        List<String[]> notifications =
                notificationDAO.getNotificationsByUser(userId);

        if (notifications == null) {
            return;
        }

        for (String[] notification : notifications) {
            tableModel.addRow(notification);
        }
    }

    // Refresh notifications
    private void refreshNotifications() {

        try {

            loadNotifications(userId);

            JOptionPane.showMessageDialog(
                    this,
                    "Notifications refreshed successfully!",
                    "Refresh",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to refresh notifications: "
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}