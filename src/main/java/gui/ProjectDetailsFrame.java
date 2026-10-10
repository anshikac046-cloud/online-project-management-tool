package gui;

import dao.ProjectDAO;
import model.Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ProjectDetailsFrame extends JFrame {

    // Color theme
    private final Color NAVY_BLUE = new Color(0, 31, 84);
    private final Color HOVER_BLUE = new Color(30, 90, 160);
    private final Color LIGHT_BLUE = new Color(225, 237, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_GRAY = new Color(75, 95, 125);

    public ProjectDetailsFrame() {

        setTitle("Project Details");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 500));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(LIGHT_BLUE);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Header panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(LIGHT_BLUE);

        JLabel titleLabel = new JLabel(
                "Project Details",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(NAVY_BLUE);

        JLabel subtitleLabel = new JLabel(
                "View project information and current progress",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        // Project information area
        JTextArea projectArea = new JTextArea();

        projectArea.setEditable(false);
        projectArea.setFont(new Font("Arial", Font.PLAIN, 16));
        projectArea.setForeground(NAVY_BLUE);
        projectArea.setBackground(WHITE);
        projectArea.setMargin(new Insets(20, 20, 20, 20));
        projectArea.setLineWrap(true);
        projectArea.setWrapStyleWord(true);
        projectArea.setCaretPosition(0);

        // Fetch projects from database
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAllProjects();

        if (projects == null || projects.isEmpty()) {

            projectArea.setText(
                    "No projects are currently available."
            );

        } else {

            for (Project project : projects) {

                projectArea.append(
                        "PROJECT ID: " + project.getProjectId() + "\n\n" +
                        "Project Title\n" +
                        project.getTitle() + "\n\n" +
                        "Description\n" +
                        project.getDescription() + "\n\n" +
                        "Start Date: " + project.getStartDate() + "\n" +
                        "End Date:   " + project.getEndDate() + "\n\n" +
                        "Project Status: " + project.getStatus() + "\n" +
                        "\n" +
                        "--------------------------------------------------\n\n"
                );
            }
        }

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(projectArea);

        scrollPane.setBorder(
                new LineBorder(new Color(170, 190, 215), 1)
        );

        scrollPane.getViewport().setBackground(WHITE);

        // Close button
        JButton closeButton = new JButton("Close");

        closeButton.setFont(new Font("Arial", Font.BOLD, 13));
        closeButton.setForeground(WHITE);
        closeButton.setBackground(NAVY_BLUE);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setBorder(
                new EmptyBorder(10, 30, 10, 30)
        );

        // Button hover effect
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

        // Button panel
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        buttonPanel.setBackground(LIGHT_BLUE);
        buttonPanel.add(closeButton);

        // Add components
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}