package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageUsersFrame extends JFrame {

    private JTable userTable;

    public ManageUsersFrame() {

        setTitle("Manage Users");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel(
                "User Management",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        String[] columns = {
                "User ID",
                "Name",
                "Email",
                "Role"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        userTable = new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(userTable);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadUsers();
    }

    private void loadUsers() {

        UserDAO userDAO = new UserDAO();

        List<User> users =
                userDAO.getAllUsers();

        DefaultTableModel model =
                (DefaultTableModel) userTable.getModel();

        for (User user : users) {

            model.addRow(new Object[]{
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            });
        }
    }
}