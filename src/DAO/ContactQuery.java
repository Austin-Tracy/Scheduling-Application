/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.JDBC;
import java.util.ArrayList;
import java.sql.*;

/**
 * This Class will handle all of the Create, Read, Update and Delete
 * implementations for the Contact Table
 *
 * @author Austin Tracy
 */
public class ContactQuery {

    /**
     * Queries a list of Contact Names to populate Contact Combo Boxes
     *
     * @return An ArrayList of Contact Names
     */
    public static ArrayList<String> getContactNames() {
        ArrayList<String> contactList = new ArrayList<>();
        String sql = "SELECT DISTINCT Contact_Name FROM contacts";
        try (PreparedStatement st = JDBC.connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                contactList.add(rs.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            return contactList;
        }
        return contactList;
    }
}
