package model;

public class Admin extends User {

    public Admin() {
        super();
        setRole("ADMIN");
    }

    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password, "ADMIN");
    }

    public void manageUsers() {
        System.out.println("Admin is managing users.");
    }

    public void manageProjects() {
        System.out.println("Admin is managing projects.");
    }

    @Override
    public void displayProfile() {
        System.out.println("Admin Profile");
        super.displayProfile();
    }
}