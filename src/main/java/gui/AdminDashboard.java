package gui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private final Color navy = new Color(25, 35, 70);
    private final Color background = new Color(245, 247, 252);

    public AdminDashboard(String adminName) {

        setTitle("CodeCrew | Admin Dashboard");
        setSize(1000, 650);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main background
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(
                BorderFactory.createEmptyBorder(22, 30, 22, 30)
        );

        JLabel brand = new JLabel("✦  CodeCrew");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel welcome = new JLabel(
                "Welcome, " + adminName + "   |   ADMIN"
        );
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.PLAIN, 16));

        header.add(brand, BorderLayout.WEST);
        header.add(welcome, BorderLayout.EAST);

        // Main content
        JPanel content = new JPanel(new BorderLayout(15, 20));
        content.setBackground(background);
        content.setBorder(
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        // Heading
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setBackground(background);

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 29));
        title.setForeground(navy);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Manage users, projects and system activities."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(new Color(100, 110, 130));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        headingPanel.add(title);
        headingPanel.add(Box.createVerticalStrut(8));
        headingPanel.add(subtitle);

        // Dashboard buttons
        JButton usersButton = createButton("👥  User Management");
        JButton addUserButton = createButton("➕  Add User");
        JButton projectsButton = createButton("📁  Project Management");
        JButton tasksButton = createButton("📋  Task Management");
        JButton reportsButton = createButton("📊  Reports");
        JButton settingsButton = createButton("⚙️  System Settings");

        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 3, 18, 18)
        );
        buttonPanel.setBackground(background);

        buttonPanel.add(usersButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(projectsButton);
        buttonPanel.add(tasksButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(settingsButton);

        content.add(headingPanel, BorderLayout.NORTH);
        content.add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel(
                "CodeCrew © 2026  |  Online Project Management Tool"
        );
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        footer.setForeground(new Color(120, 125, 140));
        footer.setBorder(
                BorderFactory.createEmptyBorder(12, 0, 0, 0)
        );

        content.add(footer, BorderLayout.SOUTH);

        root.add(header, BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);

        setContentPane(root);

        // Button actions
        usersButton.addActionListener(e ->
                new AdminUsersFrame().setVisible(true)
        );

        addUserButton.addActionListener(e ->
                new AddUserFrame().setVisible(true)
        );

        projectsButton.addActionListener(e ->
                new AdminProjectsFrame().setVisible(true)
        );

        tasksButton.addActionListener(e ->
                new AdminTasksFrame().setVisible(true)
        );

       reportsButton.addActionListener(e ->
        new ReportsFrame().setVisible(true)
);
        settingsButton.addActionListener(e ->
                new AdminSettingsFrame().setVisible(true)
        );
    }

    // Reusable button design
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(navy);
        button.setBackground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 240), 1
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 12, 20, 12
                        )
                )
        );

        // Hover effect
        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(225, 235, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }
}