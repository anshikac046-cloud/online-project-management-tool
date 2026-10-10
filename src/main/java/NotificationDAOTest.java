import dao.NotificationDAO;

public class NotificationDAOTest {

    public static void main(String[] args) {

        NotificationDAO notificationDAO = new NotificationDAO();

        // Add a notification for Anshika (user_id = 1)
        notificationDAO.addNotification(
                1,
                "You have a new project task assigned.",
                "UNREAD"
        );

        System.out.println("\n===== NOTIFICATIONS =====");

        notificationDAO.showNotifications(1);
    }
}