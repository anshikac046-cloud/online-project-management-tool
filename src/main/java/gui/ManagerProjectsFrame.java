package gui;

import dao.ProjectDAO;
import model.Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ManagerProjectsFrame extends JFrame {

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color borderColor = new Color(225, 230, 240);

    private JTable projectTable;
    private DefaultTableModel tableModel;

    public ManagerProjectsFrame() {

        setTitle("CodeCrew | Manage Projects");
        setSize(1200, 650);
        setMinimumSize(new Dimension(850, 450));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(navy);
        headerPanel.setBorder(
                new EmptyBorder(22, 30, 22, 30)
        );

        JLabel brandLabel = new JLabel("CodeCrew");
        brandLabel.setFont(
                new Font("SansSerif", Font.BOLD, 15)
        );
        brandLabel.setForeground(new Color(170, 190, 255));

        JLabel titleLabel = new JLabel("Project Management");
        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(
                "View project details, timelines and assigned managers."
        );
        subtitleLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );
        subtitleLabel.setForeground(new Color(220, 225, 240));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setOpaque(false);

        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headingPanel.add(brandLabel);
        headingPanel.add(Box.createVerticalStrut(6));
        headingPanel.add(titleLabel);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitleLabel);

        headerPanel.add(headingPanel, BorderLayout.WEST);

        // REFRESH BUTTON
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(blue);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setOpaque(true);
        refreshButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        refreshButton.setBorder(
                new EmptyBorder(12, 20, 12, 20)
        );

        headerPanel.add(refreshButton, BorderLayout.EAST);

        // TABLE
        String[] columns = {
                "Project ID",
                "Title",
                "Description",
                "Start Date",
                "End Date",
                "Manager ID",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        projectTable = new JTable(tableModel);
        projectTable.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );
        projectTable.setRowHeight(40);
        projectTable.setBackground(Color.WHITE);
        projectTable.setForeground(new Color(45, 52, 75));
        projectTable.setSelectionBackground(
                new Color(225, 233, 255)
        );
        projectTable.setSelectionForeground(navy);
        projectTable.setGridColor(borderColor);
        projectTable.setShowGrid(true);
        projectTable.setFillsViewportHeight(true);
        projectTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // TABLE HEADER
        JTableHeader tableHeader = projectTable.getTableHeader();
        tableHeader.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        tableHeader.setBackground(new Color(232, 238, 250));
        tableHeader.setForeground(navy);
        tableHeader.setOpaque(true);
        tableHeader.setPreferredSize(new Dimension(0, 44));
        tableHeader.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer =
                new DefaultTableCellRenderer();

        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(new Color(232, 238, 250));
        headerRenderer.setForeground(navy);
        headerRenderer.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        headerRenderer.setBorder(
                new EmptyBorder(0, 10, 0, 10)
        );

        for (int i = 0; i < projectTable.getColumnCount(); i++) {
            projectTable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // COLUMN WIDTHS
        int[] columnWidths = {
                120, 240, 320, 150, 150, 140, 170
        };

        for (int i = 0; i < columnWidths.length; i++) {
            projectTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(columnWidths[i]);
        }

        // CENTER ID COLUMNS
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        projectTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        projectTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(centerRenderer);

        // ALTERNATING ROW COLORS
        projectTable.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column
                                );

                        if (!isSelected) {
                            component.setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : new Color(248, 250, 255)
                            );
                            component.setForeground(
                                    new Color(45, 52, 75)
                            );
                        }

                        setBorder(
                                new EmptyBorder(0, 10, 0, 10)
                        );

                        return component;
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(background);
        tablePanel.setBorder(
                new EmptyBorder(25, 25, 25, 25)
        );
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        refreshButton.addActionListener(e -> loadProjects());

        loadProjects();
    }

    private void loadProjects() {

        tableModel.setRowCount(0);

        try {
            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projects = projectDAO.getAllProjects();

            if (projects == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to load projects.",
                        "Project Loading Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            for (Project project : projects) {
                tableModel.addRow(new Object[]{
                        project.getProjectId(),
                        project.getTitle(),
                        project.getDescription(),
                        project.getStartDate(),
                        project.getEndDate(),
                        project.getManagerId(),
                        project.getStatus()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load projects:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}