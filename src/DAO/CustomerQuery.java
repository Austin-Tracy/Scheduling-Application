/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.JDBC;
import java.util.ArrayList;
import model.Customer;
import java.sql.*;

/**
 *
 * This Class will handle all of the Create, Read, Update and Delete
 * implementations for the Customer Table
 *
 * @author austi
 */
public class CustomerQuery {

    /**
     * Initializing and Empty Customer Object Instance
     */
    public Customer cust = new Customer(0, "", "", "", "", "", "");

    /**
     * This Query returns all Customers Data to populate Customer TableView
     *
     * @return An ArrayList of Customers
     * @throws SQLException When the Query is Invalid
     */
    public static ArrayList<Customer> getCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        String sql2 = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        String sql3 = "SELECT Country from countries WHERE Country_ID=?";
        try (PreparedStatement st = JDBC.connection.prepareStatement(sql);
             PreparedStatement st2 = JDBC.connection.prepareStatement(sql2);
             PreparedStatement st3 = JDBC.connection.prepareStatement(sql3)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(rs.getInt("Customer_ID"));
                cust.setCustomerName(rs.getString("Customer_Name"));
                cust.setCustomerAddress(rs.getString("Address"));
                cust.setCustomerPostalCode(rs.getString("Postal_Code"));
                cust.setCustomerPhone(rs.getString("Phone"));
                cust.setCustomerDivision(rs.getString("Division_ID"));
                st2.setInt(1, rs.getInt("Division_ID"));
                ResultSet rs2 = st2.executeQuery();
                if (rs2.next()) {
                    cust.setCustomerDivision(rs2.getString("Division"));
                    st3.setInt(1, rs2.getInt("Country_ID"));
                    ResultSet rs3 = st3.executeQuery();
                    if (rs3.next()) {
                        cust.setCustomerCountry(rs3.getString("Country"));
                    }
                }
                customers.add(cust);
            }
        }
        return customers;
    }

    /**
     * Select Query before DML Insert operation to create a new Customer
     *
     * @param cust Customer to insert into the DB
     * @return Boolean of success or fail
     * @throws SQLException When the Query is Invalid
     */
    public static boolean insertCustomer(Customer cust) throws SQLException {
        String sql1 = "SELECT * FROM first_level_divisions WHERE Division=?";
        String sql2 = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?)";
        try (PreparedStatement st1 = JDBC.connection.prepareStatement(sql1);
             PreparedStatement st2 = JDBC.connection.prepareStatement(sql2)) {
            st1.setString(1, cust.getCustomerDivision());
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                st2.setString(1, cust.getCustomerName());
                st2.setString(2, cust.getCustomerAddress());
                st2.setString(3, cust.getCustomerPostalCode());
                st2.setString(4, cust.getCustomerPhone());
                st2.setInt(5, divisionId);
                int success = st2.executeUpdate();
                return success > 0;
            }
        }
        return false;
    }

    /**
     * Select Query to associated Division with Division_ID before performing a
     * DML operation to update the Customer record
     *
     * @param cust Customer to Updated
     * @return Boolean of success or fail
     * @throws SQLException When the Query is Invalid
     */
    public static boolean updateCustomer(Customer cust) throws SQLException {
        String sql1 = "SELECT * FROM first_level_divisions WHERE Division=?";
        String sql2 = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
        try (PreparedStatement st1 = JDBC.connection.prepareStatement(sql1);
             PreparedStatement st2 = JDBC.connection.prepareStatement(sql2)) {
            st1.setString(1, cust.getCustomerDivision());
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                st2.setString(1, cust.getCustomerName());
                st2.setString(2, cust.getCustomerAddress());
                st2.setString(3, cust.getCustomerPostalCode());
                st2.setString(4, cust.getCustomerPhone());
                st2.setInt(5, divisionId);
                st2.setInt(6, cust.getCustomerId());
                int success = st2.executeUpdate();
                return success > 0;
            }
        }
        return false;
    }

    /**
     * Select Query to determine if the chosen customer is currently associated
     * with any appointments
     *
     * @param cust to use for comparison
     * @return boolean of whether or not customer is associated with
     * appointments
     * @throws SQLException When the Query is Invalid
     */
    public static boolean checkCustomer(Customer cust) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID=?";
        try (PreparedStatement st = JDBC.connection.prepareStatement(sql)) {
            st.setInt(1, cust.getCustomerId());
            ResultSet rs = st.executeQuery();
            return rs.next();
        }
    }

    /**
     * This DML Operation deletes the chosen Customer from the DB
     *
     * @param cust to be deleted
     * @return boolean of successful delete operation
     * @throws SQLException When the Query is Invalid
     */
    public static boolean deleteCustomer(Customer cust) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID=?";
        try (PreparedStatement st = JDBC.connection.prepareStatement(sql)) {
            st.setInt(1, cust.getCustomerId());
            int success = st.executeUpdate();
            return success > 0;
        }
    }

    /**
     * Select Query used to search for a customer with a 1 to 1 ID Match
     *
     * @param custId Customer_ID to search against customers Primary Key
     * @return the Customer information if matches otherwise null
     * @throws SQLException When the Query is invalid
     */
    public static Customer lookupCustomer(int custId) throws SQLException {
        String sql1 = "SELECT * FROM customers WHERE Customer_ID=?";
        String sql2 = "SELECT * FROM first_level_divisions WHERE Division_ID=?";
        try (PreparedStatement st1 = JDBC.connection.prepareStatement(sql1);
             PreparedStatement st2 = JDBC.connection.prepareStatement(sql2)) {
            st1.setInt(1, custId);
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(rs.getInt("Customer_ID"));
                cust.setCustomerName(rs.getString("Customer_Name"));
                cust.setCustomerAddress(rs.getString("Address"));
                cust.setCustomerPostalCode(rs.getString("Postal_Code"));
                cust.setCustomerPhone(rs.getString("Phone"));
                cust.setCustomerDivision(rs.getString("Division_ID"));
                st2.setInt(1, rs.getInt("Division_ID"));
                ResultSet rs2 = st2.executeQuery();
                if (rs2.next()) {
                    cust.setCustomerDivision(rs2.getString("Division"));
                }
                return cust;
            }
        }
        return null;
    }
}
