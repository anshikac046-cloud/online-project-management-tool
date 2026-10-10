package model;

import java.time.LocalDate;

public class Task {

    private int taskId;
    private int projectId;
    private int assignedTo;
    private String taskName;
    private String description;
    private LocalDate deadline;
    private String priority;
    private String status;

    public Task() {
    }

    public Task(int taskId, int projectId, int assignedTo,
                String taskName, String description,
                LocalDate deadline, String priority, String status) {

        this.taskId = taskId;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayTask() {
        System.out.println("Task: " + taskName);
        System.out.println("Priority: " + priority);
        System.out.println("Status: " + status);
        System.out.println("Deadline: " + deadline);
    }
}