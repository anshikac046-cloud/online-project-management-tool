package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerDashboard extends JFrame {

    private final Color navy = new Color(20, 38, 82);
    private final Color blue = new Color(40, 112, 255);
    private final Color background = new Color(242, 247, 255);
    private final Color textColor = new Color(24, 45, 91);

    public ManagerDashboard(String managerName) {

        setTitle("Project Manager Dashboard");
        setSize(1200, 750);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(background);

        // HEADER
        JPanel header = new JPanel(new BorderLayout(35, 0));
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(30, 40, 30, 40));

        // BRAND
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        brandPanel.setOpaque(false);

        JLabel logo = new JLabel("⬡");
        logo.setFont(new Font("SansSerif", Font.BOLD, 55));
        logo.setForeground(new Color(105, 165, 255));

        JLabel brand = new JLabel("CodeCrew");
        brand.setFont(new Font("SansSerif", Font.BOLD, 36));
        brand.setForeground(Color.WHITE);

        brandPanel.add(logo);
        brandPanel.add(brand);

        // DIVIDER
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setForeground(new Color(85, 110, 165));
        separator.setPreferredSize(new Dimension(2, 90));

        // WELCOME
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, " + managerName);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 32));
        welcome.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Manage your projects, tasks and progress in one place."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 17));
        subtitle.setForeground(new Color(215, 225, 245));
        subtitle.setBorder(new EmptyBorder(8, 0, 0, 0));

        welcomePanel.add(welcome);
        welcomePanel.add(subtitle);

        header.add(brandPanel, BorderLayout.WEST);
        header.add(separator, BorderLayout.CENTER);
        header.add(welcomePanel, BorderLayout.EAST);

        // DASHBOARD CARDS
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        cardsPanel.setBackground(background);
        cardsPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JButton projectsCard = createCard(
                "📁", "Manage Projects", "View and manage projects"
        );

        JButton tasksCard = createCard(
                "☷", "Manage Tasks", "View assigned project tasks"
        );

        JButton progressCard = createCard(
                "▥", "Project Progress", "Track project completion"
        );

        JButton reportsCard = createCard(
                "▤", "Reports", "View project reports"
        );

        cardsPanel.add(projectsCard);
        cardsPanel.add(tasksCard);
        cardsPanel.add(progressCard);
        cardsPanel.add(reportsCard);

        root.add(header, BorderLayout.NORTH);
        root.add(cardsPanel, BorderLayout.CENTER);

        setContentPane(root);

        // BUTTON ACTIONS
        projectsCard.addActionListener(e ->
                new AdminProjectsFrame().setVisible(true)
        );

        tasksCard.addActionListener(e ->
                new AdminTasksFrame().setVisible(true)
        );

        progressCard.addActionListener(e ->
                new ProjectProgressFrame().setVisible(true)
        );

        reportsCard.addActionListener(e ->
                new ReportsFrame().setVisible(true)
        );
    }

    private JButton createCard(
            String iconText,
            String titleText,
            String descriptionText) {

        JButton card = new JButton();
        card.setLayout(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setFocusPainted(false);
        card.setContentAreaFilled(true);
        card.setOpaque(true);
        card.setBorderPainted(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 233, 247), 1
                ),
                new EmptyBorder(30, 25, 30, 25)
        ));

        // ICON
        JLabel icon = new JLabel(iconText, SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 43));
        icon.setForeground(blue);
        icon.setPreferredSize(new Dimension(90, 100));

        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setBackground(new Color(232, 241, 255));
        iconPanel.setPreferredSize(new Dimension(100, 110));
        iconPanel.add(icon);

        // TEXT
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(textColor);

        JLabel description = new JLabel(descriptionText);
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(new Color(110, 120, 145));
        description.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel accent = new JLabel("━━━━━━━━");
        accent.setFont(new Font("SansSerif", Font.BOLD, 12));
        accent.setForeground(blue);
        accent.setBorder(new EmptyBorder(10, 0, 0, 0));

        textPanel.add(title);
        textPanel.add(description);
        textPanel.add(accent);

        // ARROW
        JLabel arrow = new JLabel("➜", SwingConstants.CENTER);
        arrow.setFont(new Font("SansSerif", Font.BOLD, 24));
        arrow.setForeground(blue);
        arrow.setPreferredSize(new Dimension(45, 45));

        JPanel arrowPanel = new JPanel(new GridBagLayout());
        arrowPanel.setOpaque(false);
        arrowPanel.add(arrow);

        card.add(iconPanel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(arrowPanel, BorderLayout.EAST);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(248, 250, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
        });

        return card;
    }
}