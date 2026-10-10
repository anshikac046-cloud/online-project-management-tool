import dao.UserDAO;
import model.User;

public class DAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail("anshika@codecrew.com");

if (user != null) {
    System.out.println("Password from database: " + user.getPassword());
}

         else {

            System.out.println("User not found!");
        }
    }
}