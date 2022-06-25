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
import java.util.Locale;
import java.util.Optional;
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

    /**
     * Initializes the controller class and determines the system default
     * language to display all text in either French or English
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        Locale localeSettings = Locale.getDefault();
        if (localeSettings.getLanguage() == "fr" || localeSettings.getDisplayLanguage() == "French") {
            loginFormPreferredLanguage.setText("Votre zone actuelle est : " + zoneId.toString());
            loginFormUserName.setPromptText("Nom d'utilisateur");
            loginFormPassword.setPromptText("Mot de passe");
            usernameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            UserLoginLabel.setText("Formulaire de connexion utilisateur");
            loginFormLogin.setText("Connexion");
            isFrench = true;
        } else if (localeSettings.getLanguage() == "en" || localeSettings.getDisplayLanguage() == "English") {
            loginFormPreferredLanguage.setText("You're Current Zone is: " + zoneId.toString());
            loginFormUserName.setPromptText("Username");
            loginFormPassword.setPromptText("Password");
            usernameLabel.setText("Username");
            passwordLabel.setText("Password");
            UserLoginLabel.setText("User Login Form");
            loginFormLogin.setText("Log in");
            isFrench = false;
        }
    }

    /**
     * The onAction method that handles the user validation of username and
     * password, also interacts with the FileWriter Object and
     * login_activity.txt, logging all successful and failed login attempts
     *
     * @param event onAction Log In Button Event
     * @throws IOException When an error happens with File Stream or Stage/Scene
     * redirect
     * @throws SQLException When the Query is invalid/null
     * @throws ParseException When there is an error parsing simple date format
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException, ParseException {
        if (loginFormUserName.getText().isEmpty() || loginFormPassword.getText().isEmpty()) {
            if (isFrench) {
                loginFormError.setText("Le nom d'utilisateur et le mot de passe sont requis !");
            } else {
                loginFormError.setText("UserName and Password are Required!");
            }
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
                    Appointment app;
                    app = AppointmentQuery.upcomingAppointments();
                    if (app != null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Upcoming Appointment!\n" + "Appointment ID: " + app.getAppointmentId() + "\nScheduled DateTime: " + app.getStart());
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "No upcoming Appointments.");
                        Optional<ButtonType> result = alert.showAndWait();
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
                if (isFrench) {
                    loginFormError.setText("Nom d'utilisateur ou mot de passe incorrect, réessayez !");
                } else {
                    loginFormError.setText("Incorrect Username or Password, Try Again!");
                }
                fileWriter = new FileWriter(file, true);
                fileWriter.write("Login Failed with Incorrect Credentials at " + java.time.LocalDateTime.now() + "\n");
                fileWriter.close();
            }
        }
    }
}
