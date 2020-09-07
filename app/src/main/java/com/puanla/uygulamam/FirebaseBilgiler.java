package com.puanla.uygulamam;

import java.util.ArrayList;

public class FirebaseBilgiler {
    public String isim;
    public String adres_bilgileri;
    public String id;
    public String Puan;

    public FirebaseBilgiler(String puan) {
        Puan = puan;
    }

    public String getPuan() {
        return Puan;
    }

    public void setPuan(String puan) {
        Puan = puan;
    }

    public FirebaseBilgiler(String isim, String adres_bilgileri, String id) {
        this.isim = isim;
        this.adres_bilgileri = adres_bilgileri;
        this.id=id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FirebaseBilgiler(ArrayList<String> puanFB, PuanSayfasi puanSayfasi) {
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getAdres_bilgileri() {
        return adres_bilgileri;
    }

    public void setAdres_bilgileri(String adres_bilgileri) {
        this.adres_bilgileri = adres_bilgileri;
    }


}
