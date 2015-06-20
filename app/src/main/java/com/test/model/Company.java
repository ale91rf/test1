package com.test.model;



/**
 * Created by alejandro on 19/06/2015.
 */
public class Company {


    public String name;
    public String imageUrl;
    public float longitude;
    public int id;
    public float latitude;
    public String address;
    public String date;
    public String email;


    public Company(String name, String imageUrl, float longitude, int id, float latitude, String address, String date, String email) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.longitude = longitude;
        this.id = id;
        this.latitude = latitude;
        this.address = address;
        this.date = date;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
