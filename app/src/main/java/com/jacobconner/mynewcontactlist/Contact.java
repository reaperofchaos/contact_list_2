package com.jacobconner.mynewcontactlist;

import java.io.Serializable;

public class Contact implements Serializable {
    private int id;
    private String name;
    private String homePhone;
    private String cellPhone;
    private String email;
    private String address;
    private String city;
    private String state;
    private int zipCode;

    public String getContact() {
        return name + "\n home phone: " + homePhone + "\n cell phone: " + cellPhone + "\n email" + email + "\n address: "
                + address + "\n " + city  + ", " + state + " " + zipCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setId(int id) {
        this.id = id;
    }
}
