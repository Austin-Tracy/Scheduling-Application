/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * First Level Division Object Model
 *
 * @author Austin Tracy
 */
public class FirstLevelDivision {

    private int divisionId;
    private String division;
    private int countryId;
    private String countryName;

    /**
     * First Level Division Class Constructor
     *
     * @param divisionId The Primary Key for first_level_divisions table
     * @param division The State/Province associated with this db row
     * @param countryId The Foreign Key Country_ID
     * @param countryName The Country_Name related Country
     */
    public FirstLevelDivision(int divisionId, String division, int countryId, String countryName) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Empty Class Constructor for FirseLevelDivision
     */
    public FirstLevelDivision() {
        this.divisionId = 0;
        this.division = "";
        this.countryId = 0;
        this.countryName = "";
    }

    /**
     * Getter Method for Division_ID
     *
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter Method for Division_ID
     *
     * @param divisionId The Value to Set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter Method for Division
     *
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter Method for Division
     *
     * @param division The Value to Set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter Method for Country_ID
     *
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter Method for Country_ID
     *
     * @param countryId The Value to Set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Getter Method for Country_Name
     *
     * @return Country_Name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter Method for Country_Name
     *
     * @param countryName The Value to Set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
