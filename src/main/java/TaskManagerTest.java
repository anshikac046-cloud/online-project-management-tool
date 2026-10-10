import model.Task;
import service.TaskManager;

import java.time.LocalDate;

public class TaskManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        Task task1 = new Task(
                1,
                1,
                1,
                "Design Dashboard",
                "Create Team Member dashboard",
                LocalDate.of(2026, 10, 20),
                "HIGH",
                "TODO"
        );

        Task task2 = new Task(
                2,
                1,
                3,
                "Database Testing",
                "Test database and JDBC operations",
                LocalDate.of(2026, 10, 18),
                "MEDIUM",
                "TODO"
        );

        manager.addTask(task1);
        manager.addTask(task2);

        System.out.println("Total Tasks: " + manager.getTaskCount());

        for (Task task : manager.getTasks()) {
            System.out.println("Task: " + task.getTaskName());
        }
    }
}