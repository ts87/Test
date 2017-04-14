package com.homework.ts.model;

/**
 * Created by ts on 2017/4/14.
 */

public class OrderBrief {
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

    public OrderBrief(String number, String time, String state, String type){
        this.number = number;
        this.time = time;
        this.state = state;
        this.type = type;
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
}
