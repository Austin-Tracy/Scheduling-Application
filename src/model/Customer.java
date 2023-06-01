/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Customer Object Model
 *
 * @author Austin Tracy
 */
public class Customer {

    public static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private String customerDivision;
    private String customerCountry;

    /**
     * Customer Class Constructor.
     *
     * @param customerId Customer_ID INT(10) (PK)
     * @param customerName Customer_Name VARCHAR(50)
     * @param customerAddress Address VARCHAR(50)
     * @param customerPostalCode Postal_Code VARCHAR(50)
     * @param customerPhone Phone VARCHAR(50)
     * @param customerDivision Division VARCHAR(50)
     * @param customerCountry Country VARCHAR(50)
     */
    public Customer(int customerId, String customerName, String customerAddress,
            String customerPostalCode, String customerPhone,
            String customerDivision, String customerCountry) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
    }

    /**
     * Empty Customer Object Constructor.
     */
    public Customer() {
    }

    /**
     * Getter Method for Customer_ID
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter Method for Customer_ID
     *
     * @param customerId The Value to Set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter Method for Customer Name
     *
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter Method for Customer Name
     *
     * @param customerName The Value to Set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter Method for Customer Address
     *
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Setter Method for Customer Address
     *
     * @param customerAddress The Value to Set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter Method for Customer Postal Code
     *
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Setter Method for Customer Postal Code
     *
     * @param customerPostalCode The Value to Set
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Getter Method for Customer Phone
     *
     * @return customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Setter Method for Customer Phone
     *
     * @param customerPhone The Value to Set
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Getter Method for Customer Division
     *
     * @return customerDivision
     */
    public String getCustomerDivision() {
        return customerDivision;
    }

    /**
     * Setter Method for Customer Division
     *
     * @param customerDivision The Value to Set
     */
    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    /**
     * Getter Method for Customer Country
     *
     * @return customerCountry
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     * Setter Method for Customer Country
     *
     * @param customerCountry The Value to Set
     */
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

}
