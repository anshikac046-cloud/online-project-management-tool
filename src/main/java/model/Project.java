package model;

import java.time.LocalDate;

public class Project {

    private int projectId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int managerId;
    private String status;

    public Project() {
    }

    public Project(int projectId, String title, String description,
                   LocalDate startDate, LocalDate endDate,
                   int managerId, String status) {

        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerId = managerId;
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayProject() {
        System.out.println("Project: " + title);
        System.out.println("Status: " + status);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
    }
}