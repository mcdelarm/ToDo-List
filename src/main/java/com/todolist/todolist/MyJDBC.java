package com.todolist.todolist;

import javafx.util.converter.LocalDateStringConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyJDBC {
    public static void  main(String[] args) {

        try {
            List<Task> tasks = new ArrayList<>();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDo-List", "root", "Barcelona1@");

            Statement statement = connection.createStatement();
            Task task = new Task("Homework 1",  LocalDate.of(2024, 2, 16), "Algorithms homework 1 prolog", 2 );
            ResultSet resultSet = statement.executeQuery("select * from tasks");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("date"));
                LocalDate localDate = LocalDate.parse(resultSet.getString("date"));
                System.out.println(localDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
