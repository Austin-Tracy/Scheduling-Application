/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Abstract Util Class to establish a connection to the MySQL DB using JDBC
 * driver, and closes the connection on Application close
 *
 * @author Austin Tracy
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * Opens the Connection via the JDBC driver
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successful!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Closes the Connection via the JDBC Driver
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Closed!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
