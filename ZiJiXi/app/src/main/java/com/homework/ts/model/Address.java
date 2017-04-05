package com.homework.ts.model;

/**
 * Created by ts on 2017/3/25.
 */

public class Address {
    private String name;
    private String phone;
    private String city;
    private String district;
    private String address1;
    private String address2;
    private int sex; //0-女， 1-男

    public Address(String name, String phone, String city, String district, String address1, String address2){
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.address1 = address1;
        this.address2 = address2;
    }

    public Address(String name, String phone, String city, String district, String address1, String address2, int sex){
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.address1 = address1;
        this.address2 = address2;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
