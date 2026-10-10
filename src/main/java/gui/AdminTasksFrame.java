package gui;

import dao.TaskDAO;
import model.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.regex.Pattern;

public class AdminTasksFrame extends JFrame {

    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color textColor = new Color(45, 52, 75);

    public AdminTasksFrame() {

        setTitle("CodeCrew | Task Management");
        setSize(1250, 650);
        setMinimumSize(new Dimension(900, 500));
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

        JLabel titleLabel = new JLabel("Task Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(5, 0, 4, 0));

        JLabel subtitleLabel =
                new JLabel("View, search and monitor all project tasks.");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 225, 240));

        header.add(brandLabel);
        header.add(titleLabel);
        header.add(subtitleLabel);

        // TOOLBAR
        JPanel toolbar = new JPanel(new BorderLayout(12, 0));
        toolbar.setBackground(background);
        toolbar.setBorder(new EmptyBorder(20, 25, 15, 25));

        JLabel searchLabel = new JLabel("Search tasks:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        searchLabel.setForeground(textColor);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(280, 38));
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
        refreshButton.addActionListener(e -> loadTasks());

        toolbar.add(searchPanel, BorderLayout.CENTER);
        toolbar.add(refreshButton, BorderLayout.EAST);

        // TABLE MODEL
        String[] columns = {
                "Task ID",
                "Project ID",
                "Assigned To",
                "Task Name",
                "Description",
                "Deadline",
                "Priority",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        taskTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        taskTable.setRowHeight(38);
        taskTable.setForeground(textColor);
        taskTable.setBackground(Color.WHITE);
        taskTable.setSelectionBackground(new Color(225, 233, 255));
        taskTable.setSelectionForeground(navy);
        taskTable.setGridColor(new Color(232, 236, 244));
        taskTable.setShowVerticalLines(false);
        taskTable.setShowHorizontalLines(true);
        taskTable.setFillsViewportHeight(true);
        taskTable.setAutoCreateRowSorter(true);
        taskTable.setIntercellSpacing(new Dimension(0, 1));

        // NAVY TABLE HEADER
        JTableHeader tableHeader = taskTable.getTableHeader();
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
        headerRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));

        for (int i = 0; i < taskTable.getColumnCount(); i++) {
            taskTable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // COLUMN WIDTHS
        int[] widths = {75, 85, 100, 210, 300, 110, 100, 130};

        for (int i = 0; i < widths.length; i++) {
            taskTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
        }

        // CELL PADDING
        DefaultTableCellRenderer cellRenderer =
                new DefaultTableCellRenderer();

        cellRenderer.setForeground(textColor);
        cellRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));

        for (int i = 0; i < taskTable.getColumnCount(); i++) {
            taskTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(225, 230, 240)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(0, 25, 25, 25));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // SEARCH FILTER
        sorter = new TableRowSorter<>(tableModel);
        taskTable.setRowSorter(sorter);

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void filterTasks() {
                        String query = searchField.getText().trim();

                        if (query.isEmpty()) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(
                                    RowFilter.regexFilter(
                                            "(?i)" + Pattern.quote(query)
                                    )
                            );
                        }
                    }

                    @Override
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filterTasks();
                    }

                    @Override
                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filterTasks();
                    }

                    @Override
                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filterTasks();
                    }
                }
        );

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(background);
        contentPanel.add(toolbar, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        loadTasks();
    }

    private void loadTasks() {

        tableModel.setRowCount(0);

        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getAllTasks();

        if (tasks == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load tasks. Please check the database connection.",
                    "Task Loading Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        for (Task task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getTaskId(),
                    task.getProjectId(),
                    task.getAssignedTo(),
                    task.getTaskName(),
                    task.getDescription(),
                    task.getDeadline(),
                    task.getPriority(),
                    task.getStatus()
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