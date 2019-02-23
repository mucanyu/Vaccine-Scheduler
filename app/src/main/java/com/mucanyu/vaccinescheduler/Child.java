package com.sourcey.materiallogindemo;

import java.io.Serializable;

public class Child implements Serializable {
    private int id;
    private String name;
    private String birthDate;
    private String gender;
    private int parent_id;

    public Child(int id, String name, String birthDate, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public Child(String name, String birthDate, String gender, int parent_id) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.parent_id = parent_id;
    }

    public Child(int id, String name, String birthDate, String gender, int parent_id) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return "Name: " + name + " | birthDate: " + birthDate + "\n" +
                "Gender: " + gender + " | id: " + id;
    }
}
