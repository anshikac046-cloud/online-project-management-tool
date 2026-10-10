package gui;

import dao.ProjectDAO;
import dao.TaskDAO;
import model.Project;
import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ReportsFrame extends JFrame {

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color textColor = new Color(45, 52, 75);

    private JPanel statsPanel;
    private JPanel projectsPanel;
    private JLabel totalProjectsLabel;
    private JLabel totalTasksLabel;
    private JLabel completedTasksLabel;
    private JLabel pendingTasksLabel;

    public ReportsFrame() {

        setTitle("CodeCrew | System Reports");
        setSize(1050, 700);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JLabel brandLabel = new JLabel("CODECREW");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        brandLabel.setForeground(new Color(170, 190, 255));

        JLabel titleLabel = new JLabel("System Reports");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(5, 0, 4, 0));

        JLabel subtitleLabel =
                new JLabel("Project overview and task completion summary");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 225, 240));

        header.add(brandLabel);
        header.add(titleLabel);
        header.add(subtitleLabel);

        // SUMMARY CARDS
        statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        totalProjectsLabel = new JLabel("0");
        totalTasksLabel = new JLabel("0");
        completedTasksLabel = new JLabel("0");
        pendingTasksLabel = new JLabel("0");

        statsPanel.add(createStatCard(
                "TOTAL PROJECTS", totalProjectsLabel, blue));

        statsPanel.add(createStatCard(
                "TOTAL TASKS", totalTasksLabel, navy));

        statsPanel.add(createStatCard(
                "COMPLETED TASKS", completedTasksLabel,
                new Color(35, 145, 100)));

        statsPanel.add(createStatCard(
                "PENDING TASKS", pendingTasksLabel,
                new Color(220, 145, 40)));

        // PROJECT SECTION
        JLabel projectsTitle = new JLabel("Project Overview");
        projectsTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        projectsTitle.setForeground(navy);
        projectsTitle.setBorder(new EmptyBorder(0, 0, 12, 0));

        projectsPanel = new JPanel();
        projectsPanel.setLayout(new BoxLayout(projectsPanel, BoxLayout.Y_AXIS));
        projectsPanel.setBackground(background);

        JPanel projectSection = new JPanel(new BorderLayout());
        projectSection.setBackground(background);
        projectSection.setBorder(new EmptyBorder(0, 25, 25, 25));
        projectSection.add(projectsTitle, BorderLayout.NORTH);
        projectSection.add(projectsPanel, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Report");
        styleButton(refreshButton);
        refreshButton.addActionListener(e -> loadReport());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(background);
        buttonPanel.setBorder(new EmptyBorder(0, 25, 15, 25));
        buttonPanel.add(refreshButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(background);
        contentPanel.add(statsPanel, BorderLayout.NORTH);
        contentPanel.add(projectSection, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        loadReport();
    }

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            Color accentColor) {

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 4, 0, 0, accentColor),
                new EmptyBorder(18, 15, 18, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        titleLabel.setForeground(new Color(105, 112, 130));

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void loadReport() {

        ProjectDAO projectDAO = new ProjectDAO();
        TaskDAO taskDAO = new TaskDAO();

        List<Project> projects = projectDAO.getAllProjects();
        List<Task> tasks = taskDAO.getAllTasks();

        if (projects == null || tasks == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load the report. Please check the database connection.",
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int completed = 0;
        int pending = 0;

        for (Task task : tasks) {
            if ("DONE".equalsIgnoreCase(task.getStatus())
                    || "COMPLETED".equalsIgnoreCase(task.getStatus())) {
                completed++;
            } else {
                pending++;
            }
        }

        totalProjectsLabel.setText(String.valueOf(projects.size()));
        totalTasksLabel.setText(String.valueOf(tasks.size()));
        completedTasksLabel.setText(String.valueOf(completed));
        pendingTasksLabel.setText(String.valueOf(pending));

        projectsPanel.removeAll();

        if (projects.isEmpty()) {
            JLabel emptyLabel = new JLabel("No projects available.");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            emptyLabel.setForeground(textColor);
            emptyLabel.setBorder(new EmptyBorder(15, 15, 15, 15));
            projectsPanel.add(emptyLabel);
        } else {
            for (Project project : projects) {
                projectsPanel.add(createProjectCard(project));
                projectsPanel.add(Box.createVerticalStrut(12));
            }
        }

        projectsPanel.revalidate();
        projectsPanel.repaint();
    }

    private JPanel createProjectCard(Project project) {

        JPanel card = new JPanel(new BorderLayout(15, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 240)),
                new EmptyBorder(16, 18, 16, 18)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);

        JLabel titleLabel = new JLabel(
                project.getTitle() == null
                        ? "Untitled Project"
                        : project.getTitle()
        );
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        titleLabel.setForeground(navy);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel(
                "Project ID: " + project.getProjectId()
        );
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        idLabel.setForeground(new Color(105, 112, 130));
        idLabel.setBorder(new EmptyBorder(5, 0, 8, 0));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateLabel = new JLabel(
                "Start: " + project.getStartDate()
                        + "     |     End: " + project.getEndDate()
        );
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateLabel.setForeground(textColor);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        details.add(titleLabel);
        details.add(idLabel);
        details.add(dateLabel);

        String status = String.valueOf(project.getStatus());

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setForeground(blue);
        statusLabel.setBorder(new EmptyBorder(7, 10, 7, 10));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(235, 239, 255));

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setOpaque(false);
        statusPanel.add(statusLabel);

        card.add(details, BorderLayout.CENTER);
        card.add(statusPanel, BorderLayout.EAST);

        return card;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(blue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(11, 20, 11, 20));
    }
}