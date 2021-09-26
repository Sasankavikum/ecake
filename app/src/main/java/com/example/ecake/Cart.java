package com.example.ecake;

public class Cart {

    private String cartId;
    private String cakeName;
    private String cakePrice;
    private String cakeImage;
    private String cakeDescription;
    private int cakeQuantity;
    private int totPrice;

    public Cart() {}

    public Cart(String cartId, String cakeName, String cakePrice, String cakeImage, String cakeDescription, int cakeQuantity, int totPrice) {
        this.cartId = cartId;
        this.cakeName = cakeName;
        this.cakePrice = cakePrice;
        this.cakeImage = cakeImage;
        this.cakeDescription = cakeDescription;
        this.cakeQuantity = cakeQuantity;
        this.totPrice = totPrice;
    }

    public String getCartId() {
        return cartId;
    }

    public String getCakeName() {
        return cakeName;
    }

    public String getCakePrice() {
        return cakePrice;
    }

    public String getCakeImage() {
        return cakeImage;
    }

    public String getCakeDescription() {
        return cakeDescription;
    }

    public int getCakeQuantity() {
        return cakeQuantity;
    }

    public int getTotPrice() {
        return totPrice;
    }
}
