package com.sourcey.materiallogindemo;

public class AsiTablosuDAO {
    int AsiTablosu_id;
    boolean asiYapildiMi;
    int Bilgiler_id;
    int Child_id;

    public AsiTablosuDAO(int asiTablosu_id, boolean asiYapildiMi, int bilgiler_id, int child_id) {
        AsiTablosu_id = asiTablosu_id;
        this.asiYapildiMi = asiYapildiMi;
        Bilgiler_id = bilgiler_id;
        Child_id = child_id;
    }

    public int getAsiTablosu_id() {
        return AsiTablosu_id;
    }

    public void setAsiTablosu_id(int asiTablosu_id) {
        AsiTablosu_id = asiTablosu_id;
    }

    public boolean isAsiYapildiMi() {
        return asiYapildiMi;
    }

    public void setAsiYapildiMi(boolean asiYapildiMi) {
        this.asiYapildiMi = asiYapildiMi;
    }

    public int getBilgiler_id() {
        return Bilgiler_id;
    }

    public void setBilgiler_id(int bilgiler_id) {
        Bilgiler_id = bilgiler_id;
    }

    public int getChild_id() {
        return Child_id;
    }

    public void setChild_id(int child_id) {
        Child_id = child_id;
    }
}
