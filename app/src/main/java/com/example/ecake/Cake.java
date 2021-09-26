package com.example.ecake;
public class Cake {
    private String cakeId;
    private String name;
    private String cakeDescription;
    private String cakeQuantity;
    private String cakePrice;
    private String imgUri;


    public Cake() {}

    public Cake(String cakeId, String name, String cakeDescription, String cakeQuantity, String cakePrice, String url) {
        this.cakeId = cakeId;
        this.name = name;
        this.cakeDescription = cakeDescription;
        this.cakeQuantity = cakeQuantity;
        this.cakePrice = cakePrice;
        this.imgUri = url;
    }

    public String getCakeId() {
        return cakeId;
    }

    public String getName() {
        return name;
    }

    public String getCakeDescription() {
        return cakeDescription;
    }

    public String getCakeQuantity() {
        return cakeQuantity;
    }

    public String getCakePrice() {
        return cakePrice;
    }

    public String getImgUri() {
        return imgUri;
    }
}