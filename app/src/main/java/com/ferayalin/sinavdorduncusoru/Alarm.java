package com.ferayalin.sinavdorduncusoru;

import android.widget.Button;

import java.util.Calendar;

/**
 * Created by Feray on 19.05.2018.
 */

public class Alarm {
    private String adi;
    private int saati;
    private int dakikasi;
    private String tekrarZamani;
    private String uyarıTipi;
    public Button btnSil;

    public Alarm(){}

    public Alarm(String adi, int saati, int dakikasi, String tekrarZamani, String uyarıTipi) {
        this.adi = adi;
        this.saati = saati;
        this.dakikasi = dakikasi;
        this.tekrarZamani = tekrarZamani;
        this.uyarıTipi = uyarıTipi;
    }

    public String getTekrarZamani() {
        return tekrarZamani;
    }

    public void setTekrarZamani(String tekrarZamani) {
        this.tekrarZamani = tekrarZamani;
    }

    public String getUyarıTipi() {
        return uyarıTipi;
    }

    public void setUyarıTipi(String uyarıTipi) {
        this.uyarıTipi = uyarıTipi;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public int getSaati() {
        return saati;
    }

    public void setSaati(int saati) {
        this.saati = saati;
    }

    public int getDakikasi() {
        return dakikasi;
    }

    public void setDakikasi(int dakikasi) {
        this.dakikasi = dakikasi;
    }


    @Override
    public String toString() {
        return "adi='" + adi + '\'' +
                ", saati=" + saati +
                ", dakikasi=" + dakikasi +
                ", tekrarZamani='" + tekrarZamani + '\'' +
                ", uyarıTipi='" + uyarıTipi + '\'';
    }
}
