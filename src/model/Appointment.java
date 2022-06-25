/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Appointment Object Model
 *
 * @author Austin Tracy
 */
public class Appointment {

    private int appointmentId;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;

    /**
     * Appointment Class Constructor
     *
     * @param appointmentId Appointment_ID INT(10) (PK) (AI)
     * @param title Title VARCHAR(50)
     * @param description Description VARCHAR(50)
     * @param location Location VARCHAR(50)
     * @param type Type VARCHAR(50)
     * @param start Start DATETIME
     * @param end End DATETIME
     * @param customerId Customer_ID INT(10) (FK)
     * @param userId User_ID INT(10) (FK)
     * @param contactId Contact_ID INT(10) (FK)
     * @param contactName Contact_Name VARCHAR(50)
     */
    public Appointment(int appointmentId, String title, String description,
            String location, String type, String start, String end,
            int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Empty Appointment Class Constructor
     */
    public Appointment() {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Setter Method for Appointment_ID INT(10) (PK)
     *
     * @param appointmentId Setting the Appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter for Appointment_ID INT(10) (PK)
     *
     * @return Appointment_ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Setter for Appointment Title VARCHAR(50)
     *
     * @param title The Value to Set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for Appointment Title VARCHAR(50)
     *
     * @return Appointment Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for Appointment Description VARCHAR(50)
     *
     * @param description The Value to Set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for Appointment Description VARCHAR(50)
     *
     * @return Appointment Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter Method for Appointment Location VARCHAR(50)
     *
     * @param location The Value to Set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter Method for Appointment Location VARCHAR(50)
     *
     * @return Appointment Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for Appointment Type VARCHAR(50)
     *
     * @param type The Value to Set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Appointment Type VARCHAR(50)
     *
     * @return Appointment Type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for Appointment Start Time DATETIME
     *
     * @param start The Value to Set
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Getter for Appointment Start Time DATETIME
     *
     * @return Time of Appointment Start
     */
    public String getStart() {
        return start;
    }

    /**
     * Setter for Appointment End Time DATETIME
     *
     * @param end The Value to Set
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Getter for Appointment End Time DATETIME
     *
     * @return Time of Appointment End
     */
    public String getEnd() {
        return end;
    }

    /**
     * Setter for Appointment Foreign Key Customer_ID INT(10)
     *
     * @param customerId The Value to Set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for Appointment Foreign Key Customer_ID INT(10)
     *
     * @return Foreign Key Customer_ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for Appointment Foreign Key User_ID INT(10)
     *
     * @param userId The Value to Set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for Appointment Foreign Key User_ID INT(10)
     *
     * @return Foreign Key User_ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for Appointment Foreign Key Contact_ID INT(10)
     *
     * @param contactId The Value to Set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for Appointment Foreign Key Contact_ID INT(10)
     *
     * @return Foreign Key Contact_ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter for Contact_Name VARCHAR(50)
     *
     * @param contactName The Value to Set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for Contact_Name VARCHAR(50)
     *
     * @return Appointment Contact_Name
     */
    public String getContactName() {
        return contactName;
    }

}
