/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

import Utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Method stored in this file
 *
 * @author Austin Tracy
 */
public class Software2 extends Application {

    static int id;
    Stage stage;
    Parent root;

    /**
     * Find User ID to confirm upcoming appointments
     *
     * @return userId
     */
    public int getID() {
        return id;
    }

    /**
     * Main Method
     *
     * @param args Start Application
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

    /**
     * Start JavaFx Stage
     *
     * @param stage FXML View to start
     * @throws Exception On Error
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.show();

    }

}
