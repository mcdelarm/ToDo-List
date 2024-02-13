module com.todolist.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.todolist.todolist to javafx.fxml;
    exports com.todolist.todolist;
}