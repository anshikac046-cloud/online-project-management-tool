package gui;

import dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddUserFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color textColor = new Color(45, 52, 75);

    public AddUserFrame() {

        setTitle("CodeCrew | Add New User");
        setSize(650, 600);
        setMinimumSize(new Dimension(550, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel titleLabel = new JLabel("Add New User");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(8, 0, 5, 0));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel =
                new JLabel("Create an account and assign a role.");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 225, 240));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(brandLabel);
        header.add(titleLabel);
        header.add(subtitleLabel);

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(9, 5, 9, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        roleBox = new JComboBox<>(new String[]{
                "ADMIN",
                "PROJECT_MANAGER",
                "TEAM_MEMBER"
        });

        styleField(nameField);
        styleField(emailField);
        styleField(passwordField);

        roleBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roleBox.setBackground(Color.WHITE);
        roleBox.setForeground(textColor);
        roleBox.setPreferredSize(new Dimension(250, 42));
        roleBox.setFocusable(false);

        addFormRow(formPanel, gbc, 0, "Full Name", nameField);
        addFormRow(formPanel, gbc, 1, "Email Address", emailField);
        addFormRow(formPanel, gbc, 2, "Password", passwordField);
        addFormRow(formPanel, gbc, 3, "User Role", roleBox);

        // BUTTONS
        JButton addButton = new JButton("Add User");
        JButton clearButton = new JButton("Clear Fields");

        styleButton(addButton, blue, Color.WHITE);
        styleButton(clearButton, new Color(235, 239, 250), navy);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(25, 5, 0, 5);
        formPanel.add(buttonPanel, gbc);

        // ASSEMBLE WINDOW
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        addButton.addActionListener(e -> addUser());
        clearButton.addActionListener(e -> clearFields());

        getRootPane().setDefaultButton(addButton);
    }

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent field) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(textColor);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(9, 5, 4, 5);
        panel.add(label, gbc);

        gbc.gridy = row + 1;
        gbc.insets = new Insets(0, 5, 10, 5);
        field.setPreferredSize(new Dimension(250, 42));
        panel.add(field, gbc);
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(textColor);
        field.setBackground(new Color(250, 251, 255));
        field.setCaretColor(blue);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 221, 235)),
                new EmptyBorder(8, 12, 8, 12)
        ));
    }

    private void styleButton(
            JButton button,
            Color backgroundColor,
            Color foregroundColor) {

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setPreferredSize(new Dimension(150, 44));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(12, 15, 12, 15));
    }

    private void addUser() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.addUser(
                name,
                email,
                password,
                role
        );

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "User added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            clearFields();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add user.\nThe email may already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        roleBox.setSelectedIndex(0);
        nameField.requestFocusInWindow();
    }
}