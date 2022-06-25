/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * User Object Model
 *
 * @author Austin Tracy
 */
public class User {

    public static int userId;
    private String userName;
    private String password;

    /**
     * User Class Constructor
     *
     * @param userId User_ID INT(10)
     * @param userName User_Name VARCHAR(50)
     * @param password Password TEXT
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Empty Class Constructor for User
     */
    public User() {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Getter for User_ID INT(10) (PK)
     *
     * @return User User_ID
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Setter for User_ID INT(10) (PK)
     *
     * @param userId The Value to Set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for User_Name VARCHAR(50)
     *
     * @return User User_Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for User_Name VARCHAR(50)
     *
     * @param userName The Value to Set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for Password TEXT
     *
     * @return User Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for User Password TEXT
     *
     * @param password The Value to Set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
