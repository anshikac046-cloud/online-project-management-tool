package model;

public class ProjectManager extends User {

    public ProjectManager() {
        super();
        setRole("PROJECT_MANAGER");
    }

    public ProjectManager(int userId, String name, String email, String password) {
        super(userId, name, email, password, "PROJECT_MANAGER");
    }

    public void createProject() {
        System.out.println("Project Manager is creating a project.");
    }

    public void assignTask() {
        System.out.println("Project Manager is assigning a task.");
    }

    public void monitorProgress() {
        System.out.println("Project Manager is monitoring project progress.");
    }

    @Override
    public void displayProfile() {
        System.out.println("Project Manager Profile");
        super.displayProfile();
    }
}