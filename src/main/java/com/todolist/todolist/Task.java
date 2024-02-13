package com.todolist.todolist;

import java.time.LocalDate;
import java.util.Date;

public class Task {
    private String taskName;
    private LocalDate date;
    private int id;
    private String description;

    public Task(String taskName, LocalDate date, String description, int id) {
        this.taskName = taskName;
        this.date = date;
        this.description = description;
        this.id = id;
    }

    public String getTaskName() {
        return this.taskName;
    }
    public LocalDate getDate() {
        return this.date;
    }
    public int getID() {
        return this.id;
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
    public void setID(int id) {
        this.id = id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
