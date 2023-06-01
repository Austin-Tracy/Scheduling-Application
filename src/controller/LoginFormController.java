/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import DAO.AppointmentQuery;
import DAO.UserQuery;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

/**
 * FXML Controller class that handles the interaction with the loginForm view
 *
 * @author Austin Tracy
 */
public class LoginFormController implements Initializable {

    public static int id;
    public Stage stage;
    public Parent scene;
    public FileWriter fileWriter;
    public File file = new File("login_activity.txt");
    public boolean isFrench = false;

    public ZoneId zoneId = ZoneId.systemDefault();
    @FXML
    private PasswordField loginFormPassword;
    @FXML
    private TextField loginFormUserName;
    @FXML
    private Button loginFormLogin;
    @FXML
    private Label loginFormError;
    @FXML
    private Label loginFormPreferredLanguage;
    @FXML
    private Label UserLoginLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;

    // Our ResourceBundle which will be loaded based on the system's default locale.
    private ResourceBundle resourceBundle;

    /**
     * Initializes the controller class and determines the system default
     * language to display all text in either French or English
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        // Get the system's default locale.
        Locale locale = Locale.getDefault();
        // Load the ResourceBundle for this locale.
        this.resourceBundle = ResourceBundle.getBundle("messages", locale);

        // Set all texts based on the ResourceBundle.
        loginFormPreferredLanguage
                .setText(resourceBundle.getString("preferredLanguage.text") + " " + zoneId.toString());
        loginFormUserName.setPromptText(resourceBundle.getString("username.prompt"));
        loginFormPassword.setPromptText(resourceBundle.getString("password.prompt"));
        usernameLabel.setText(resourceBundle.getString("username.label"));
        passwordLabel.setText(resourceBundle.getString("password.label"));
        UserLoginLabel.setText(resourceBundle.getString("userLoginForm.label"));
        loginFormLogin.setText(resourceBundle.getString("login.text"));
    }

    /**
     * The onAction method that handles the user validation of username and
     * password, also interacts with the FileWriter Object and
     * login_activity.txt, logging all successful and failed login attempts
     *
     * @param event onAction Log In Button Event
     * @throws IOException    When an error happens with File Stream or Stage/Scene
     *                        redirect
     * @throws SQLException   When the Query is invalid/null
     * @throws ParseException When there is an error parsing simple date format
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException, ParseException {
        if (loginFormUserName.getText().isEmpty() || loginFormPassword.getText().isEmpty()) {
            loginFormError.setText(resourceBundle.getString("login.error.emptyFields"));
            fileWriter = new FileWriter(file, true);
            fileWriter.write("Login Failed with Null Values at " + java.time.LocalDateTime.now() + "\n");
            fileWriter.close();
        } else {
            User user = new User();
            user.setUserName(loginFormUserName.getText());
            user.setPassword(loginFormPassword.getText());
            int usrId = UserQuery.validUser(user);
            if (usrId != 0) {
                fileWriter = new FileWriter(file, true);
                fileWriter.write("Login Succeeded at " + java.time.LocalDateTime.now() + "\n");
                fileWriter.close();
                try {
                    ArrayList<Appointment> apps = AppointmentQuery.upcomingAppointments();
                    if (apps != null && !apps.isEmpty()) {
                        Appointment app = apps.get(0); // get the first upcoming appointment
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Upcoming Appointment!\n" + "Appointment ID: "
                                + app.getAppointmentId() + "\nScheduled DateTime: " + app.getStart());
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "No upcoming Appointments.");
                        alert.showAndWait();
                    }
                } catch (SQLException | ParseException ex) {
                    Logger.getLogger(LandingHubController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/LandingHub.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Scheduling Hub");
                stage.show();
            } else {
                loginFormError.setText(resourceBundle.getString("login.error.emptyFields"));
                fileWriter = new FileWriter(file, true);
                fileWriter.write("Login Failed with Incorrect Credentials at " + java.time.LocalDateTime.now() + "\n");
                fileWriter.close();
            }
        }
    }
}
