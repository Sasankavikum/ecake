package com.example.cake;

public class Admin {
    private String name;
    private String email;
    private String shopName;
    private String phoneNumber;
    private String password;

    public Admin(String name, String email, String shopName, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.shopName = shopName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getShopName() {
        return shopName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
