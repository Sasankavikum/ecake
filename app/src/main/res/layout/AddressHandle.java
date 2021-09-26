package com.example.cake;


public class AddressHandle {


    private String Names,phonenumCountry, phoneNumber, Street1, Street3, Cities;

    public AddressHandle(String names, String phoneNumber, String street1, String street3, String cities) {
        Names = names;
        this.phoneNumber = phoneNumber;
        Street1 = street1;
        Street3 = street3;
        Cities = cities;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet1() {
        return Street1;
    }

    public void setStreet1(String street1) {
        Street1 = street1;
    }

    public String getStreet3() {
        return Street3;
    }

    public void setStreet3(String street3) {
        Street3 = street3;
    }

    public String getCities() {
        return Cities;
    }

    public void setCities(String cities) {
        Cities = cities;
    }
}
