/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.JDBC;
import java.sql.*;
import java.util.ArrayList;

/**
 * This Class will handle all of the Create, Read, Update and Delete
 * implementations for the Country Table
 *
 * @author Austin Tracy
 */
public class CountryQuery {

    /**
     * This Query returns an ArrayList of all Countries from the countries table
     *
     * @return List of Country Strings
     */
    public static ArrayList getCountries() {
        ArrayList countryList = new ArrayList();
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT Country FROM countries");
            while (rs.next()) {
                countryList.add(rs.getString("Country"));
            }
        } catch (SQLException e) {
            return countryList;
        }
        return countryList;
    }
}
