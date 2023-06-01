/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.JDBC;
import java.sql.*;
import java.util.ArrayList;
import model.Customer;
import model.FirstLevelDivision;

/**
 * This Class will handle all of the Create, Read, Update and Delete
 * implementations for the FirstLevelDivision Table
 *
 * @author Austin Tracy
 */
public class FirstLevelDivisionQuery {

    public FirstLevelDivision division = new FirstLevelDivision(0, "", 0, "");

    /**
     * Query to returns all data from first_level_divisions and assigns the date
     * to the respective field of FirstLevelDivision Object
     *
     * @return All FirstLevelDivision Data
     * @throws SQLException When Query Error Occurs
     */
    public static ArrayList<FirstLevelDivision> getFirstLevelDivision() throws SQLException {
        ArrayList<FirstLevelDivision> divisionList = null;
        try {
            divisionList = new ArrayList<>();
            String sql = "SELECT * FROM first_level_divisions";
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                FirstLevelDivision division = new FirstLevelDivision();
                division.setDivisionId(Integer.parseInt(rs.getString("Division_ID")));
                division.setCountryId(Integer.parseInt(rs.getString("Country_ID")));
                division.setDivision(rs.getString("Division"));
                divisionList.add(division);
            }
        } catch (SQLException e) {
            return divisionList;
        }
        return divisionList;
    }

    /**
     * This Query returns an ArrayList of all State/Provinces
     *
     * @return State/Provinces
     */
    public static ArrayList<String> getDivisionList() {
        ArrayList<String> divisionList = new ArrayList<>();
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("Select Division from first_level_divisions");
            while (rs.next()) {
                divisionList.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            return divisionList;
        }
        return divisionList;
    }

    /**
     * This Query returns the Division String with the provided Country_ID using
     * the Foreign Key of Division_ID
     *
     * @param cust the customer object to associated with the Division String
     * @return the String version of the Division_ID
     */
    public static ArrayList<String> getFilteredList(Customer cust) {
        ArrayList<String> filteredList = new ArrayList<>();
        String sql1 = "SELECT Country_ID FROM first_level_divisions WHERE Country = ?";
        String sql2 = "SELECT Division FROM first_level_divisions WHERE Country_ID = ?";
        try {
            PreparedStatement st = JDBC.connection.prepareStatement(sql1);
            st.setString(1, cust.getCustomerCountry());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                PreparedStatement st2 = JDBC.connection.prepareStatement(sql2);
                st2.setInt(1, rs.getInt("Country_ID"));
                ResultSet rs2 = st2.executeQuery();
                while (rs2.next()) {
                    filteredList.add(rs2.getString("Division"));
                }
            }
        } catch (SQLException e) {
            return filteredList;
        }
        return filteredList;
    }
}
