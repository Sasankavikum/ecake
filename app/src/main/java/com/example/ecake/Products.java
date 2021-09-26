package com.example.ecake;

public class Products {
    private String price;
    private String QTY;
    private String Name;
    private String image;

    public Products() {
    }

    public Products(String price, String QTY, String name, String image) {
        this.price = price;
        this.QTY = QTY;
        Name = name;
        this.image = image;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
