package com.sourcey.materiallogindemo;

public class BilgilerDAO {
    int Bilgiler_id;
    String Asi_ismi;
    int Asi_Gunu;
    String Aciklama;

    public BilgilerDAO(int bilgiler_id, String asi_ismi, String aciklama, int asi_Gunu) {
        Bilgiler_id = bilgiler_id;
        Asi_ismi = asi_ismi;
        Aciklama = aciklama;
        Asi_Gunu = asi_Gunu;
    }

    public int getBilgiler_id() {
        return Bilgiler_id;
    }

    public void setBilgiler_id(int bilgiler_id) {
        Bilgiler_id = bilgiler_id;
    }

    public String getAsi_ismi() {
        return Asi_ismi;
    }

    public void setAsi_ismi(String asi_ismi) {
        Asi_ismi = asi_ismi;
    }

    public String getAciklama() {
        return Aciklama;
    }

    public void setAciklama(String aciklama) {
        Aciklama = aciklama;
    }

    public int getAsi_Gunu() {
        return Asi_Gunu;
    }

    public void setAsi_Gunu(int asi_Gunu) {
        Asi_Gunu = asi_Gunu;
    }

}
