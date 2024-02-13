package com.todolist.todolist;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }
    public List<Task> getTasks() {
        return model.getTasks();
    }
    public void handleCompleteClick(Task task) {
        model.removeTask(task);
    }
    public void handleSubmitClick(Task task) {
        model.addTask(task);
    }
    public void handleSaveChangesClick(Task task, String taskName, String taskDescription, LocalDate date) {
        model.updateTask(task, taskName, taskDescription, date);
    }
}
