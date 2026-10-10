import service.TaskService;

public class TaskServiceTest {

    public static void main(String[] args) {

        TaskService taskService = new TaskService();

        taskService.createTask();
        taskService.updateTaskStatus();
        taskService.deleteTask();
    }
}