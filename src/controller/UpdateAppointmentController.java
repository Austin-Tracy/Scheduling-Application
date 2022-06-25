/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import DAO.AppointmentQuery;
import static DAO.AppointmentQuery.*;
import static DAO.ContactQuery.getContactNames;
import Utils.DateTimeParser;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

/**
 * FXML Controller class that handles all interaction with the UI, this class
 * calls the DAO layer as well as interactions with the FXML View
 *
 * @author Austin Tracy
 */
public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    /**
     * Public Variable to store the Users Default zone ID
     */
    public ZoneId zoneId = ZoneId.systemDefault();
    /**
     * Initializing an empty Appointment Object
     */
    public Appointment newAppointment = new Appointment(0, "", "", "", "", "", "", 0, 0, 0, "");
    @FXML
    private TableColumn<Appointment, Integer> updateAppointmentID;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentContactID;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentTitle;
    @FXML
    private TableColumn<Appointment, Integer> updateAppointmentUserID;
    @FXML
    private TableColumn<Appointment, Integer> updateAppointmentCustomerID;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentDescription;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentStart;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentEnd;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentLocation;
    @FXML
    private TableColumn<Appointment, String> updateAppointmentType;
    @FXML
    private TextField appointmentID;
    @FXML
    private TextField appointmentTitle;
    @FXML
    private TextField appointmentDescription;
    @FXML
    private TextField appointmentLocation;
    @FXML
    private TextField appointmentType;
    @FXML
    private TextField appointmentCustomer;
    @FXML
    private TextField appointmentUser;
    @FXML
    private ComboBox<Object> appointmentContact;
    @FXML
    private DatePicker appointmentDate;
    @FXML
    private Button deleteAppointment;
    @FXML
    private Button addAppointment;
    @FXML
    private TextField appointmentSearch;
    @FXML
    private Label appointmentSearchError;
    @FXML
    private Button saveAppointment;
    @FXML
    private Button appointmentClearForm;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private Label appointmentButtonError;
    @FXML
    private ComboBox<String> startHour;
    @FXML
    private ComboBox<String> startMinute;
    @FXML
    private ComboBox<String> endHour;
    @FXML
    private ComboBox<String> endMinute;
    @FXML
    private TextField appointmentStartDateTime;
    @FXML
    private TextField appointmentEndDateTime;
    @FXML
    private Button appointmentSave;
    @FXML
    private RadioButton viewByWeekRadioButton;
    @FXML
    private ToggleGroup viewToggle;
    @FXML
    private RadioButton viewByMonthRadioButton;
    @FXML
    private RadioButton viewAllRadioButton;
    @FXML
    private Button backToHub;

    /**
     * Initializes the controller class and loads the appointments into the
     * TableView, also uses lambda expressions to add eventListeners to handle
     * input validation
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        appointmentTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                appointmentTitle.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        appointmentDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                appointmentDescription.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        appointmentType.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                appointmentType.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        appointmentLocation.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sA-Za-z0-9+")) {
                appointmentLocation.setText(newValue.replaceAll("[^\\sA-Za-z0-9]", ""));
            }
        });

        appointmentUser.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("0-9+")) {
                appointmentUser.setText(newValue.replaceAll("[^0-9]", ""));
            }
        });

        appointmentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("0-9+")) {
                appointmentSearch.setText(newValue.replaceAll("[^0-9]", ""));
            }
        });

        appointmentCustomer.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("0-9+")) {
                appointmentCustomer.setText(newValue.replaceAll("[^0-9]", ""));
            }
        });

        try {
            refreshAppointmentTable();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList contactNames = getContactNames();
        for (int i = 0; i < contactNames.size(); i++) {
            appointmentContact.getItems().add(contactNames.get(i));
        }

        businessHours();

        startMinute.getItems()
                .addAll("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
        endMinute.getItems()
                .addAll("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");

        updateAppointmentID.setCellValueFactory(
                new PropertyValueFactory<>(
                        "appointmentId"));
        updateAppointmentContactID.setCellValueFactory(
                new PropertyValueFactory<>(
                        "contactName"));
        updateAppointmentTitle.setCellValueFactory(
                new PropertyValueFactory<>(
                        "title"));
        updateAppointmentUserID.setCellValueFactory(
                new PropertyValueFactory<>(
                        "userId"));
        updateAppointmentCustomerID.setCellValueFactory(
                new PropertyValueFactory<>(
                        "customerId"));
        updateAppointmentDescription.setCellValueFactory(
                new PropertyValueFactory<>(
                        "description"));
        updateAppointmentStart.setCellValueFactory(
                new PropertyValueFactory<>(
                        "start"));
        updateAppointmentEnd.setCellValueFactory(
                new PropertyValueFactory<>(
                        "end"));
        updateAppointmentLocation.setCellValueFactory(
                new PropertyValueFactory<>(
                        "location"));
        updateAppointmentType.setCellValueFactory(
                new PropertyValueFactory<>(
                        "type"));
    }

    /**
     * This method handles the delete action of the TableView Object along with
     * the DB object keeping them in sync
     *
     * @param event onAction Delete Button Event
     * @throws SQLException When the Query is invalid
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        if (appointmentTableView.getSelectionModel().getSelectedItem() == null) {
            appointmentButtonError.setText("No Appointment Selected!");
        } else {
            appointmentButtonError.setText(null);
            Appointment tempApp = appointmentTableView.getSelectionModel().getSelectedItem();
            AppointmentQuery.deleteAppointment(tempApp);
            appointmentButtonError.setText("Appointment ID: " + tempApp.getAppointmentId() + " of Type: " + tempApp.getType() + " has been Deleted");
            viewAllRadioButton.setSelected(true);
            refreshAppointmentTable();
        }
    }

    /**
     * This Method handles Adding a New Appointment into the View and DB
     *
     * @param event onAction New Button Event
     * @throws SQLException When the query is invalid
     * @throws ParseException When the TimeFormat Parsing returns invalid
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws SQLException, ParseException {
        if (!appointmentID.getText().isEmpty()) {
            appointmentButtonError.setText("No Duplicate Customers Allowed, Please Clear Form!");
        } else {
            if (appointmentTitle.getText() == null
                    || appointmentDescription.getText() == null
                    || appointmentLocation.getText() == null
                    || appointmentType.getText() == null
                    || appointmentContact.getValue() == null
                    || appointmentDate.getValue() == null
                    || startHour.getValue() == null
                    || startMinute.getValue() == null
                    || endHour.getValue() == null
                    || endMinute.getValue() == null
                    || appointmentCustomer.getText() == null
                    || appointmentUser.getText() == null) {
                appointmentButtonError.setText("Null Values Not Allowed!");
            } else {
                appointmentButtonError.setText(null);
                Appointment newApp = new Appointment();
                newApp.setTitle(appointmentTitle.getText());
                newApp.setDescription(appointmentDescription.getText());
                newApp.setLocation(appointmentLocation.getText());
                newApp.setType(appointmentType.getText());
                newApp.setContactName(appointmentContact.getValue().toString());
                newApp.setCustomerId(Integer.parseInt(appointmentCustomer.getText()));
                newApp.setUserId(Integer.parseInt(appointmentUser.getText()));
                updateDateTimeFields();
                newApp.setStart(appointmentStartDateTime.getText().replace("T", " ").substring(0, 16) + ":00");
                newApp.setEnd(appointmentEndDateTime.getText().replace("T", " ").substring(0, 16) + ":00");
                ArrayList<Appointment> isOverLapping = AppointmentQuery.overLapping(newApp.getStart(), newApp.getEnd(), newApp.getCustomerId());
                if (isOverLapping.size() > 0) {
                    appointmentButtonError.setText("Customer Appointment Times cannot OverLap, Please Try a different Time or Customer!");
                } else {
                    AppointmentQuery.insertAppointment(newApp);
                    viewAllRadioButton.setSelected(true);
                    refreshAppointmentTable();
                    clearForm();

                }
            }
        }
    }

    /**
     * This method passes the information into the Form Fields where they can be
     * modified
     *
     * @param app The Appointment Object passed from TableView to Form fields
     * @throws java.text.ParseException when DateTime parsing fails
     */
    public void passAppointmentInfo(Appointment app) throws ParseException {
        appointmentID.setText(String.valueOf(app.getAppointmentId()));
        appointmentTitle.setText(String.valueOf(app.getTitle()));
        appointmentDescription.setText(String.valueOf(app.getDescription()));
        appointmentLocation.setText(String.valueOf(app.getLocation()));
        appointmentType.setText(String.valueOf(app.getType()));
        appointmentCustomer.setText(String.valueOf(app.getCustomerId()));
        appointmentUser.setText(String.valueOf(app.getUserId()));
        appointmentStartDateTime.setText(String.valueOf(app.getStart()));
        appointmentEndDateTime.setText(String.valueOf(app.getEnd()));
        appointmentDate.setValue(LocalDate.parse(appointmentStartDateTime.getText().substring(0, 10), DateTimeFormatter.ISO_DATE));
        startHour.setValue(appointmentStartDateTime.getText().substring(11, 13));
        startMinute.setValue(appointmentStartDateTime.getText().substring(14, 16));
        endHour.setValue(appointmentEndDateTime.getText().substring(11, 13));
        endMinute.setValue(appointmentEndDateTime.getText().substring(14, 16));
        appointmentContact.setValue(app.getContactName());
    }

    /**
     * The onAction Save Event that saves the modified form fields and saves the
     * updated appointment to the View and DB
     *
     * @param event onAction Save Updates Event
     * @throws ParseException When there is an error parsing the Simple Date
     * Format
     */
    @FXML
    void onActionSaveModAppointment(ActionEvent event) throws ParseException {
        if (appointmentTableView.getSelectionModel().getSelectedItem() == null) {
            appointmentButtonError.setText("No Appointment Selected!");
        } else {
            appointmentButtonError.setText(null);
            passAppointmentInfo(appointmentTableView.getSelectionModel().getSelectedItem());

        }
    }

    /**
     * The onAction Event Handler that calls the ClearForm Method
     *
     * @param event onAction clearForm Button Event
     */
    @FXML
    void onActionClearForm(ActionEvent event) {
        clearForm();
    }

    /**
     * This Method polls the Radio Buttons before clearing and adding updated
     * appointment data into the Table View
     *
     * @throws SQLException When the Query is Invalid
     */
    public void refreshAppointmentTable() throws SQLException {
        if (!appointmentTableView.getItems().isEmpty()) {
            appointmentTableView.getItems().clear();
        }
        if (viewByMonthRadioButton.isSelected()) {
            ArrayList<Appointment> appointments = getAppointmentsByWeek();
            for (int i = 0; i < appointments.size(); i++) {
                appointmentTableView.getItems().add(appointments.get(i));
            }
        } else if (viewByWeekRadioButton.isSelected()) {
            ArrayList<Appointment> appointments = getAppointmentsByMonth();
            for (int i = 0; i < appointments.size(); i++) {
                appointmentTableView.getItems().add(appointments.get(i));
            }
        } else if (viewAllRadioButton.isSelected()) {
            ArrayList<Appointment> appointments = getAppointments();
            for (int i = 0; i < appointments.size(); i++) {
                appointmentTableView.getItems().add(appointments.get(i));
            }
        }
    }

    /**
     * The Logic that clears all of the form fields.
     */
    public void clearForm() {
        appointmentID.clear();
        appointmentTitle.clear();
        appointmentDescription.clear();
        appointmentLocation.clear();
        appointmentType.clear();
        appointmentCustomer.clear();
        appointmentUser.clear();
        appointmentContact.setValue(null);
        appointmentContact.setPromptText("Select Contact");
        startHour.setValue(null);
        startHour.setPromptText("Hour");
        startMinute.setValue(null);
        startMinute.setPromptText("Min");
        endHour.setValue(null);
        endHour.setPromptText("Hour");
        endMinute.setValue(null);
        endMinute.setPromptText("Min");
        appointmentDate.setValue(null);
        appointmentStartDateTime.clear();
        appointmentEndDateTime.clear();
    }

    /**
     * The onAction Save Event, that requires all fields to have data entered
     * before saving the updated appointment data
     *
     * @param event onAction Save Button Event
     * @throws SQLException When the Query is Invalid
     * @throws ParseException When there is an error parsing the Simple Date
     * Format
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, ParseException {
        if (appointmentID.getText().isEmpty()) {
            appointmentButtonError.setText("Please Click New to create a New Appointment!");
        } else {
            appointmentButtonError.setText(null);
            if (appointmentTitle.getText() == null
                    || appointmentDescription.getText() == null
                    || appointmentLocation.getText() == null
                    || appointmentType.getText() == null
                    || appointmentContact.getValue() == null
                    || appointmentDate.getValue() == null
                    || startHour.getValue() == null
                    || startMinute.getValue() == null
                    || endHour.getValue() == null
                    || endMinute.getValue() == null
                    || appointmentCustomer.getText() == null
                    || appointmentUser.getText() == null) {
                appointmentButtonError.setText("Null Values Not Allowed!");
            } else {
                appointmentButtonError.setText(null);
                Appointment updateApp = new Appointment();
                updateApp.setTitle(appointmentTitle.getText());
                updateApp.setDescription(appointmentDescription.getText());
                updateApp.setLocation(appointmentLocation.getText());
                updateApp.setType(appointmentType.getText());
                updateApp.setContactName(appointmentContact.getValue().toString());
                updateApp.setCustomerId(Integer.parseInt(appointmentCustomer.getText()));
                updateApp.setUserId(Integer.parseInt(appointmentUser.getText()));
                updateDateTimeFields();
                updateApp.setStart(appointmentStartDateTime.getText().replace("T", " ").substring(0, 16) + ":00");
                updateApp.setEnd(appointmentEndDateTime.getText().replace("T", " ").substring(0, 16) + ":00");
                updateApp.setAppointmentId(Integer.parseInt(appointmentID.getText()));
                ArrayList<Appointment> isOverLapping = AppointmentQuery.overLapping(updateApp.getStart(), updateApp.getEnd(), updateApp.getCustomerId());
                if (isOverLapping.size() > 1) {
                    System.out.print("This Appointment Time is already taken!\n");
                    appointmentButtonError.setText("Customer Appointment Times cannot OverLap, Please Try a different Time or Customer!");
                } else {
                    AppointmentQuery.updateAppointment(updateApp);
                    viewAllRadioButton.setSelected(true);
                    refreshAppointmentTable();
                    clearForm();
                }
            }
        }
    }

    /**
     * This method collects all of the strings from the Date time Fields and
     * combo boxes and replaces text to align in the correct format to pass to
     * the DB
     */
    public void updateDateTimeFields() {
        appointmentStartDateTime.setText("0000-00-00 00:00:00");
        appointmentEndDateTime.setText("0000-00-00 00:00:00");
        String newEndMinute = endMinute.getValue();
        appointmentEndDateTime.replaceText(14, 16, newEndMinute);

        String newEndHour = endHour.getValue();
        appointmentEndDateTime.replaceText(11, 13, newEndHour);

        String newStartMinute = startMinute.getValue();
        appointmentStartDateTime.replaceText(14, 16, newStartMinute);

        String newStartHour = startHour.getValue();
        appointmentStartDateTime.replaceText(11, 13, newStartHour);

        String newDate = appointmentDate.getValue().toString();
        appointmentStartDateTime.replaceText(0, 10, newDate);
        appointmentEndDateTime.replaceText(0, 10, newDate);

        appointmentStartDateTime.setText(DateTimeParser.toUTC(appointmentStartDateTime.getText()));
        appointmentEndDateTime.setText(DateTimeParser.toUTC(appointmentEndDateTime.getText()));
    }

    /**
     * Radio Button option to view only the appointments that begin within the
     * next week
     *
     * @param event onAction Radio Button Select
     * @throws SQLException When the Query is Invalid
     */
    @FXML
    private void onActionViewByWeek(ActionEvent event) throws SQLException {
        if (!appointmentTableView.getItems().isEmpty()) {
            appointmentTableView.getItems().clear();
        }
        ArrayList<Appointment> appointments = getAppointmentsByWeek();
        for (int i = 0; i < appointments.size(); i++) {
            appointmentTableView.getItems().add(appointments.get(i));
        }
    }

    /**
     * Radio Button option to view only the appointments that begin within the
     * next month
     *
     * @param event onAction Radio Button Select
     * @throws SQLException When the Query is Invalid
     */
    @FXML
    private void onActionViewByMonth(ActionEvent event) throws SQLException {
        if (!appointmentTableView.getItems().isEmpty()) {
            appointmentTableView.getItems().clear();
        }
        ArrayList<Appointment> appointments = getAppointmentsByMonth();
        for (int i = 0; i < appointments.size(); i++) {
            appointmentTableView.getItems().add(appointments.get(i));
        }
    }

    /**
     * Radio Button option to view all appointments
     *
     * @param event onAction Radio Button Select
     * @throws SQLException When the Query is Invalid
     */
    @FXML
    private void onActionViewAll(ActionEvent event) throws SQLException {
        if (!appointmentTableView.getItems().isEmpty()) {
            appointmentTableView.getItems().clear();
        }
        ArrayList<Appointment> appointments = getAppointments();
        for (int i = 0; i < appointments.size(); i++) {
            appointmentTableView.getItems().add(appointments.get(i));
        }
    }

    /**
     * TextField Search action that queries the appointment table and returns
     * the appointment based on only ID, must be a 1 to 1 match on the ID and
     * this field only accepts numbers as input
     *
     * @param event onAction Event normally triggered by Enter commit
     * @throws SQLException When the Query is Invalid
     */
    @FXML
    void onActionAppointmentSearch(ActionEvent event) throws SQLException {
        if (appointmentSearch.getText().length() > 0) {
            Appointment app = new Appointment();
            app = AppointmentQuery.lookupAppointment(Integer.parseInt(appointmentSearch.getText()));
            if (app != null) {
                appointmentTableView.getItems().clear();
                appointmentTableView.getItems().add(app);
            } else {
                refreshAppointmentTable();
            }
        } else {
            refreshAppointmentTable();
        }
    }

    /**
     * This Button redirects back to the central Scheduling Hub
     *
     * @param event onAction Button Event
     * @throws IOException When there is invalid Stage or Scene
     */
    @FXML
    void onActionBackToHub(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LandingHub.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Scheduling Hub");
        stage.show();
    }

    /**
     * Switch Statement to Populate the allowed Business Hour Selections
     */
    public void businessHours() {
        if (null != ZoneId.systemDefault().toString()) {
            switch (ZoneId.systemDefault().toString()) {
                case "America/Los_Angeles":
                    startHour.getItems().addAll("05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18");
                    endHour.getItems().addAll("05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19");
                    break;
                case "America/Chicago":
                    startHour.getItems().addAll("07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
                    endHour.getItems().addAll("07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
                    break;
                case "America/New_York":
                    startHour.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21");
                    endHour.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22");
                    break;
                case "America/Denver", "GMT-06:00":
                    startHour.getItems().addAll("06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19");
                    endHour.getItems().addAll("06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
                    break;
                default:
                    break;
            }
            startHour.setVisibleRowCount(5);
            endHour.setVisibleRowCount(5);
            startMinute.setVisibleRowCount(5);
            endMinute.setVisibleRowCount(5);
        }
    }

    /**
     * On Action Event Handler to prevent scheduling an appointment after 10:00
     * PM Eastern Time
     *
     * @param event Any action involving the End Hour ComboBox
     */
    @FXML
    void onActionEndHour(ActionEvent event) {

        if (endHour.getSelectionModel().isSelected(14) == true) {
            endMinute.getSelectionModel().selectFirst();
            endMinute.getSelectionModel().clearAndSelect(0);
            endMinute.getItems().remove(1, 12);
        } else {
            endMinute.getItems().clear();
            endMinute.getItems().addAll("00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55");
            endMinute.setVisibleRowCount(5);
        }
    }

}
