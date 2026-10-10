import service.TaskException;
import service.TaskService;

public class ExceptionTest {

    public static void main(String[] args) {

        TaskService taskService = new TaskService();

        try {

            taskService.validateTask("");

        } catch (TaskException e) {

            System.out.println("Exception handled: " + e.getMessage());
        }

        try {

            taskService.validateTask("Design Dashboard");

        } catch (TaskException e) {

            System.out.println("Exception handled: " + e.getMessage());
        }
    }
}