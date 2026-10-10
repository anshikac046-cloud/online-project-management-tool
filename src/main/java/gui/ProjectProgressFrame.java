package gui;

import dao.TaskDAO;
import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ProjectProgressFrame extends JFrame {

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color green = new Color(35, 145, 100);
    private final Color muted = new Color(105, 112, 130);

    private JProgressBar progressBar;
    private JLabel percentageLabel;
    private JLabel completedLabel;
    private JLabel totalLabel;
    private JLabel pendingLabel;

    public ProjectProgressFrame() {

        setTitle("CodeCrew | Project Progress");
        setSize(1000, 650);
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);

        // HEADER
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JLabel brandLabel = new JLabel("CODECREW");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        brandLabel.setForeground(new Color(170, 190, 255));

        JLabel titleLabel = new JLabel("Project Progress");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(5, 0, 4, 0));

        JLabel subtitleLabel = new JLabel(
                "Track completed and pending tasks across your projects."
        );
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 225, 240));

        header.add(brandLabel);
        header.add(titleLabel);
        header.add(subtitleLabel);

        // CONTENT
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(background);
        content.setBorder(new EmptyBorder(30, 35, 30, 35));

        JLabel overviewTitle = new JLabel("Overall Project Progress");
        overviewTitle.setFont(new Font("SansSerif", Font.BOLD, 23));
        overviewTitle.setForeground(navy);
        overviewTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(overviewTitle);
        content.add(Box.createVerticalStrut(22));

        // PROGRESS CARD
        JPanel progressCard = new JPanel(new BorderLayout(0, 18));
        progressCard.setBackground(Color.WHITE);
        progressCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 240)),
                new EmptyBorder(25, 25, 25, 25)
        ));
        progressCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));

        JPanel progressTop = new JPanel(new BorderLayout());
        progressTop.setOpaque(false);

        JLabel progressTitle = new JLabel("Task Completion");
        progressTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        progressTitle.setForeground(navy);

        percentageLabel = new JLabel("0%");
        percentageLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        percentageLabel.setForeground(blue);

        progressTop.add(progressTitle, BorderLayout.WEST);
        progressTop.add(percentageLabel, BorderLayout.EAST);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(500, 28));
        progressBar.setForeground(blue);
        progressBar.setBackground(new Color(230, 235, 245));
        progressBar.setBorderPainted(false);

        JLabel progressDescription = new JLabel(
                "Progress is calculated from tasks marked DONE or COMPLETED."
        );
        progressDescription.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );
        progressDescription.setForeground(muted);

        progressCard.add(progressTop, BorderLayout.NORTH);
        progressCard.add(progressBar, BorderLayout.CENTER);
        progressCard.add(progressDescription, BorderLayout.SOUTH);

        content.add(progressCard);
        content.add(Box.createVerticalStrut(25));

        // STATISTICS
        JLabel statsTitle = new JLabel("Task Summary");
        statsTitle.setFont(new Font("SansSerif", Font.BOLD, 21));
        statsTitle.setForeground(navy);
        statsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(statsTitle);
        content.add(Box.createVerticalStrut(15));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 18, 0));
        statsPanel.setOpaque(false);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

        completedLabel = new JLabel("0");
        totalLabel = new JLabel("0");
        pendingLabel = new JLabel("0");

        statsPanel.add(createStatCard(
                "COMPLETED TASKS", completedLabel, green
        ));

        statsPanel.add(createStatCard(
                "TOTAL TASKS", totalLabel, navy
        ));

        statsPanel.add(createStatCard(
                "PENDING TASKS", pendingLabel, new Color(220, 145, 40)
        ));

        content.add(statsPanel);
        content.add(Box.createVerticalStrut(20));

        // REFRESH BUTTON
        JButton refreshButton = new JButton("Refresh Progress");
        refreshButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        refreshButton.setBackground(blue);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setBorder(new EmptyBorder(12, 20, 12, 20));
        refreshButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        refreshButton.addActionListener(e -> loadProgress());

        content.add(refreshButton);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        root.add(header, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);

        setContentPane(root);

        loadProgress();
    }

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            Color accentColor) {

        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 4, 0, 0, accentColor
                ),
                new EmptyBorder(18, 15, 18, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        titleLabel.setForeground(muted);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void loadProgress() {

        try {
            TaskDAO taskDAO = new TaskDAO();
            List<Task> tasks = taskDAO.getAllTasks();

            if (tasks == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to load tasks. Check your database connection.",
                        "Progress Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            int total = tasks.size();
            int completed = 0;

            for (Task task : tasks) {
                String status = task.getStatus();

                if ("DONE".equalsIgnoreCase(status)
                        || "COMPLETED".equalsIgnoreCase(status)) {
                    completed++;
                }
            }

            int pending = total - completed;
            int percentage = total == 0
                    ? 0
                    : (int) Math.round(completed * 100.0 / total);

            completedLabel.setText(String.valueOf(completed));
            totalLabel.setText(String.valueOf(total));
            pendingLabel.setText(String.valueOf(pending));

            percentageLabel.setText(percentage + "%");
            progressBar.setValue(percentage);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load project progress:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}