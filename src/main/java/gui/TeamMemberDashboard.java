package gui;

import model.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TeamMemberDashboard extends JFrame {

    // Color theme
    private final Color NAVY_BLUE = new Color(0, 31, 84);
    private final Color HOVER_BLUE = new Color(30, 90, 160);
    private final Color LIGHT_BLUE = new Color(225, 237, 250);
    private final Color WHITE = Color.WHITE;

    public TeamMemberDashboard(User user) {

        String memberName = user.getName();
        int userId = user.getUserId();

        setTitle("Team Member Dashboard");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 25));
        mainPanel.setBackground(LIGHT_BLUE);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        // Welcome heading
        JLabel titleLabel = new JLabel(
                "Welcome, " + memberName,
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 30)
        );

        titleLabel.setForeground(NAVY_BLUE);

        JLabel subtitleLabel = new JLabel(
                "Your workspace at a glance",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        subtitleLabel.setForeground(new Color(75, 95, 125));

        // Header panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(LIGHT_BLUE);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Dashboard cards
        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 2, 25, 25)
        );

        buttonPanel.setBackground(LIGHT_BLUE);

        // Create buttons
        JButton tasksButton = createDashboardButton(
                "My Tasks",
                "View and manage your tasks"
        );

        JButton projectButton = createDashboardButton(
                "Project Details",
                "View your project information"
        );

        JButton profileButton = createDashboardButton(
                "My Profile",
                "View your personal details"
        );

        JButton notificationButton = createDashboardButton(
                "Notifications",
                "Check your latest updates"
        );

        // Add buttons to panel
        buttonPanel.add(tasksButton);
        buttonPanel.add(projectButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(notificationButton);

        // Footer
        JLabel footerLabel = new JLabel(
                "TEAM MEMBER PORTAL",
                SwingConstants.CENTER
        );

        footerLabel.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        footerLabel.setForeground(new Color(90, 110, 140));

        // Add components
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // My Tasks
        tasksButton.addActionListener(e -> {
            MyTasksFrame myTasks = new MyTasksFrame(userId);
            myTasks.setVisible(true);
        });

        // Project Details
        projectButton.addActionListener(e -> {
            ProjectDetailsFrame projectDetails =
                    new ProjectDetailsFrame();

            projectDetails.setVisible(true);
        });

        // My Profile
        profileButton.addActionListener(e -> {
            ProfileFrame profileFrame = new ProfileFrame(userId);
            profileFrame.setVisible(true);
        });

        // Notifications
        notificationButton.addActionListener(e -> {
            NotificationsFrame notificationsFrame =
                    new NotificationsFrame(userId);

            notificationsFrame.setVisible(true);
        });
    }

    // Create stylish dashboard cards
    private JButton createDashboardButton(
            String title,
            String description
    ) {

        JButton button = new JButton();

        button.setLayout(new BorderLayout(5, 10));

        button.setBackground(WHITE);
        button.setForeground(NAVY_BLUE);

        button.setFocusPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(160, 185, 215),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                25, 15, 25, 15
                        )
                )
        );

        // Button title
        JLabel titleLabel = new JLabel(
                title,
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 21)
        );

        titleLabel.setForeground(NAVY_BLUE);

        // Button description
        JLabel descriptionLabel = new JLabel(
                description,
                SwingConstants.CENTER
        );

        descriptionLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        descriptionLabel.setForeground(
                new Color(90, 105, 130)
        );

        // Text panel
        JPanel textPanel = new JPanel(
                new GridLayout(2, 1, 5, 8)
        );

        textPanel.setOpaque(false);

        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);

        button.add(textPanel, BorderLayout.CENTER);

        // Hover effect
        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(NAVY_BLUE);
                        button.setBorder(
                                new LineBorder(
                                        HOVER_BLUE,
                                        2,
                                        true
                                )
                        );

                        titleLabel.setForeground(WHITE);
                        descriptionLabel.setForeground(
                                new Color(220, 235, 255)
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(WHITE);

                        button.setBorder(
                                BorderFactory.createCompoundBorder(
                                        new LineBorder(
                                                new Color(160, 185, 215),
                                                1,
                                                true
                                        ),
                                        BorderFactory.createEmptyBorder(
                                                25, 15, 25, 15
                                        )
                                )
                        );

                        titleLabel.setForeground(NAVY_BLUE);
                        descriptionLabel.setForeground(
                                new Color(90, 105, 130)
                        );
                    }
                }
        );

        return button;
    }
}