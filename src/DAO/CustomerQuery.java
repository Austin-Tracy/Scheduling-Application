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
        ArrayList<Customer> customers = null;
        try {
            customers = new ArrayList<>();
            String sql = "SELECT * FROM customers";
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            Statement st3 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(Integer.parseInt(rs.getString("Customer_ID")));
                cust.setCustomerName(rs.getString("Customer_Name"));
                cust.setCustomerAddress(rs.getString("Address"));
                cust.setCustomerPostalCode(rs.getString("Postal_Code"));
                cust.setCustomerPhone(rs.getString("Phone"));
                cust.setCustomerDivision(rs.getString("Division_ID"));
                //cust.setDivisionId(Integer.parseInt(rs.getString("Division_ID")));
                ResultSet rs2 = st2.executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID=" + rs.getInt("Division_ID"));
                rs2.next();
                cust.setCustomerDivision(rs2.getString("Division"));
                ResultSet rs3 = st3.executeQuery("SELECT Country from countries WHERE Country_ID=" + rs2.getInt("Country_ID"));
                rs3.next();
                cust.setCustomerCountry(rs3.getString("Country"));
                //cust.setCustomerCountry(rs.getString("Country"))
                customers.add(cust);
            }
        } catch (SQLException e) {
            return customers;
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
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM first_level_divisions"
                    + " WHERE Division='" + cust.getCustomerDivision() + "'");
            rs.next();
            int divisionId = rs.getInt("Division_ID");
            int success = st.executeUpdate("INSERT INTO customers(Customer_Name,"
                    + " Address, Postal_Code, Phone, Division_ID)"
                    + " VALUES('" + cust.getCustomerName() + "','"
                    + cust.getCustomerAddress() + "','"
                    + cust.getCustomerPostalCode() + "','"
                    + cust.getCustomerPhone() + "','" + divisionId + "');");
            if (success == 0) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        System.out.print("Insert Succeeded\n");
        return true;
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
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM first_level_divisions "
                    + "WHERE Division='" + cust.getCustomerDivision() + "'");

            rs.next();
            int divisionId = rs.getInt("Division_ID");
            String sql = "UPDATE customers SET Customer_Name='"
                    + cust.getCustomerName() + "',Address='"
                    + cust.getCustomerAddress() + "',Postal_Code='"
                    + cust.getCustomerPostalCode() + "',Phone='"
                    + cust.getCustomerPhone() + "',Division_ID="
                    + divisionId + " WHERE Customer_ID=" + cust.getCustomerId();
            int success = st.executeUpdate(sql);
            if (success == 0) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        System.out.print("Update Succeeded\n");
        return true;
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
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointments WHERE Customer_ID ='" + cust.getCustomerId());
            if (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    /**
     * This DML Operation deletes the chosen Customer from the DB
     *
     * @param cust to be deleted
     * @return boolean of successful delete operation
     * @throws SQLException When the Query is Invalid
     */
    public static boolean deleteCustomer(Customer cust) throws SQLException {
        try {
            Statement st = JDBC.connection.createStatement();
            int returnedRecords = st.executeUpdate("DELETE FROM customers WHERE Customer_ID = " + cust.getCustomerId());
            if (returnedRecords == 0) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        System.out.print("Deletetion Succeeded\n");
        return true;
    }

    /**
     * Select Query used to search for a customer with a 1 to 1 ID Match
     *
     * @param custId Customer_ID to search against customers Primary Key
     * @return the Customer information if matches otherwise null
     * @throws SQLException When the Query is invalid
     */
    public static Customer lookupCustomer(int custId) throws SQLException {
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = " + custId;
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(Integer.parseInt(rs.getString("Customer_ID")));
                cust.setCustomerName(rs.getString("Customer_Name"));
                cust.setCustomerAddress(rs.getString("Address"));
                cust.setCustomerPostalCode(rs.getString("Postal_Code"));
                cust.setCustomerPhone(rs.getString("Phone"));
                cust.setCustomerDivision(rs.getString("Division_ID"));
                ResultSet rs2 = st2.executeQuery("SELECT * FROM first_level_divisions WHERE Division_ID = " + rs.getInt("Division_ID"));
                rs2.next();
                cust.setCustomerDivision(rs2.getString("Division"));
                return cust;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }
}
