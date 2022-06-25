/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import static DAO.CountryQuery.getCountries;
import DAO.CustomerQuery;
import static DAO.CustomerQuery.getCustomers;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Customer;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class to manipulate the UI and call the Appointment Query
 * Methods as needed
 *
 * @author Austin Tracy
 */
public class UpdateCustomerController implements Initializable {

    Stage stage;
    Parent scene;
    public Customer newCustomer = new Customer(0, "", "", "", "", "", "");
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;
    @FXML
    private TableColumn<Customer, String> customerPostalCodeColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer, String> customerFirstLevelDivisionColumn;
    @FXML
    private Button customerClearForm;
    @FXML
    private Button newCustomerButton;
    @FXML
    private Button modifyCustomer;
    @FXML
    private Button saveCustomer;
    @FXML
    private Button deleteCustomer;
    @FXML
    private TextField customerSearch;
    @FXML
    private TextField customerIDTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerAddressTextField;
    @FXML
    private TextField customerPostalCodeTextField;
    @FXML
    private TextField customerPhoneNumberTextField;
    @FXML
    private ComboBox<Object> customerCountryCombo;
    @FXML
    private ComboBox<Object> customerStateCombo;
    @FXML
    private Label customerButtonError;
    @FXML
    private Label customerSearchError;
    @FXML
    private Button backToHub;

    /**
     * Initializes the controller class, populates the Table and provide Lambda
     * Expressions for input validation
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                customerNameTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        customerPostalCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("A-Za-z0-9+")) {
                customerPostalCodeTextField.setText(newValue.replaceAll("[^A-Za-z0-9]", ""));
            }
        });

        customerPhoneNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-0-9+")) {
                customerPhoneNumberTextField.setText(newValue.replaceAll("[^-0-9]", ""));
            }
        });

        customerAddressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sA-Za-z0-9+")) {
                customerAddressTextField.setText(newValue.replaceAll("[^\\sA-Za-z0-9]", ""));
            }
        });

        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("0-9+")) {
                customerSearch.setText(newValue.replaceAll("[^0-9]", ""));
            }
        });

        ArrayList countryNames = getCountries();

        for (int i = 0; i < countryNames.size(); i++) {
            customerCountryCombo.getItems().add(countryNames.get(i));
        }

        refreshCustomerTable();

        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerFirstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));

    }

    /**
     * Delete Button Event to Remove Customer From View and DB
     *
     * @param event onAction Button Click
     * @throws SQLException When Error from Query
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            customerButtonError.setText("No Customer has been Selected!");
        } else {
            customerButtonError.setText(null);
            Customer tempCustomer = customerTableView.getSelectionModel().getSelectedItem();
            boolean hasAppointments = CustomerQuery.checkCustomer(tempCustomer);
            if (hasAppointments) {
                customerButtonError.setText("This Customer has Appointments and cannot be Deleted!");
            } else {
                CustomerQuery.deleteCustomer(tempCustomer);
                customerButtonError.setText("Customer " + tempCustomer.getCustomerName() + " has been Deleted!");
            }
            refreshCustomerTable();
        }
    }

    /**
     * Save Button Event to Update an existing Customer Record
     *
     * @param event onAction Button Click
     * @throws SQLException When Error From Query
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException {
        if (customerIDTextField.getText().isEmpty()) {
            customerButtonError.setText("Please Select New Customer for creating Customers!");
        } else {
            if (customerNameTextField.getText() == null
                    || customerAddressTextField.getText() == null
                    || customerPostalCodeTextField.getText() == null
                    || customerPhoneNumberTextField.getText() == null
                    || customerCountryCombo.getValue() == null
                    || customerStateCombo.getValue() == null) {
                customerButtonError.setText("Null Values Not Allowed!");
            } else {
                customerButtonError.setText(null);
                Customer updateCustomer = new Customer();
                updateCustomer.setCustomerId(Integer.parseInt(customerIDTextField.getText()));
                updateCustomer.setCustomerName(customerNameTextField.getText());
                updateCustomer.setCustomerAddress(customerAddressTextField.getText());
                updateCustomer.setCustomerPostalCode(customerPostalCodeTextField.getText());
                updateCustomer.setCustomerPhone(customerPhoneNumberTextField.getText());
                updateCustomer.setCustomerCountry(customerCountryCombo.getValue().toString());
                updateCustomer.setCustomerDivision(customerStateCombo.getValue().toString());

                boolean isUpdated = CustomerQuery.updateCustomer(updateCustomer);
                if (isUpdated) {
                    refreshCustomerTable();
                    clearForm();
                } else {
                    System.out.print("Update Failed\n");
                }
            }
        }
    }

    /**
     * Handles the Event of button selection to clear the form
     *
     * @param event onAction Button Select
     */
    @FXML
    private void onActionClearForm(ActionEvent event
    ) {
        clearForm();
    }

    /**
     * Takes the selected customer and passes the date to the respective form
     * fields for modification
     *
     * @param cust The selected Customer to update
     * @throws SQLException When Query Error Occurs
     */
    public void passCustomerInfo(Customer cust) throws SQLException {
        customerNameTextField.setText(String.valueOf(cust.getCustomerName()));
        customerAddressTextField.setText(String.valueOf(cust.getCustomerAddress()));
        customerIDTextField.setText(String.valueOf(cust.getCustomerId()));
        customerPostalCodeTextField.setText(String.valueOf(cust.getCustomerPostalCode()));
        customerPhoneNumberTextField.setText(String.valueOf(cust.getCustomerPhone()));
        customerCountryCombo.setValue(cust.getCustomerCountry());
        filterDivisions();
        customerStateCombo.setValue(cust.getCustomerDivision());
    }

    /**
     * Clears the form fields
     */
    public void clearForm() {
        customerNameTextField.clear();
        customerAddressTextField.clear();
        customerIDTextField.clear();
        customerPostalCodeTextField.clear();
        customerPhoneNumberTextField.clear();
        customerCountryCombo.setValue(null);
        customerCountryCombo.setPromptText("Select Country");
        customerStateCombo.setValue(null);
        customerStateCombo.setPromptText("Select State");
    }

    /**
     * The onAction event that will save the modified Customer data
     * Textfields/ComboBox and update the View and DB
     *
     * @param event onAction for Save Button Event
     * @throws SQLException When Query Error Occurs
     */
    @FXML
    void onActionNewCustomer(ActionEvent event) throws SQLException {
        if (!customerIDTextField.getText().isEmpty()) {
            customerButtonError.setText("No Duplicate Customers Allowed, Please Clear Form!");
        } else {
            if (customerNameTextField.getText() == null
                    || customerAddressTextField.getText() == null
                    || customerPostalCodeTextField.getText() == null
                    || customerPhoneNumberTextField.getText() == null
                    || customerCountryCombo.getValue() == null
                    || customerStateCombo.getValue() == null) {
                customerButtonError.setText("Null Values Not Allowed!");
            } else {
                customerButtonError.setText(null);
                Customer newCustomer = new Customer();
                newCustomer.setCustomerName(customerNameTextField.getText());
                newCustomer.setCustomerAddress(customerAddressTextField.getText());
                newCustomer.setCustomerPostalCode(customerPostalCodeTextField.getText());
                newCustomer.setCustomerPhone(customerPhoneNumberTextField.getText());
                newCustomer.setCustomerCountry(customerCountryCombo.getValue().toString());
                newCustomer.setCustomerDivision(customerStateCombo.getValue().toString());
                boolean isInserted = CustomerQuery.insertCustomer(newCustomer);
                if (isInserted) {
                    refreshCustomerTable();
                    clearForm();
                } else {
                    System.out.print("Insert Failed\n");
                }
            }
        }
    }

    /**
     * The onAction event that takes the selected customer information and
     * passes it along to the TextFields/ComboBox to change the data
     *
     * @param event onAction Button Select
     * @throws SQLException When Query Error Occurs
     */
    @FXML
    private void onActionModifyCustomer(ActionEvent event) throws SQLException {
        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            customerButtonError.setText("No Customer Selected!");
        } else {
            customerButtonError.setText(null);
            passCustomerInfo(customerTableView.getSelectionModel().getSelectedItem());

        }
    }

    /**
     * Handles the updating of both the View and DB
     */
    public void refreshCustomerTable() {
        if (!customerTableView.getItems().isEmpty()) {
            customerTableView.getItems().clear();
        }
        try {
            ArrayList<Customer> customers = getCustomers();
            for (int i = 0; i < customers.size(); i++) {
                customerTableView.getItems().add(customers.get(i));
            }
        } catch (SQLException e) {
            Logger.getLogger(UpdateCustomerController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    /**
     * This Switch statement handles the Filter Selection for customerStateCombo
     * ComboBox
     */
    public void filterDivisions() {
        customerStateCombo.setPromptText("Select State");
        customerStateCombo.setVisibleRowCount(4);
        if (customerCountryCombo.getValue().toString() != null) {
            if (null != customerCountryCombo.getValue().toString()) {
                switch (customerCountryCombo.getValue().toString()) {
                    case "U.S":
                        customerStateCombo.getItems().clear();
                        customerStateCombo.getItems().addAll("Alabama", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Hawaii", "Alaska");
                        break;
                    case "UK":
                        customerStateCombo.getItems().clear();
                        customerStateCombo.getItems().addAll("England", "Wales", "Scotland", "Northern Ireland");
                        break;
                    case "Canada":
                        customerStateCombo.getItems().clear();
                        customerStateCombo.getItems().addAll("Northwest Territories", "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Nova Scotia", "Prince Edward Island", "Ontario", "Québec", "Saskatchewan", "Nunavut", "Yukon", "Newfoundland and Labrador");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * The onAction event that triggers State/Province Filter
     *
     * @param event onAction ComboBox Select
     */
    @FXML
    private void onActionCountryCombo(ActionEvent event) {
        if (customerCountryCombo.getSelectionModel().getSelectedItem() != null) {
            filterDivisions();
        }
    }

    /**
     * The onAction Event Button that returns to the Landing Page
     *
     * @param event onAction Button Event
     * @throws IOException Exception handler for swapping stages
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
     * Customer Search bar, will only find a perfect 1 to 1 match based on the
     * ID, Lambda expression in the initialize prevents text from being entered
     * into this field.
     *
     * @param event on Action Event defined most commonly as Enter Key
     * @throws SQLException When the Query is invalid
     */
    @FXML
    void onActionCustomerSearch(ActionEvent event) throws SQLException {
        if (customerSearch.getText().length() > 0) {
            Customer cust = new Customer();
            cust = CustomerQuery.lookupCustomer(Integer.parseInt(customerSearch.getText()));
            if (cust != null) {
                customerTableView.getItems().clear();
                customerTableView.getItems().add(cust);
            } else {
                refreshCustomerTable();
            }
        } else {
            refreshCustomerTable();
        }
    }
}
