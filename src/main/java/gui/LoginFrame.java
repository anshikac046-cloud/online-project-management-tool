package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);

    public LoginFrame() {

        setTitle("CodeCrew | Login");
        setSize(650, 500);
        setMinimumSize(new Dimension(550, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel brandLabel = new JLabel("CODECREW");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        brandLabel.setForeground(new Color(170, 190, 255));
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel("Welcome Back!");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(8, 0, 5, 0));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel =
                new JLabel("Login to manage your projects and tasks.");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 225, 240));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(brandLabel);
        header.add(titleLabel);
        header.add(subtitleLabel);

        // LOGIN FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        emailLabel.setForeground(navy);

        emailField = new JTextField();
        styleField(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passwordLabel.setForeground(navy);

        passwordField = new JPasswordField();
        styleField(passwordField);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);

        gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);

        gbc.gridy = 1;
        formPanel.add(emailField, gbc);

        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 8, 0);
        formPanel.add(loginButton, gbc);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        loginButton.addActionListener(e -> login());
        getRootPane().setDefaultButton(loginButton);
    }

    private void login() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both email and password.",
                    "Missing Details",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(email);

            if (user == null ||
                    user.getPassword() == null ||
                    !password.equals(user.getPassword().trim())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String role = user.getRole();

            if (role == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No role is assigned to this account.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Open the dashboard according to the user's role
            switch (role.trim().toUpperCase()) {

                case "ADMIN":
                    new AdminDashboard(user.getName()).setVisible(true);
                    break;

                case "PROJECT_MANAGER":
                    new ManagerDashboard(user.getName()).setVisible(true);
                    break;

                case "TEAM_MEMBER":
                    new TeamMemberDashboard(user).setVisible(true);
                    break;

                default:
                    JOptionPane.showMessageDialog(
                            this,
                            "Unknown user role: " + role,
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
            }

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to login. Please check your database connection.\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

    private void styleField(JTextField field) {

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 42));
        field.setBackground(new Color(250, 251, 255));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 221, 235)),
                new EmptyBorder(8, 12, 8, 12)
        ));
    }

    private void styleButton(JButton button) {

        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(150, 44));
        button.setBackground(blue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}