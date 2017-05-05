package com.homework.ts.model;

/**
 * Created by ts on 2017/3/25.
 */

public class Address {
    private int id;
    private String name;
    private String tel;
    private String city;
    private String region;//区
    private String community;//小区或大厦名
    private String house_number;//门牌号
    private String sex;
    private double lng;//经度
    private double lat;//纬度

    public Address(String name, String phone, String city, String district, String address1, String address2){
        this.name = name;
        this.tel = phone;
        this.city = city;
        this.region = district;
        this.community = address1;
        this.house_number = address2;
    }

    public Address(String name, String phone, String city, String district, String address1, String address2, String sex){
        this.name = name;
        this.tel = phone;
        this.city = city;
        this.region = district;
        this.community = address1;
        this.house_number = address2;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
