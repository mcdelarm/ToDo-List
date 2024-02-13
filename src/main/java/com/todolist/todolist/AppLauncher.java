package com.todolist.todolist;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    @Override
    public void start(Stage stage)  {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(controller);
        ModelObserver observer =
                (Model m) -> {
            Scene scene = new Scene(view.render());
            stage.setScene(scene);
            stage.show();
                };
        model.addObserver(observer);
        Scene scene = new Scene(view.render());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}