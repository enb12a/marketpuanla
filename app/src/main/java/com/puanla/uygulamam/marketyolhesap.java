package com.puanla.uygulamam;

public class marketyolhesap implements Comparable<marketyolhesap> {

    private String isim;
    private float mesafe;


    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public float getMesafe() {
        return mesafe;
    }

    public void setMesafe(float mesafe) {
        this.mesafe = mesafe;
    }

    public marketyolhesap(String isim, float mesafe) {
        this.isim = isim;
        this.mesafe = mesafe;
    }

    public marketyolhesap() {
    }

    @Override
    public String toString() {
        return this.getIsim() +"     "+ this.getMesafe() + "m";
    }

    @Override
    public int compareTo(marketyolhesap o) {
        return this.mesafe> o.mesafe ? 1 :(this.mesafe< o.mesafe ? -1:0);
    }
}
