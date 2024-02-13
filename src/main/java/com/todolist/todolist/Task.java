package com.todolist.todolist;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private String taskName;
    private LocalDate date;
    private boolean isCompleted;
    private String description;

    public Task(String taskName, LocalDate date, String description) {
        this.taskName = taskName;
        this.date = date;
        this.description = description;
        this.isCompleted = false;
    }

    public String getTaskName() {
        return this.taskName;
    }
    public LocalDate getDate() {
        return this.date;
    }
    public boolean getIsCompleted() {
        return this.isCompleted;
    }
    public String getDescription() {
        return this.description;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
