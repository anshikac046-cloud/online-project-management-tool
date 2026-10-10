import model.Admin;
import model.ProjectManager;
import model.TeamMember;
import model.User;

public class OOPTest {

    public static void main(String[] args) {

        User admin = new Admin(
                1,
                "Riya Singh",
                "riya@codecrew.com",
                "12345"
        );

        User manager = new ProjectManager(
                4,
                "Anmol Singh Rajput",
                "anmol@codecrew.com",
                "12345"
        );

        User member = new TeamMember(
                1,
                "Anshika Chaudhary",
                "anshika@codecrew.com",
                "12345"
        );

        System.out.println("===== ADMIN =====");
        admin.displayProfile();

        System.out.println("\n===== PROJECT MANAGER =====");
        manager.displayProfile();

        System.out.println("\n===== TEAM MEMBER =====");
        member.displayProfile();
    }
}