package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfileFrame extends JFrame {

    // Color theme
    private final Color NAVY_BLUE = new Color(0, 31, 84);
    private final Color HOVER_BLUE = new Color(30, 90, 160);
    private final Color LIGHT_BLUE = new Color(225, 237, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_GRAY = new Color(75, 95, 125);

    public ProfileFrame(int userId) {

        setTitle("My Profile");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(650, 450));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 25));
        mainPanel.setBackground(LIGHT_BLUE);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(LIGHT_BLUE);

        JLabel titleLabel = new JLabel(
                "My Profile",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(NAVY_BLUE);

        JLabel subtitleLabel = new JLabel(
                "View your personal information",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitleLabel.setForeground(TEXT_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Fetch user from database
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail("anshika@codecrew.com");

        // Profile card
        JPanel profileCard = new JPanel(new GridLayout(4, 2, 20, 20));
        profileCard.setBackground(WHITE);
        profileCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(170, 190, 215), 1, true),
                        new EmptyBorder(30, 30, 30, 30)
                )
        );

        if (user != null) {

            addProfileRow(profileCard, "Name", user.getName());
            addProfileRow(profileCard, "Email", user.getEmail());
            addProfileRow(profileCard, "Role", user.getRole());
            addProfileRow(
                    profileCard,
                    "User ID",
                    String.valueOf(user.getUserId())
            );

        } else {

            JLabel errorLabel = new JLabel(
                    "User profile not found.",
                    SwingConstants.CENTER
            );

            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            errorLabel.setForeground(Color.RED);

            profileCard.setLayout(new BorderLayout());
            profileCard.add(errorLabel, BorderLayout.CENTER);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(LIGHT_BLUE);

        JButton closeButton = new JButton("Close");

        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setForeground(WHITE);
        closeButton.setBackground(NAVY_BLUE);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setBorder(
                new EmptyBorder(12, 35, 12, 35)
        );

        // Hover effect
        closeButton.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        closeButton.setBackground(HOVER_BLUE);
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        closeButton.setBackground(NAVY_BLUE);
                    }
                }
        );

        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        // Add components
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(profileCard, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Add a row to the profile card
    private void addProfileRow(
            JPanel panel,
            String label,
            String value) {

        JLabel nameLabel = new JLabel(label + ":");

        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setForeground(NAVY_BLUE);

        JLabel valueLabel = new JLabel(
                value == null ? "Not available" : value
        );

        valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        valueLabel.setForeground(TEXT_GRAY);

        panel.add(nameLabel);
        panel.add(valueLabel);
    }
}