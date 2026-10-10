package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.table.JTableHeader;

public class AdminUsersFrame extends JFrame {

    private final Color navy = new Color(25, 35, 70);
    private final Color blue = new Color(75, 105, 230);
    private final Color background = new Color(245, 247, 252);
    private final Color muted = new Color(110, 118, 140);

    private JTable userTable;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private JLabel countLabel;
    private JTextField searchField;

    public AdminUsersFrame() {

        setTitle("CodeCrew | User Management");
        setSize(1100, 680);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 22));
        root.setBackground(background);
        root.setBorder(new EmptyBorder(25, 30, 20, 30));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("User Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(navy);

        JLabel subtitle = new JLabel(
                "Manage CodeCrew members, access and account roles."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(muted);

        heading.add(title);
        heading.add(Box.createVerticalStrut(7));
        heading.add(subtitle);

        JButton addButton = new JButton("+ Add User");
        styleButton(addButton, blue);
        addButton.addActionListener(e -> openAddUser());

        header.add(heading, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);

        // USER COUNT CARD
        JPanel countCard = new JPanel(new BorderLayout());
        countCard.setBackground(Color.WHITE);
        countCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(228, 232, 242)),
                new EmptyBorder(17, 20, 17, 20)
        ));

        JLabel countTitle = new JLabel("TOTAL REGISTERED USERS");
        countTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        countTitle.setForeground(muted);

        countLabel = new JLabel("Loading users...");
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        countLabel.setForeground(navy);

        JPanel countContent = new JPanel();
        countContent.setOpaque(false);
        countContent.setLayout(new BoxLayout(
                countContent, BoxLayout.Y_AXIS));

        countContent.add(countTitle);
        countContent.add(Box.createVerticalStrut(8));
        countContent.add(countLabel);

        countCard.add(countContent, BorderLayout.WEST);

        // SEARCH AND REFRESH TOOLBAR
        JPanel toolbar = new JPanel(new BorderLayout(12, 0));
        toolbar.setOpaque(false);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 42));
        searchField.setToolTipText(
                "Search by ID, name, email or role");

        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 221, 233)),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, navy);
        refreshButton.addActionListener(e -> loadUsers());

        toolbar.add(searchField, BorderLayout.CENTER);
        toolbar.add(refreshButton, BorderLayout.EAST);

        // TABLE MODEL
        String[] columns = {
                "ID",
                "Full Name",
                "Email Address",
                "Role"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(model);
        userTable.setRowHeight(46);
        userTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userTable.setForeground(navy);
        userTable.setBackground(Color.WHITE);
        userTable.setSelectionBackground(new Color(220, 232, 255));
        userTable.setSelectionForeground(navy);
        userTable.setShowVerticalLines(false);
        userTable.setShowHorizontalLines(true);
        userTable.setGridColor(new Color(235, 238, 245));
        userTable.setFillsViewportHeight(true);
        userTable.setIntercellSpacing(new Dimension(0, 1));
        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        // TABLE HEADER
       // TABLE HEADER — FIXED COLORS
JTableHeader tableHeader = userTable.getTableHeader();

tableHeader.setFont(
        new Font("SansSerif", Font.BOLD, 14)
);

tableHeader.setOpaque(true);
tableHeader.setBackground(navy);
tableHeader.setForeground(Color.WHITE);
tableHeader.setPreferredSize(new Dimension(0, 46));
tableHeader.setReorderingAllowed(false);
tableHeader.setResizingAllowed(true);

// Force header cells to use the intended colors
DefaultTableCellRenderer headerRenderer =
        new DefaultTableCellRenderer();

headerRenderer.setOpaque(true);
headerRenderer.setBackground(navy);
headerRenderer.setForeground(Color.WHITE);
headerRenderer.setFont(
        new Font("SansSerif", Font.BOLD, 14)
);
headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
headerRenderer.setBorder(
        BorderFactory.createEmptyBorder(0, 12, 0, 12)
);

for (int i = 0; i < userTable.getColumnModel().getColumnCount(); i++) {
    userTable.getColumnModel()
            .getColumn(i)
            .setHeaderRenderer(headerRenderer);
}

tableHeader.repaint();

        // COLUMN WIDTHS
        userTable.getColumnModel().getColumn(0)
                .setPreferredWidth(70);
        userTable.getColumnModel().getColumn(1)
                .setPreferredWidth(230);
        userTable.getColumnModel().getColumn(2)
                .setPreferredWidth(350);
        userTable.getColumnModel().getColumn(3)
                .setPreferredWidth(200);

        // ID RENDERER
        DefaultTableCellRenderer idRenderer =
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean selected, boolean focused,
                            int row, int column) {

                        Component c =
                                super.getTableCellRendererComponent(
                                        table, value, selected,
                                        focused, row, column);

                        setHorizontalAlignment(SwingConstants.CENTER);
                        setBorder(new EmptyBorder(0, 8, 0, 8));

                        if (!selected) {
                            c.setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : new Color(249, 250, 253));
                            setForeground(muted);
                        }

                        return c;
                    }
                };

        userTable.getColumnModel().getColumn(0)
                .setCellRenderer(idRenderer);

        // NAME AND EMAIL RENDERERS
        DefaultTableCellRenderer textRenderer =
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean selected, boolean focused,
                            int row, int column) {

                        Component c =
                                super.getTableCellRendererComponent(
                                        table, value, selected,
                                        focused, row, column);

                        setBorder(new EmptyBorder(0, 12, 0, 12));

                        if (!selected) {
                            c.setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : new Color(249, 250, 253));
                            setForeground(navy);
                        }

                        return c;
                    }
                };

        userTable.getColumnModel().getColumn(1)
                .setCellRenderer(textRenderer);
        userTable.getColumnModel().getColumn(2)
                .setCellRenderer(textRenderer);

        // ROLE RENDERER
        userTable.getColumnModel().getColumn(3)
                .setCellRenderer(new RoleRenderer());

        // SORTING
        sorter = new TableRowSorter<>(model);
        userTable.setRowSorter(sorter);

        // SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(228, 232, 242)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // LIVE SEARCH
        searchField.getDocument().addDocumentListener(
                new DocumentListener() {

                    private void filter() {
                        String query =
                                searchField.getText().trim();

                        if (query.isEmpty()) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(
                                    RowFilter.regexFilter(
                                            "(?i)" +
                                            java.util.regex.Pattern.quote(
                                                    query)));
                        }
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        filter();
                    }
                });

        // TABLE SECTION
        JPanel tableSection = new JPanel(
                new BorderLayout(0, 12));
        tableSection.setOpaque(false);
        tableSection.add(toolbar, BorderLayout.NORTH);
        tableSection.add(scrollPane, BorderLayout.CENTER);

        // CENTER AREA
        JPanel center = new JPanel(
                new BorderLayout(0, 18));
        center.setOpaque(false);
        center.add(countCard, BorderLayout.NORTH);
        center.add(tableSection, BorderLayout.CENTER);

        // FOOTER
        JLabel footer = new JLabel(
                "CodeCrew  |  User Directory  |  Select a row to highlight a user"
        );
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.setForeground(muted);

        // ROOT LAYOUT
        root.add(header, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);

        loadUsers();
    }

    private void styleButton(JButton button, Color color) {

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadUsers() {

        model.setRowCount(0);

        List<User> users = new UserDAO().getAllUsers();

        for (User user : users) {
            model.addRow(new Object[]{
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            });
        }

        countLabel.setText(
                users.size() + (users.size() == 1 ? " user" : " users")
        );
    }

    private void openAddUser() {

        AddUserFrame frame = new AddUserFrame();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loadUsers();
            }
        });

        frame.setVisible(true);
    }

    private class RoleRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean selected, boolean focused,
                int row, int column) {

            JLabel label = (JLabel)
                    super.getTableCellRendererComponent(
                            table, value, selected,
                            focused, row, column);

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            label.setBorder(new EmptyBorder(0, 8, 0, 8));

            if (!selected) {
                label.setBackground(
                        row % 2 == 0
                                ? Color.WHITE
                                : new Color(249, 250, 253));

                String role = value == null
                        ? ""
                        : value.toString();

                if (role.equals("ADMIN")) {
                    label.setForeground(new Color(135, 85, 205));
                } else if (role.equals("PROJECT_MANAGER")) {
                    label.setForeground(new Color(20, 125, 180));
                } else {
                    label.setForeground(new Color(20, 145, 110));
                }
            }

            return label;
        }
    }
}