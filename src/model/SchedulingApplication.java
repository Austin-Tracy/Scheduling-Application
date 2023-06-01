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
public class SchedulingApplication extends Application {

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

/*
GitHub Copilot: To run a JavaFX application in Visual Studio Code, you need to create a launch configuration that specifies the main class of your application and the classpath that includes the JavaFX libraries.

Here are the steps to create a launch configuration for a JavaFX application in Visual Studio Code:

1. Open your JavaFX project in Visual Studio Code.
2. Open the Run view by clicking on the Run icon in the left-hand sidebar.
3. Click on the "create a launch.json file" link at the top of the Run view.
4. Select "Java" as the environment for the launch configuration.
5. In the "Main class" field, enter the fully qualified name of the main class of your JavaFX application.
6. In the "Classpath" field, enter the path to the JavaFX libraries on your system. This can be a relative or absolute path, depending on your project setup.
7. Save the launch configuration by clicking on the "Save" button.

Once you have created the launch configuration, you can run your JavaFX application by clicking on the "Run" button in the Run view. This will compile your application and launch it in a new window.

Note that you may need to set the `--module-path` and `--add-modules` options in the launch configuration to specify the location of the JavaFX modules and the modules that your application requires. The exact values of these options depend on your project setup and the version of Java that you are using.
*/