package com.todolist.todolist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Task> tasks;
    private List<ModelObserver> observers;

    public Model() {
        this.tasks = new ArrayList<>();
        this.observers = new ArrayList<>();
    }
    public List<Task> getTasks() {
        return this.tasks;
    }
    public void addTask(Task task) {
        tasks.add(task);
        for (ModelObserver observer: observers) {
            observer.update(this);
        }
    }
    public void removeTask(Task task) {
        tasks.remove(task);
        for (ModelObserver observer: observers) {
            observer.update(this);
        }
    }
    public void updateTask(Task task, String taskName, String taskDescription, LocalDate date) {
        int index = this.tasks.indexOf(task);
        this.tasks.get(index).setTaskName(taskName);
        this.tasks.get(index).setDescription(taskDescription);
        this.tasks.get(index).setDate(date);
        for (ModelObserver observer : observers) {
            observer.update(this);
        }
    }
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }
}
