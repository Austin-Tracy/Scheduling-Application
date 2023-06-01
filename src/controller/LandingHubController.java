/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import DAO.AppointmentQuery;
import static DAO.AppointmentQuery.getAppointmentTypes;
import static DAO.AppointmentQuery.getTypeCount;
import DAO.ContactQuery;
import Utils.JDBC;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 * FXML Controller class that handles all interaction with the UI, also handles
 * DAO interaction for DB Queries
 *
 * @author Austin Tracy
 */
public class LandingHubController implements Initializable {

    Stage stage;
    Parent scene;
    final Tab PieTab = new Tab();
    public File file = new File("login_activity.txt");
    //public ArrayList<Appointment> appointments;
    @FXML
    private Button hubAppointments;
    @FXML
    private Button hubCustomerButton;
    @FXML
    private Button hubExit;
    @FXML
    private Tab report1Tab;
    @FXML
    private Tab report2Tab;
    @FXML
    private TableColumn<Appointment, Integer> report2IDColumn;
    @FXML
    private TableColumn<Appointment, String> report2TitleColumn;
    @FXML
    private TableColumn<Appointment, String> report2TypeColumn;
    @FXML
    private TableColumn<Appointment, String> report2DescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> report2StartDateColumn;
    @FXML
    private TableColumn<Appointment, String> report2EndDateColumn;
    @FXML
    private ComboBox<Object> report2ContactCombo;
    @FXML
    private Tab report3Tab;
    @FXML
    private Button signOut;
    @FXML
    private TableView<Appointment> report2TableView;
    @FXML
    private TableColumn<Appointment, String> report2LocationColumn;
    @FXML
    private ComboBox<Object> report1Type;
    @FXML
    private DatePicker report1Date;
    @FXML
    private Button report1Query;
    @FXML
    private TextField totalCountTextField;
    @FXML
    private Label report1Error;
    @FXML
    private TableColumn<?, ?> report2CustomerIDColumn;

    /**
     * Initializes the Landing Hub Controller Class and populates comboBoxes and
     * the data used in the PieChar
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> types;
        ArrayList<String> contacts;

        try {
            types = getAppointmentTypes();
            setPieTab(report3Tab);
            contacts = ContactQuery.getContactNames();
            for (int i = 0; i < contacts.size(); i++) {
                report2ContactCombo.getItems().add(contacts.get(i));
            }
            for (int i = 0; i < types.size(); i++) {
                report1Type.getItems().add(types.get(i));
            }
        } catch (IOException | SQLException e) {
            Logger.getLogger(LandingHubController.class.getName()).log(Level.SEVERE, null, e);
        }
        report2IDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        report2CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        report2TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        report2DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        report2StartDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        report2EndDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        report2TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        report2LocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    /**
     * This method creates a FileReader Object to write all characters in the
     * login_activity.txt file to a String Array, and assigning the Integer
     * values to populate the PieChar ObservableList
     *
     * @param pieTab tab container to display Pie Chart
     * @throws IOException When there is an Error reading the text file
     */
    public void setPieTab(Tab pieTab) throws IOException {

        ArrayList<String> aList = readLoginActivity(file);

        int totalAttempts = aList.size();
        int occurrences = Collections.frequency(aList, ("Failed"));
        for (int i = 0; i < aList.size(); i++) {
            if (aList.get(i).toString().startsWith("Login Failed")) {
                occurrences++;
            }
        }
        pieTab.setText("Custom Report");
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Successful Attempts", totalAttempts - occurrences),
                        new PieChart.Data("Failed Attempts", occurrences));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Log Activity");
        pieTab.setContent(chart);
    }

    /**
     * This action loads the stage/scene of the appointment view
     *
     * @param event onAction view change Event
     * @throws IOException When there is an IO Stream error
     */
    @FXML
    void onActionAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointments");
        stage.show();
    }

    /**
     * This action loads the stage/scene of the appointment view
     *
     * @param event onAction View Change Event
     * @throws IOException When there is an IO Stream error
     */
    @FXML
    void onActionCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Customers");
        stage.show();
    }

    /**
     * This action closes JDBC connection before terminating the application
     *
     * @param event onAction System Exit
     * @throws IOException When there is an IO Stream error
     */
    @FXML
    private void onActionExit(ActionEvent event) throws SQLException {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Takes the prepopulated combobox items and compares the selected option
     * with current appointments to display all appointments for a specific
     * contact
     *
     * @param event onAction ComboBox Selected Item
     * @throws SQLException When the Query is Invalid
     */
    @FXML
    void onActionReport2ContactCombo(ActionEvent event) throws SQLException {
        if (report2ContactCombo.getSelectionModel().getSelectedItem() != null) {
            ArrayList<Appointment> appointments;
            appointments = AppointmentQuery.getAppointmentsByContact(report2ContactCombo.getValue().toString());
            report2TableView.getItems().clear();
            for (int i = 0; i < appointments.size(); i++) {
                report2TableView.getItems().add(appointments.get(i));
            }
        }
    }

    /**
     * This Event returns to the Log In screen that will prompt the user to log
     * back in
     *
     * @param event onAction Sign Out Button Select
     * @throws IOException When there is a stage/scene error
     */
    @FXML
    void onActionSignOut(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("User Login");
        stage.show();
    }

    /**
     * This method handles the logic of opening the log activity and storing the
     * attemps into a String Array
     *
     * @param tempFile The file to open and read
     * @return ArrayList of all Login Attempts delimeted by new lines
     * @throws IOException when there is an error with IO stream functionality
     */
    public ArrayList<String> readLoginActivity(File tempFile) throws IOException {
        int i;
        String tempText = "";
        ArrayList<String> logAttempts = new ArrayList<>();
        FileReader fileReader = new FileReader(tempFile);
        while ((i = fileReader.read()) != -1) {
            tempText += (char) i;
        }
        String[] logList = tempText.split("\\r?\\n");
        logAttempts.addAll(Arrays.asList(logList));
        fileReader.close();
        return logAttempts;
    }

    /**
     * Validates user selected Type and date to extract month, before querying
     * the count of a presented appointment type that takes place in the course
     * of the chosen month
     *
     * @param event onAction Query Button Event
     * @throws SQLException When the Query is invalid/null
     */
    @FXML
    void onActionReport1Query(ActionEvent event) throws SQLException {
        if (report1Type.getValue() != null && report1Date.getValue() != null) {
            if (report1Type.getValue().toString().length() > 0
                    && report1Date.getValue().toString().length() > 0) {
                report1Error.setText(null);
                totalCountTextField.setText(String.valueOf(getTypeCount(report1Date.getValue().getMonth().toString(), report1Type.getValue().toString())));
            }
        } else {
            report1Error.setText("Null Values not Allowed!");
        }
    }
}
