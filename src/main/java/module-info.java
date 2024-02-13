module com.todolist.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.todolist.todolist to javafx.fxml;
    exports com.todolist.todolist;
}