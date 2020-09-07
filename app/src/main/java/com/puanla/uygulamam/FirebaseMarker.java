package com.puanla.uygulamam;

public class FirebaseMarker {

    public double enlem;
    public double boylam;

    public FirebaseMarker(String adres_bilgileri) {
        this.adres_bilgileri = adres_bilgileri;
    }

    public String isim;
    public  String adres_bilgileri;

    public String getAdres_bilgileri() {
        return adres_bilgileri;
    }

    public void setAdres_bilgileri(String adres_bilgileri) {
        this.adres_bilgileri = adres_bilgileri;
    }

    public FirebaseMarker(double latitude, double longitude){

    }

    public FirebaseMarker(){
        this.boylam=boylam;
        this.isim=isim;
        this.enlem=enlem;
    }


    public double getEnlem() {
        return enlem;
    }

    public void setEnlem(double enlem) {
        this.enlem = enlem;
    }

    public double getBoylam() {
        return boylam;
    }

    public void setBoylam(double boylam) {
        this.boylam = boylam;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
