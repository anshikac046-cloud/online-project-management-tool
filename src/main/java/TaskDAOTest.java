import dao.TaskDAO;
import model.Task;

import java.util.List;

public class TaskDAOTest {

    public static void main(String[] args) {

        TaskDAO taskDAO = new TaskDAO();

        List<Task> tasks = taskDAO.getAllTasks();

        for (Task task : tasks) {

            System.out.println("Task ID: " + task.getTaskId());
            System.out.println("Task Name: " + task.getTaskName());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Status: " + task.getStatus());
            System.out.println("------------------------");
        }
    }
}