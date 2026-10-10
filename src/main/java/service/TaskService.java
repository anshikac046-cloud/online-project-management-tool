package service;

public class TaskService implements TaskOperations {

    @Override
    public void createTask() {
        System.out.println("Task created successfully.");
    }

    @Override
    public void updateTaskStatus() {
        System.out.println("Task status updated successfully.");
    }

    @Override
    public void deleteTask() {
        System.out.println("Task deleted successfully.");
    }

    public void validateTask(String taskName) throws TaskException {

        if (taskName == null || taskName.trim().isEmpty()) {
            throw new TaskException("Task name cannot be empty.");
        }

        System.out.println("Task is valid: " + taskName);
    }
}