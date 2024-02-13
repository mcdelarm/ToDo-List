package com.todolist.todolist;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class View implements FXComponent{
    private Controller controller;
    public View(Controller controller) {
        this.controller = controller;
    }
    @Override
    public Parent render() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        VBox layout = new VBox();
        layout.setSpacing(20);
        //Title Label
        HBox titleBox = new HBox();
        Label titleLabel = new Label("To Do List");
        titleLabel.setFont(new Font("Arial", 24));
        titleBox.getChildren().add(titleLabel);
        titleBox.setAlignment(Pos.CENTER);

        //Set Task List Headers
        GridPane taskList = new GridPane();
        taskList.getColumnConstraints().add(new ColumnConstraints(50));
        ColumnConstraints column0 = new ColumnConstraints(150);
        column0.setHgrow(Priority.ALWAYS);
        taskList.getColumnConstraints().add(column0);
        ColumnConstraints column1 = new ColumnConstraints(250);
        column1.setHgrow(Priority.ALWAYS);
        taskList.getColumnConstraints().add(column1);
        taskList.getColumnConstraints().add(new ColumnConstraints(150));
        ColumnConstraints column3 = new ColumnConstraints(100);
        column3.setHgrow(Priority.ALWAYS);
        taskList.getColumnConstraints().add(column3);
        taskList.setAlignment(Pos.CENTER);
        Label taskLabel = new Label("Task");
        taskLabel.setUnderline(true);
        taskLabel.setFont(new Font("Arial", 16));
        taskList.add(taskLabel, 1, 0);
        Label descriptionLabel = new Label("Description");
        descriptionLabel.setUnderline(true);
        descriptionLabel.setFont(new Font("Arial", 16));
        taskList.add(descriptionLabel, 2, 0);
        Label dateLabel = new Label("Date");
        dateLabel.setUnderline(true);
        dateLabel.setFont(new Font("Arial", 16));
        taskList.add(dateLabel, 3, 0);
        taskList.setHgap(50);
        taskList.setVgap(10);
        //Add task data
        List<Task> tasks = controller.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            Image img = new Image("edit.png");
            ImageView imgView = new ImageView(img);
            imgView.setPreserveRatio(true);
            imgView.setFitHeight(15);
            imgView.setFitWidth(15);
            Button editButton = new Button("", imgView);
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Stage dialog = new Stage();
                    dialog.initModality(Modality.NONE);
                    VBox dialogBox = new VBox();
                    //New Task Title
                    HBox newTaskTitle = new HBox();
                    Label newTaskTitleLabel = new Label("Edit Task");
                    newTaskTitleLabel.setFont(new Font("Arial", 18));
                    newTaskTitleLabel.setUnderline(true);
                    newTaskTitle.getChildren().add(newTaskTitleLabel);
                    newTaskTitle.setAlignment(Pos.CENTER);

                    //Create new task form and get Task
                    HBox newTaskForm = new HBox();
                    int index = GridPane.getRowIndex(editButton) - 1;
                    Task task = controller.getTasks().get(index);

                    //Task name field
                    VBox taskName = new VBox();
                    Label taskNameLabel = new Label("Task Name");
                    TextField taskNameTextField = new TextField(task.getTaskName());
                    taskName.getChildren().addAll(taskNameLabel, taskNameTextField);

                    //Task description field
                    VBox taskDescription = new VBox();
                    Label taskDescriptionLabel = new Label("Description");
                    TextField descriptionTextField = new TextField(task.getDescription());
                    taskDescription.getChildren().addAll(taskDescriptionLabel, descriptionTextField);

                    //Task date field
                    VBox taskDate = new VBox();
                    Label taskDateLabel = new Label("Date");
                    DatePicker taskDatePicker = new DatePicker(task.getDate());
                    taskDate.getChildren().addAll(taskDateLabel, taskDatePicker);
                    newTaskForm.getChildren().addAll(taskName, taskDescription, taskDate);
                    newTaskForm.setAlignment(Pos.CENTER);

                    //Cancel and submit buttons
                    HBox buttons = new HBox();
                    Button cancelButton = new Button("Cancel");
                    cancelButton.setAlignment(Pos.BASELINE_LEFT);
                    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            dialog.close();
                        }
                    });
                    Button submitButton = new Button("Save Changes");
                    submitButton.setAlignment(Pos.BASELINE_RIGHT);
                    submitButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            if ((taskNameTextField.getText() == null) || (descriptionTextField.getText() == null) || (taskDatePicker.getValue() == null)) {
                                HBox errorLabelBox = new HBox();
                                errorLabelBox.setAlignment(Pos.CENTER);
                                Label errorLabel = new Label("Please enter task information");
                                errorLabelBox.getChildren().add(errorLabel);
                                dialogBox.getChildren().add(errorLabelBox);
                            }
                            else {
                                String taskName = taskNameTextField.getText();
                                String taskDescription = descriptionTextField.getText();
                                LocalDate taskDate = taskDatePicker.getValue();
                                controller.handleSaveChangesClick(task, taskName, taskDescription, taskDate);
                                dialog.close();
                            }
                        }
                    });

                    buttons.getChildren().addAll(cancelButton, submitButton);
                    buttons.setAlignment(Pos.CENTER);

                    //Add all elements to layout and show stage
                    dialogBox.getChildren().addAll(newTaskTitle, newTaskForm, buttons);
                    dialogBox.setSpacing(10);
                    Scene dialogScene = new Scene(dialogBox);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
            });
            taskList.add(editButton, 0, i + 1);
            Label taskName = new Label(tasks.get(i).getTaskName());
            taskList.add(taskName, 1, i + 1);
            Label taskDescription = new Label(tasks.get(i).getDescription());
            taskList.add(taskDescription, 2, i + 1);
            Label taskDate = new Label(tasks.get(i).getDate().format(formatter));
            taskList.add(taskDate, 3, i + 1);
            img = new Image("complete.png");
            imgView = new ImageView(img);
            imgView.setPreserveRatio(true);
            imgView.setFitHeight(20);
            imgView.setFitWidth(20);
            Button completeButton = new Button("Complete", imgView);
            completeButton.setAlignment(Pos.BASELINE_RIGHT);
            completeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int index = GridPane.getRowIndex(completeButton) - 1;
                    controller.handleCompleteClick(tasks.get(index));
                }
            });
            taskList.add(completeButton, 4, i + 1);
            GridPane.setHalignment(completeButton, HPos.RIGHT);
        }

        //New Task Button
        HBox newTaskBox = new HBox();
        Image img = new Image("new-task.png");
        ImageView imgView = new ImageView(img);
        imgView.setPreserveRatio(true);
        imgView.setFitWidth(20);
        imgView.setFitHeight(20);
        Button newTask = new Button("New Task", imgView);
        newTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage dialog = new Stage();
                dialog.initModality(Modality.NONE);
                VBox dialogBox = new VBox();
                //New Task Title
                HBox newTaskTitle = new HBox();
                Label newTaskTitleLabel = new Label("New Task");
                newTaskTitleLabel.setFont(new Font("Arial", 18));
                newTaskTitleLabel.setUnderline(true);
                newTaskTitle.getChildren().add(newTaskTitleLabel);
                newTaskTitle.setAlignment(Pos.CENTER);

                //Create new task form
                HBox newTaskForm = new HBox();

                //Task name field
                VBox taskName = new VBox();
                Label taskNameLabel = new Label("Task Name");
                TextField taskNameTextField = new TextField();
                taskNameTextField.setPromptText("Enter task name here:");
                taskName.getChildren().addAll(taskNameLabel, taskNameTextField);

                //Task description field
                VBox taskDescription = new VBox();
                Label taskDescriptionLabel = new Label("Description");
                TextField descriptionTextField = new TextField();
                descriptionTextField.setPromptText("Enter task description here");
                taskDescription.getChildren().addAll(taskDescriptionLabel, descriptionTextField);

                //Task date field
                VBox taskDate = new VBox();
                Label taskDateLabel = new Label("Date");
                DatePicker taskDatePicker = new DatePicker();
                taskDate.getChildren().addAll(taskDateLabel, taskDatePicker);
                newTaskForm.getChildren().addAll(taskName, taskDescription, taskDate);
                newTaskForm.setAlignment(Pos.CENTER);

                //Cancel and submit buttons
                HBox buttons = new HBox();
                Button cancelButton = new Button("Cancel");
                cancelButton.setAlignment(Pos.BASELINE_LEFT);
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        dialog.close();
                    }
                });
                Button submitButton = new Button("Submit");
                submitButton.setAlignment(Pos.BASELINE_RIGHT);
                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if ((taskNameTextField.getText() == null) || (descriptionTextField.getText() == null) || (taskDatePicker.getValue() == null)) {
                            HBox errorLabelBox = new HBox();
                            errorLabelBox.setAlignment(Pos.CENTER);
                            Label errorLabel = new Label("Please enter task information");
                            errorLabelBox.getChildren().add(errorLabel);
                            dialogBox.getChildren().add(errorLabelBox);
                        }
                        else {
                            String taskName = taskNameTextField.getText();
                            String taskDescription = descriptionTextField.getText();
                            LocalDate taskDate = taskDatePicker.getValue();
                            Task task = new Task(taskName, taskDate, taskDescription);
                            controller.handleSubmitClick(task);
                            dialog.close();
                        }
                    }
                });

                buttons.getChildren().addAll(cancelButton, submitButton);
                buttons.setAlignment(Pos.CENTER);

                //Add all elements to layout and show stage
                dialogBox.getChildren().addAll(newTaskTitle, newTaskForm, buttons);
                dialogBox.setSpacing(10);
                Scene dialogScene = new Scene(dialogBox);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
        newTaskBox.getChildren().add(newTask);
        newTaskBox.setAlignment(Pos.BASELINE_RIGHT);

        //Add all elements to layout
        layout.getChildren().addAll(titleBox, taskList, newTaskBox);
        return layout;
    }
}
