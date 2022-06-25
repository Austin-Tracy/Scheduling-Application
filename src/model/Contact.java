/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Contact Object Model
 *
 * @author Austin Tracy
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Contact Class Constructor
     *
     * @param contactId Contact_ID INT(10) (PK)
     * @param contactName Contact_Name VARCHAR(50)
     * @param contactEmail Email VARCHAR(50)
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Getter Method for Contact_ID
     *
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter Method for Contact_ID
     *
     * @param contactId The Value to Set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter Method for Contact_Name
     *
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter Method for Contact_Name
     *
     * @param contactName The Value to Set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter Method for Contact Email
     *
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter Method for Contact Email
     *
     * @param contactEmail The Value to Set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
