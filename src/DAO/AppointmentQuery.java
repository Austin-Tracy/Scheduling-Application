/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.DateTimeParser;
import Utils.JDBC;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import model.Appointment;

/**
 * This Class will handle all of the Create, Read, Update and Delete
 * implementations for the Appointment Table
 *
 * @author Austin Tracy
 */
public class AppointmentQuery {

    public Appointment app = new Appointment(0, "", "", "", "", "", "", 0, 0, 0, "");

    /**
     * Insert Appointment DML Operation
     *
     * @param app Appointment Information
     * @return Boolean of success or fail
     * @throws SQLException When Querying Database
     */
    public static boolean insertAppointment(Appointment app) throws SQLException {
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contacts WHERE Contact_Name='"
                    + app.getContactName() + "'");
            rs.next();
            int contactId = rs.getInt("Contact_ID");
            int success = st.executeUpdate("INSERT INTO appointments(Title, Description,"
                    + " Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                    + "VALUES ('" + app.getTitle() + "', '" + app.getDescription() + "', '"
                    + app.getLocation() + "', '" + app.getType() + "', '"
                    + app.getStart() + "', '" + app.getEnd() + "', " + app.getCustomerId()
                    + ", " + app.getUserId() + ", " + contactId + ");");
            if (success == 0) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Select Query to add Contact_Names to Appointment before DML Update
     * Operation
     *
     * @param app Appointment Information
     * @return Boolean of update successful
     * @throws SQLException When Querying Database
     */
    public static boolean updateAppointment(Appointment app) throws SQLException {
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contacts WHERE Contact_Name='"
                    + app.getContactName() + "'");
            rs.next();
            int contactId = rs.getInt("Contact_ID");
            String sql = "UPDATE appointments SET Title = '" + app.getTitle()
                    + "', Description = '" + app.getDescription()
                    + "', Location = '" + app.getLocation()
                    + "', Type = '" + app.getType()
                    + "', Start = '" + app.getStart()
                    + "', End = '" + app.getEnd()
                    + "', Customer_ID = " + app.getCustomerId()
                    + ", User_ID = " + app.getUserId()
                    + ", Contact_ID = " + contactId
                    + " WHERE Appointment_ID = " + app.getAppointmentId();
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
     * Select Query to return All Appointments and populate TableView
     *
     * @return An ArrayList of Appointments
     * @throws SQLException When Querying Database
     */
    public static ArrayList<Appointment> getAppointments() throws SQLException {
        ArrayList<Appointment> appointments = null;
        try {
            appointments = new ArrayList<>();
            String sql = "SELECT * FROM appointments";
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(Integer.parseInt(rs.getString("Appointment_ID")));
                app.setContactId(rs.getInt("Contact_ID"));
                app.setTitle(rs.getString("Title"));
                app.setDescription(rs.getString("Description"));
                app.setLocation(rs.getString("Location"));
                app.setType(rs.getString("Type"));
                app.setStart(DateTimeParser.toLocal(rs.getString("Start")));
                app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
                app.setCustomerId(rs.getInt("Customer_ID"));
                app.setUserId(rs.getInt("User_ID"));
                ResultSet rs2 = st2.executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
                rs2.next();
                app.setContactName(rs2.getString("Contact_Name"));
                appointments.add(app);
            }
        } catch (SQLException e) {
            return appointments;
        }
        return appointments;
    }

    /**
     * DML Delete Operation to Delete an appointment
     *
     * @param app Appointment Information
     * @return Boolean of whether appointment was deleted
     * @throws SQLException When Querying Database
     */
    public static boolean deleteAppointment(Appointment app) throws SQLException {
        try {
            Statement st = JDBC.connection.createStatement();
            int returnedRecords = st.executeUpdate("DELETE FROM appointments WHERE Appointment_ID = " + app.getAppointmentId());
            if (returnedRecords == 0) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        System.out.print("Appointment Deleted\n");
        return true;
    }

    /**
     * Query to check for any existing appointments before inserting or updating
     * an appointment
     *
     * @param startTime Appointment Start Time to compare for Overlapping Time
     * slots
     * @param endTime Appointment Start Time to compare for Overlapping Time
     * slots
     * @return Int for matching Appointment ID
     * @throws SQLException When Querying Database
     * @throws ParseException When Parsing DateTime
     */
    public static ArrayList<Appointment> overLapping(String startTime, String endTime, int customerID) throws SQLException, ParseException {
        ArrayList<Appointment> appointments = null;
        try {
            appointments = new ArrayList<>();
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT Appointment_ID, Start, End , Customer_ID from appointments Where Customer_ID = " + customerID);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(sdf.parse(startTime));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(sdf.parse(endTime));

            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(rs.getInt("Appointment_ID"));
                Calendar c1 = Calendar.getInstance();
                c1.setTime(sdf.parse(rs.getString("Start")));
                Calendar c2 = Calendar.getInstance();
                c2.setTime(sdf.parse(rs.getString("End")));
                if (calendar1.after(c1) && calendar1.before(c2)
                        || calendar2.after(c1) && calendar2.before(c2)
                        || calendar1.equals(c1) && calendar2.equals(c2)) {
                    appointments.add(app);
                }
            }
        } catch (SQLException | ParseException e) {
            return appointments;
        }
        return appointments;

    }

    /**
     * Query to check Appointments happening within the next 15 minutes
     *
     * @return @throws SQLException When Querying Database
     * @throws ParseException When Parsing DateTime
     */
    public static Appointment upcomingAppointments() throws SQLException, ParseException {
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("Select * From appointments");
            while (rs.next()) {
                Calendar startTime = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(sdf.parse(DateTimeParser.toLocal(rs.getString("Start")).replace("T", " ").substring(0, 16) + ":00"));
                Calendar newStartTime = Calendar.getInstance();
                newStartTime.add(Calendar.MINUTE, 15);
                if (newStartTime.after(endTime) && startTime.before(endTime)) {
                    Appointment app = new Appointment();
                    app.setAppointmentId(rs.getInt("Appointment_ID"));
                    System.out.print(rs.getString("Start") + "\n");
                    app.setStart(DateTimeParser.toLocal(rs.getString("Start")).replace("T", " ").substring(0, 16) + "\n");
                    app.setEnd(DateTimeParser.toLocal(rs.getString("End")));

                    System.out.print(app.getStart());
                    return app;
                }
            }
        } catch (SQLException | ParseException e) {
            return null;
        }
        return null;
    }

    /**
     * Query to return all appointments from Today() through the next week
     *
     * @return All appointments that begin in the next week
     * @throws SQLException When Querying Database
     */
    public static ArrayList<Appointment> getAppointmentsByWeek() throws SQLException {
        ArrayList<Appointment> appointments = null;
        try {
            appointments = new ArrayList<>();
            String sql = "Select * From appointments WHERE Start Between Date_sub(curDate(), interval 1 Day) AND Date_add(curdate(), interval 7 Day);";
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(Integer.parseInt(rs.getString("Appointment_ID")));
                app.setContactId(rs.getInt("Contact_ID"));
                app.setTitle(rs.getString("Title"));
                app.setDescription(rs.getString("Description"));
                app.setLocation(rs.getString("Location"));
                app.setType(rs.getString("Type"));
                app.setStart(DateTimeParser.toLocal(rs.getString("Start")));
                app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
                app.setCustomerId(rs.getInt("Customer_ID"));
                app.setUserId(rs.getInt("User_ID"));
                ResultSet rs2 = st2.executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
                rs2.next();
                app.setContactName(rs2.getString("Contact_Name"));

                appointments.add(app);
            }
        } catch (SQLException e) {
            return appointments;
        }
        return appointments;
    }

    /**
     * Query to return all appointments from Today() through the next month
     *
     * @return All appointments that begin in the next month
     * @throws SQLException When Querying Database
     */
    public static ArrayList<Appointment> getAppointmentsByMonth() throws SQLException {
        ArrayList<Appointment> appointments = null;
        try {
            appointments = new ArrayList<>();
            String sql = "Select * From appointments WHERE Start Between Date_sub(curDate(), interval 1 Day) AND Date_add(curdate(), interval 30 Day);";
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(Integer.parseInt(rs.getString("Appointment_ID")));
                app.setContactId(rs.getInt("Contact_ID"));
                app.setTitle(rs.getString("Title"));
                app.setDescription(rs.getString("Description"));
                app.setLocation(rs.getString("Location"));
                app.setType(rs.getString("Type"));
                app.setStart(DateTimeParser.toLocal(rs.getString("Start")));
                app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
                app.setCustomerId(rs.getInt("Customer_ID"));
                app.setUserId(rs.getInt("User_ID"));
                ResultSet rs2 = st2.executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
                rs2.next();
                app.setContactName(rs2.getString("Contact_Name"));

                appointments.add(app);
            }
        } catch (SQLException e) {
            return appointments;
        }
        return appointments;
    }

    /**
     * Query of appointments based on appointment ID used in the appointment
     * search
     *
     * @param appId Integer of appointment ID to search
     * @return Appointment if the Integer Input matches an appointment
     * @throws SQLException When a Query is Invalid
     */
    public static Appointment lookupAppointment(int appId) throws SQLException {
        try {
            String sql = "Select * From appointments Where Appointment_ID = " + appId;
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(Integer.parseInt(rs.getString("Appointment_ID")));
                app.setContactId(rs.getInt("Contact_ID"));
                app.setTitle(rs.getString("Title"));
                app.setDescription(rs.getString("Description"));
                app.setLocation(rs.getString("Location"));
                app.setType(rs.getString("Type"));
                app.setStart(DateTimeParser.toLocal(rs.getString("Start")));
                app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
                app.setCustomerId(rs.getInt("Customer_ID"));
                app.setUserId(rs.getInt("User_ID"));
                ResultSet rs2 = st2.executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
                rs2.next();
                app.setContactName(rs2.getString("Contact_Name"));
                return app;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    /**
     * Queries for a Distinct List of Types to add to a ComboBox
     *
     * @return Unique List of Types
     * @throws SQLException When the Query is Invalid
     */
    public static ArrayList getAppointmentTypes() throws SQLException {
        ArrayList typeList = new ArrayList();
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("Select DISTINCT Type FROM appointments");
            while (rs.next()) {
                typeList.add(rs.getString("Type"));
            }
        } catch (SQLException e) {
            return typeList;
        }
        return typeList;
    }

    /**
     * Queries appointment table and return a list of appointments associated
     * with contact name provided
     *
     * @param contactName Contact_Name to query against
     * @return An ArrayList of Appointments filtered by Contact Name
     * @throws SQLException When Query is Invalid
     */
    public static ArrayList<Appointment> getAppointmentsByContact(String contactName) throws SQLException {
        ArrayList<Appointment> appointments = null;
        try {
            appointments = new ArrayList<>();
            String sql = "SELECT * FROM appointments";
            Statement st = JDBC.connection.createStatement();
            Statement st2 = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setAppointmentId(Integer.parseInt(rs.getString("Appointment_ID")));
                app.setContactId(rs.getInt("Contact_ID"));
                app.setTitle(rs.getString("Title"));
                app.setDescription(rs.getString("Description"));
                app.setLocation(rs.getString("Location"));
                app.setType(rs.getString("Type"));
                app.setStart(DateTimeParser.toLocal(rs.getString("Start")));
                app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
                app.setCustomerId(rs.getInt("Customer_ID"));
                app.setUserId(rs.getInt("User_ID"));
                ResultSet rs2 = st2.executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
                rs2.next();
                app.setContactName(rs2.getString("Contact_Name"));
                if (app.getContactName() == null ? contactName == null : app.getContactName().equals(contactName)) {
                    appointments.add(app);
                }
            }
        } catch (SQLException e) {
            return appointments;
        }
        return appointments;
    }

    /**
     * Query to return the count of appointments based on user provided Type and
     * Month
     *
     * @param month MonthName
     * @param type String Type to Filter List
     * @return The Count of a specified type in a given month period
     * @throws SQLException When a Query is Invalid
     */
    public static int getTypeCount(String month, String type) throws SQLException {
        int count = 0;
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointments WHERE Type = '" + type + "' AND monthname(Start) = '" + month + "'");
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            return count;
        }
        return count;
    }
}
