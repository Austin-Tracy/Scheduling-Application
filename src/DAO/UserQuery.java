/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.JDBC;
import java.sql.*;
import model.User;

/**
 * This Class will handle all of the Create, Read, Update and Delete
 * implementations for the User Table
 *
 * @author Austin Tracy
 */
public class UserQuery {

    public User usr = new User(0, "", "");

    /**
     * This Query compares the provided username and password against the data
     * stored in the users table to validate the login attempt
     *
     * @param user user object to store userName and password
     * @return Integer that is not 0 if it was a successful login attempt
     * @throws SQLException When the Query is invalid
     */
    public static int validUser(User user) throws SQLException {
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE User_Name='" + user.getUserName() + "' AND Password='" + user.getPassword() + "'");
            if (rs.next()) {
                User usr = new User();
                int usrId = (Integer.parseInt(rs.getString("User_ID")));
                usr.setUserName(rs.getString("User_Name"));
                usr.setPassword(rs.getString("Password"));
                return usrId;
            }
        } catch (SQLException e) {
            return 0;
        }
        return 0;
    }

}
