package com.example.ecake;


public class PaymentHandle {

    private String HolderName,CardNo,MM,CVV;

    public PaymentHandle(String cardNo, String holderName, String CVV, String MM ) {

        this.CardNo = cardNo;
        this.HolderName = holderName;
        this.CVV = CVV;
        this.MM = MM;
    }
    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getMM() {
        return MM;
    }

    public void setMM(String MM) {
        this.MM = MM;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getHolderName() {
        return HolderName;
    }

    public void setHolderName(String holderName) {
        HolderName = holderName;
    }
}
