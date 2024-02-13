package com.todolist.todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Task> tasks;
    private List<ModelObserver> observers;

    public Model() {
        this.tasks = new ArrayList<>();
        this.observers = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDo-List", "root", "Barcelona1@");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tasks");

            while (resultSet.next()) {
                String str = resultSet.getString("date");
                Task task = new Task(resultSet.getString("name"), LocalDate.parse(str), resultSet.getString("description"), resultSet.getInt("id"));
                this.tasks.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Task> getTasks() {
        return this.tasks;
    }
    public void addTask(Task task) {
        this.executeQuery("INSERT INTO tasks " + "Values (" + task.getID() + ", '" + task.getTaskName() + "', '" + task.getDescription() + "', '" + task.getDate().toString() + "')" );
        tasks.add(task);
        for (ModelObserver observer: observers) {
            observer.update(this);
        }
    }
    public void removeTask(Task task) {
        this.executeQuery("DELETE FROM tasks " + "WHERE id = " + task.getID());
        tasks.remove(task);
        for (ModelObserver observer: observers) {
            observer.update(this);
        }
    }
    public void updateTask(Task task, String taskName, String taskDescription, LocalDate date) {
        this.executeQuery("UPDATE tasks " + "SET name = '" + taskName + "', description = '" + taskDescription + "', date = '" + task.getDate().toString() + "' WHERE id = " + task.getID());
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
    private void executeQuery(String query) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDo-List", "root", "Barcelona1@");
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
