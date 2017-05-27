package com.homework.ts.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ts on 2017/4/14.
 */

public class OrderBrief implements Parcelable {
    private int id;
    private int category_id;
    private int address_id;
    private int total_price;
    private int status;
    private String created_at;
    private String category_name;
    private String name;
    private String tel;
    private String city;
    private String region;
    private String community;
    private String house_number;
    private String date;
    private int waybills_status;
    private String waybills_desc;

/*
    private String number;
    private String time;
    private String state;
    private String type;
    private String buttonText;

    public OrderBrief(String number, String time, String state, String type, String text){
        this.number = number;
        this.time = time;
        this.state = state;
        this.type = type;
        this.buttonText = text;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
*/

    protected OrderBrief(Parcel in) {
        id = in.readInt();
        category_id = in.readInt();
        address_id = in.readInt();
        total_price = in.readInt();
        status = in.readInt();
        created_at = in.readString();
        category_name = in.readString();
        name = in.readString();
        tel = in.readString();
        city = in.readString();
        region = in.readString();
        community = in.readString();
        house_number = in.readString();
        date = in.readString();
        waybills_status = in.readInt();
        waybills_desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(category_id);
        dest.writeInt(address_id);
        dest.writeInt(total_price);
        dest.writeInt(status);
        dest.writeString(created_at);
        dest.writeString(category_name);
        dest.writeString(name);
        dest.writeString(tel);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeString(community);
        dest.writeString(house_number);
        dest.writeString(date);
        dest.writeInt(waybills_status);
        dest.writeString(waybills_desc);
    }

    public static final Creator<OrderBrief> CREATOR = new Creator<OrderBrief>() {
        @Override
        public OrderBrief createFromParcel(Parcel in) {
            return new OrderBrief(in);
        }

        @Override
        public OrderBrief[] newArray(int size) {
            return new OrderBrief[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWaybills_status() {
        return waybills_status;
    }

    public void setWaybills_status(int waybills_status) {
        this.waybills_status = waybills_status;
    }

    public String getWaybills_desc() {
        return waybills_desc;
    }

    public void setWaybills_desc(String waybills_desc) {
        this.waybills_desc = waybills_desc;
    }
}

