import dao.ProjectDAO;
import model.Project;

import java.util.List;

public class ProjectDAOTest {

    public static void main(String[] args) {

        ProjectDAO projectDAO = new ProjectDAO();

        List<Project> projects = projectDAO.getAllProjects();

        for (Project project : projects) {

            System.out.println("Project ID: " + project.getProjectId());
            System.out.println("Title: " + project.getTitle());
            System.out.println("Status: " + project.getStatus());
            System.out.println("------------------------");
        }
    }
}