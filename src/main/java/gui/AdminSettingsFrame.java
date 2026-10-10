package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class AdminSettingsFrame extends JFrame {

    // Navy blue color theme
    private final Color NAVY_BLUE = new Color(0, 31, 84);
    private final Color LIGHT_BLUE = new Color(225, 237, 250);
    private final Color WHITE = Color.WHITE;
    private final Color HOVER_BLUE = new Color(30, 90, 160);

    public AdminSettingsFrame() {

        setTitle("System Settings");
        setSize(720, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBackground(LIGHT_BLUE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Title
        JLabel titleLabel = new JLabel("System Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(NAVY_BLUE);
        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 15, 0)
        );

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(LIGHT_BLUE);
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Settings panel
        JPanel settingsPanel = new JPanel(new GridLayout(3, 2, 20, 25));
        settingsPanel.setBackground(WHITE);
        settingsPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(180, 200, 225), 1, true),
                        BorderFactory.createEmptyBorder(25, 20, 25, 20)
                )
        );

        // Theme
        JLabel themeLabel = createLabel("Theme:");

        JComboBox<String> themeBox =
                new JComboBox<>(new String[]{"Light", "Dark"});

        styleComboBox(themeBox);

        // Notifications
        JLabel notificationLabel = createLabel("Notifications:");

        JCheckBox notificationBox =
                new JCheckBox("Enable Notifications", true);

        notificationBox.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        notificationBox.setForeground(NAVY_BLUE);
        notificationBox.setBackground(WHITE);
        notificationBox.setFocusPainted(false);

        // System status
        JLabel systemLabel = createLabel("System Status:");

        JLabel statusLabel =
                new JLabel("● System is running normally");

        statusLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );
        statusLabel.setForeground(new Color(0, 128, 70));

        // Add components
        settingsPanel.add(themeLabel);
        settingsPanel.add(themeBox);

        settingsPanel.add(notificationLabel);
        settingsPanel.add(notificationBox);

        settingsPanel.add(systemLabel);
        settingsPanel.add(statusLabel);

        // Buttons
        JButton saveButton = createButton("Save Settings");
        JButton closeButton = createButton("Close");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(LIGHT_BLUE);
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Save button action
        saveButton.addActionListener(e -> {

            String selectedTheme =
                    (String) themeBox.getSelectedItem();

            boolean notificationsEnabled =
                    notificationBox.isSelected();

            JOptionPane.showMessageDialog(
                    this,
                    "Settings saved successfully!\n\n"
                            + "Theme: " + selectedTheme + "\n"
                            + "Notifications: "
                            + (notificationsEnabled ? "Enabled" : "Disabled"),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Close button action
        closeButton.addActionListener(e -> dispose());

        // Change theme selection
        themeBox.addActionListener(e -> {

            String selectedTheme =
                    (String) themeBox.getSelectedItem();

            if ("Dark".equals(selectedTheme)) {
                mainPanel.setBackground(new Color(35, 42, 58));
                titlePanel.setBackground(new Color(35, 42, 58));
                settingsPanel.setBackground(new Color(48, 56, 72));
                buttonPanel.setBackground(new Color(35, 42, 58));

                titleLabel.setForeground(Color.WHITE);
                themeLabel.setForeground(Color.WHITE);
                notificationLabel.setForeground(Color.WHITE);
                systemLabel.setForeground(Color.WHITE);

                notificationBox.setBackground(new Color(48, 56, 72));
                notificationBox.setForeground(Color.WHITE);

                statusLabel.setForeground(new Color(100, 230, 150));

            } else {
                mainPanel.setBackground(LIGHT_BLUE);
                titlePanel.setBackground(LIGHT_BLUE);
                settingsPanel.setBackground(WHITE);
                buttonPanel.setBackground(LIGHT_BLUE);

                titleLabel.setForeground(NAVY_BLUE);
                themeLabel.setForeground(NAVY_BLUE);
                notificationLabel.setForeground(NAVY_BLUE);
                systemLabel.setForeground(NAVY_BLUE);

                notificationBox.setBackground(WHITE);
                notificationBox.setForeground(NAVY_BLUE);

                statusLabel.setForeground(new Color(0, 128, 70));
            }

            mainPanel.repaint();
        });
    }

    // Create navy blue labels
    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 17)
        );

        label.setForeground(NAVY_BLUE);

        return label;
    }

    // Style dropdown
    private void styleComboBox(JComboBox<String> comboBox) {

        comboBox.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        comboBox.setForeground(NAVY_BLUE);
        comboBox.setBackground(WHITE);
        comboBox.setBorder(
                new LineBorder(new Color(130, 160, 200), 1, true)
        );

        comboBox.setFocusable(false);
    }

    // Create buttons with hover effects
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        button.setForeground(WHITE);
        button.setBackground(NAVY_BLUE);

        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(NAVY_BLUE, 1, true),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(HOVER_BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(NAVY_BLUE);
            }
        });

        return button;
    }
}