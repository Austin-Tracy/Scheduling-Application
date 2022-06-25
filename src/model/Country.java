/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Country Object Model
 *
 * @author Austin Tracy
 */
public class Country {

    private int countryId;
    private String country;

    /**
     * Country Class Object Constructor
     *
     * @param countryId Country_ID INT(10) (PK)
     * @param country Country VARCHAR(50)
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;

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
     * Getter Method for Country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter Method for Country
     *
     * @param country The Value to Set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
