package model;

public class TeamMember extends User {

    public TeamMember() {
        super();
        setRole("TEAM_MEMBER");
    }

    public TeamMember(int userId, String name, String email, String password) {
        super(userId, name, email, password, "TEAM_MEMBER");
    }

    public void viewAssignedTasks() {
        System.out.println("Team Member is viewing assigned tasks.");
    }

    public void updateTaskStatus() {
        System.out.println("Team Member is updating task status.");
    }

    public void viewProjectDetails() {
        System.out.println("Team Member is viewing project details.");
    }

    @Override
    public void displayProfile() {
        System.out.println("Team Member Profile");
        super.displayProfile();
    }
}