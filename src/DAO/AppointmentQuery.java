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
        String contactQuery = "SELECT * FROM contacts WHERE Contact_Name=?";
        String insertQuery = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstContact = JDBC.connection.prepareStatement(contactQuery);
                PreparedStatement pstInsert = JDBC.connection.prepareStatement(insertQuery)) {
            pstContact.setString(1, app.getContactName());
            ResultSet rs = pstContact.executeQuery();
            rs.next();
            int contactId = rs.getInt("Contact_ID");

            pstInsert.setString(1, app.getTitle());
            pstInsert.setString(2, app.getDescription());
            pstInsert.setString(3, app.getLocation());
            pstInsert.setString(4, app.getType());
            pstInsert.setString(5, app.getStart());
            pstInsert.setString(6, app.getEnd());
            pstInsert.setInt(7, app.getCustomerId());
            pstInsert.setInt(8, app.getUserId());
            pstInsert.setInt(9, contactId);
            int success = pstInsert.executeUpdate();
            return success != 0;
        }
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
        String contactQuery = "SELECT * FROM contacts WHERE Contact_Name=?";
        String updateQuery = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        try (PreparedStatement pstContact = JDBC.connection.prepareStatement(contactQuery);
                PreparedStatement pstUpdate = JDBC.connection.prepareStatement(updateQuery)) {
            pstContact.setString(1, app.getContactName());
            ResultSet rs = pstContact.executeQuery();
            rs.next();
            int contactId = rs.getInt("Contact_ID");

            pstUpdate.setString(1, app.getTitle());
            pstUpdate.setString(2, app.getDescription());
            pstUpdate.setString(3, app.getLocation());
            pstUpdate.setString(4, app.getType());
            pstUpdate.setString(5, app.getStart());
            pstUpdate.setString(6, app.getEnd());
            pstUpdate.setInt(7, app.getCustomerId());
            pstUpdate.setInt(8, app.getUserId());
            pstUpdate.setInt(9, contactId);
            pstUpdate.setInt(10, app.getAppointmentId());
            int success = pstUpdate.executeUpdate();
            return success != 0;
        }
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
                ResultSet rs2 = st2
                        .executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
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
        String deleteQuery = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (PreparedStatement pstDelete = JDBC.connection.prepareStatement(deleteQuery)) {
            pstDelete.setInt(1, app.getAppointmentId());
            int returnedRecords = pstDelete.executeUpdate();
            return returnedRecords != 0;
        }
    }

    /**
     * Query to check for any existing appointments before inserting or updating
     * an appointment
     *
     * @param startTime Appointment Start Time to compare for Overlapping Time
     *                  slots
     * @param endTime   Appointment Start Time to compare for Overlapping Time
     *                  slots
     * @return Int for matching Appointment ID
     * @throws SQLException   When Querying Database
     * @throws ParseException When Parsing DateTime
     */
    public static ArrayList<Appointment> overLapping(String startTime, String endTime, int customerID)
            throws SQLException, ParseException {
        ArrayList<Appointment> appointments = null;
        try {
            appointments = new ArrayList<>();
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT Appointment_ID, Start, End , Customer_ID from appointments Where Customer_ID = "
                            + customerID);
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
    public static ArrayList<Appointment> upcomingAppointments() throws SQLException, ParseException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery("Select * From appointments");
            while (rs.next()) {
                Calendar startTime = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(sdf.parse(
                        DateTimeParser.toLocal(rs.getString("Start")).replace("T", " ").substring(0, 16) + ":00"));
                Calendar newStartTime = Calendar.getInstance();
                newStartTime.add(Calendar.MINUTE, 15);
                if (newStartTime.after(endTime) && startTime.before(endTime)) {
                    Appointment app = new Appointment();
                    app.setAppointmentId(rs.getInt("Appointment_ID"));
                    app.setStart(
                            DateTimeParser.toLocal(rs.getString("Start")).replace("T", " ").substring(0, 16) + "\n");
                    app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
                    appointments.add(app);
                }
            }
        } catch (SQLException | ParseException e) {
            return null;
        }
        return appointments;
    }

    /**
     * Query to return all appointments from Today() through the next week
     *
     * @return All appointments that begin in the next week
     * @throws SQLException When Querying Database
     */
    public static ArrayList<Appointment> getAppointmentsByWeek() throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "Select a.*, c.Contact_Name From appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE Start Between Date_sub(curDate(), interval 1 Day) AND Date_add(curdate(), interval 7 Day);";
        try (Statement st = JDBC.connection.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Appointment app = new Appointment();
                setAppointmentData(app, rs);
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
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "Select a.*, c.Contact_Name From appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE Start Between Date_sub(curDate(), interval 1 Day) AND Date_add(curdate(), interval 30 Day);";
        try (Statement st = JDBC.connection.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Appointment app = new Appointment();
                setAppointmentData(app, rs);
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
                ResultSet rs2 = st2
                        .executeQuery("SELECT Contact_Name from contacts WHERE Contact_ID=" + rs.getInt("Contact_ID"));
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
    public static ArrayList<String> getAppointmentTypes() throws SQLException {
        ArrayList<String> typeList = new ArrayList<>();
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
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.*, c.Contact_Name FROM appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE c.Contact_Name = ?";
        try (PreparedStatement st = JDBC.connection.prepareStatement(sql)) {
            st.setString(1, contactName);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Appointment app = new Appointment();
                    setAppointmentData(app, rs);
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
     * @param type  String Type to Filter List
     * @return The Count of a specified type in a given month period
     * @throws SQLException When a Query is Invalid
     */
    public static int getTypeCount(String month, String type) throws SQLException {
        int count = 0;
        try {
            Statement st = JDBC.connection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM appointments WHERE Type = '" + type + "' AND monthname(Start) = '" + month + "'");
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            return count;
        }
        return count;
    }

    private static void setAppointmentData(Appointment app, ResultSet rs) throws SQLException {
        app.setAppointmentId(rs.getInt("Appointment_ID"));
        app.setContactId(rs.getInt("Contact_ID"));
        app.setTitle(rs.getString("Title"));
        app.setDescription(rs.getString("Description"));
        app.setLocation(rs.getString("Location"));
        app.setType(rs.getString("Type"));
        app.setStart(DateTimeParser.toLocal(rs.getString("Start")));
        app.setEnd(DateTimeParser.toLocal(rs.getString("End")));
        app.setCustomerId(rs.getInt("Customer_ID"));
        app.setUserId(rs.getInt("User_ID"));
        app.setContactName(rs.getString("Contact_Name"));
    }

}
