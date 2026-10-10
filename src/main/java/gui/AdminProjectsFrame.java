package gui;

import dao.ProjectDAO;
import model.Project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class AdminProjectsFrame extends JFrame {

    private JTable projectTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color textColor = new Color(45, 52, 75);

    public AdminProjectsFrame() {

        setTitle("CodeCrew | Project Management");
        setSize(1150, 650);
        setMinimumSize(new Dimension(850, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(background);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel brandLabel = new JLabel("CODECREW");
        brandLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        brandLabel.setForeground(new Color(170, 190, 255));

        JLabel titleLabel = new JLabel("Project Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(5, 0, 4, 0));

        JLabel subtitleLabel =
                new JLabel("View and manage all registered projects.");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 225, 240));

        titlePanel.add(brandLabel);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        header.add(titlePanel, BorderLayout.WEST);

        // TOOLBAR
        JPanel toolbar = new JPanel(new BorderLayout(12, 0));
        toolbar.setBackground(background);
        toolbar.setBorder(new EmptyBorder(20, 25, 15, 25));

        JLabel searchLabel = new JLabel("Search projects:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        searchLabel.setForeground(textColor);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 221, 235)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, blue, Color.WHITE);
        refreshButton.addActionListener(e -> loadProjects());

        toolbar.add(searchPanel, BorderLayout.CENTER);
        toolbar.add(refreshButton, BorderLayout.EAST);

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
        projectTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        projectTable.setRowHeight(38);
        projectTable.setForeground(textColor);
        projectTable.setBackground(Color.WHITE);
        projectTable.setSelectionBackground(new Color(225, 233, 255));
        projectTable.setSelectionForeground(navy);
        projectTable.setGridColor(new Color(232, 236, 244));
        projectTable.setShowVerticalLines(false);
        projectTable.setShowHorizontalLines(true);
        projectTable.setFillsViewportHeight(true);
        projectTable.setAutoCreateRowSorter(true);
        projectTable.setIntercellSpacing(new Dimension(0, 1));

        // TABLE HEADER
        JTableHeader tableHeader = projectTable.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 13));
        tableHeader.setBackground(navy);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setOpaque(true);
        tableHeader.setPreferredSize(new Dimension(0, 44));
        tableHeader.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer =
                new DefaultTableCellRenderer();

        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(navy);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(new Font("SansSerif", Font.BOLD, 13));
        headerRenderer.setBorder(
                new EmptyBorder(0, 10, 0, 10)
        );

        for (int i = 0;
             i < projectTable.getColumnModel().getColumnCount();
             i++) {

            projectTable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // COLUMN WIDTHS
        int[] columnWidths = {85, 200, 280, 110, 110, 100, 140};

        for (int i = 0; i < columnWidths.length; i++) {
            projectTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(columnWidths[i]);
        }

        // CELL ALIGNMENT
        DefaultTableCellRenderer leftRenderer =
                new DefaultTableCellRenderer();

        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        leftRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));

        for (int i = 0; i < projectTable.getColumnCount(); i++) {
            projectTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(leftRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(225, 230, 240)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(0, 25, 25, 25));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // SEARCH FILTER
        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(tableModel);

        projectTable.setRowSorter(sorter);

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void filter() {
                        String query = searchField.getText().trim();

                        if (query.isEmpty()) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(
                                    RowFilter.regexFilter(
                                            "(?i)" + java.util.regex.Pattern.quote(query)
                                    )
                            );
                        }
                    }

                    @Override
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }
                }
        );

        mainPanel.add(header, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(background);
        contentPanel.add(toolbar, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        loadProjects();
    }

    private void loadProjects() {

        tableModel.setRowCount(0);

        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projects = projectDAO.getAllProjects();

        if (projects == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load projects. Please check the database connection.",
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
    }

    private void styleButton(
            JButton button,
            Color bg,
            Color fg) {

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }
}